package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;

import Model.Action;
import Model.Group;
import Model.LibraryAbonament;
import Model.Mark;
import Model.Professor;
import Model.Search;
import Model.Student;
import Service.StudentService;

//@WebServlet("/list")
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StudentService studentService = new StudentService();

	public StudentController() {
		super();
	}	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Action action = Action.ADD;
		if (request.getParameter("action") != null)
			action = Action.valueOf(request.getParameter("action"));
		Student newStudent = new Student();
		String json;
		switch (action) {
		case ADD:
			request.setAttribute("listPhoneTypes", studentService.listPhoneTypes());
			request.setAttribute("listDisciplines", studentService.listDisciplines());
			request.setAttribute("listGroups", studentService.listGroups());
			request.setAttribute("listStudents", studentService.getCompletListStudents());
			session.setAttribute("listStudents", studentService.getCompletListStudents());
			request.getRequestDispatcher("/list.jsp").forward(request, response);
			break;
		case LIST:
			int abonament_id = Integer.parseInt(request.getParameter("id"));
			newStudent = studentService.getStudentById(abonament_id);
			newStudent.setAbonament(studentService.getAbonamentById(abonament_id));
			json = new Gson().toJson(newStudent);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			break;
		case GET_MARK:
			int id = Integer.parseInt(request.getParameter("id"));
			newStudent = studentService.getStudentById(id);
			Group newGroup = studentService.getGroupById(id);
			newStudent.setGroup(newGroup);
			json = new Gson().toJson(newStudent);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			break;
		case GET_PROFESSORS:
			int discipline_id = Integer.parseInt(request.getParameter("id"));
			List<Professor> listProfessors = new ArrayList<>();
			listProfessors = studentService.getListProfessorsById(discipline_id);
			json = new Gson().toJson(listProfessors);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			break;
		case DOWNLOAD_PDF:
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename='FirstPdf.pdf';");
			DownloadPdf pdfFile = new DownloadPdf();
			pdfFile.createPdf(response, studentService.getCompletListStudents());
			break;
		case GET_STUDENT:
			int student_id = Integer.parseInt(request.getParameter("id"));
			newStudent = studentService.getStudentById(student_id);
			json = new Gson().toJson(newStudent);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			break;
		default:
			break;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<Student> listStudents = new ArrayList<>();
		listStudents = studentService.getCompletListStudents();
		boolean eventStatus = false;
		String textStatus = null;
		Action action = Action.ADD;
		if (request.getParameter("action") != null)
			action = Action.valueOf(request.getParameter("action"));
		switch (action) {
		case SEARCH:
			Search newSearch = new Search();
			newSearch = (Search) request.getAttribute("newSearch");
			listStudents = studentService.getStudentsBySearch(newSearch);
			eventStatus = true;
			if (listStudents.size() > 0) {
				textStatus = "Rezultatul cautarii este mai sus!";
			} else {
				textStatus = "Cautare esuata!";
			}

			break;
		case ADD:

			boolean isMultipart = ServletFileUpload.isMultipartContent((HttpServletRequest) request);
			if (isMultipart) {

				Student newStudent = (Student) request.getAttribute("newStudent");
				if (newStudent.getStudent_id() == 0) {

					try {
						if (studentService.addStudent(newStudent)) {
							eventStatus = true;
							textStatus = "Studentul a fost adaugat!";
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					try {
						if (studentService.updateStudent(newStudent)) {
							eventStatus = true;
							textStatus = "Studentul a fost modificat!";
						}
						;
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			break;
		case ADD_ABONAMENT:
			LibraryAbonament newAbonament = (LibraryAbonament) request.getAttribute("newAbonament");
			if (studentService.updateAbonament(newAbonament)) {
				eventStatus = true;
				textStatus = "Abonamentul a fost modificat!";
			}
			break;
		case ADD_MARK:
			Mark newMark = (Mark) request.getAttribute("newMark");
			if (studentService.addMark(newMark)) {
				eventStatus = true;
				textStatus = "Nota a fost adaugata!";
			}
			break;
		case DELETE:
			@SuppressWarnings("unchecked")
			List<Integer> idList = (List<Integer>) request.getAttribute("idList");
			try {
				if (studentService.deleteStudents(idList)) {
					eventStatus = true;
					textStatus = "Studentii/Studentul au fost stersi!";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}

		session.setAttribute("listStudents", studentService.getCompletListStudents());
		if (eventStatus) {
			request.setAttribute("textStatus", textStatus);
		}

		request.setAttribute("listPhoneTypes", studentService.listPhoneTypes());
		request.setAttribute("listDisciplines", studentService.listDisciplines());
		request.setAttribute("listGroups", studentService.listGroups());
		request.setAttribute("listStudents", listStudents);
		request.getRequestDispatcher("/list.jsp").forward(request, response);
	}
}
