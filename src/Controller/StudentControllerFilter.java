package Controller;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import Model.AbonamentStatus;
import Model.Action;
import Model.Adress;
import Model.Discipline;
import Model.Group;
import Model.LibraryAbonament;
import Model.Mark;
import Model.Phone;
import Model.PhoneType;
import Model.Search;
import Model.Student;

/**
 * Servlet Filter implementation class StudentControllerFilter
 */
public class StudentControllerFilter implements Filter {
	public StudentControllerFilter() {
		// TODO Auto-generated constructor stub
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;

		Action action = Action.ADD;
		if (request.getParameter("action") != null)
			action = Action.valueOf(request.getParameter("action"));
		switch (action) {
		case ADD:
			addData(req, res);
			break;
		case ADD_ABONAMENT:
			updateAbonament(req, res);
			break;
		case SEARCH:
			try {
				createSearch(req, res);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case ADD_MARK:
			try {
				addMark(req, res);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case DELETE:
			
			String[] students_id1 = req.getParameterValues("check");
			List<Integer> listStudents = new ArrayList<>();

			for (String id : students_id1) {
				int student_id = Integer.parseInt(id);
				listStudents.add(student_id);
			}
			request.setAttribute("idList", listStudents);
			break;
		default:
			break;
		}
		chain.doFilter(request, res);
	}

	private void createSearch(ServletRequest req, ServletResponse res) throws ParseException {
		HttpServletRequest request = (HttpServletRequest) req;
		Search newSearch = new Search();
		if (request.getParameter("partialName").length() > 0) {
			newSearch.setName(request.getParameter("partialName"));
		} else {
			newSearch.setName(null);
		}
		if (request.getParameter("partialAdress").length() > 0) {
			newSearch.setAdress(request.getParameter("partialAdress"));
		} else {
			newSearch.setAdress(null);
		}
		if (request.getParameter("group") != null) {
			newSearch.setGroup_id(Integer.parseInt(request.getParameter("group")));
		} else {
			newSearch.setGroup_id(0);
		}
		if (request.getParameter("gender") != null) {
			newSearch.setGender(request.getParameter("gender").charAt(0));
		} else {
			newSearch.setGender((char) 0);
		}
		DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		if (request.getParameter("startDob").length() > 0) {
			String startDobString = (((String) request.getParameter("startDob")).replaceAll("/", "-"));
			Date startDob;
			startDob = (Date) formatter.parse(startDobString);
			newSearch.setStartDob(startDob);
		} else {
			newSearch.setStartDob(null);
		}
		if (request.getParameter("endDob").length() > 0) {
			String endDobString = (((String) request.getParameter("endDob")).replaceAll("/", "-"));
			Date endDob;
			endDob = (Date) formatter.parse(endDobString);
			newSearch.setEndDob(endDob);
		} else {
			newSearch.setEndDob(null);
		}

		if (request.getParameter("discipline").length() > 0) {
			newSearch.setDiscipline_id(Integer.parseInt(request.getParameter("discipline")));
		} else {
			newSearch.setDiscipline_id(0);
		}
		if (request.getParameter("disciplineAverage").length() > 0) {
			newSearch.setDisciplineAvg(Float.parseFloat(request.getParameter("disciplineAverage")));
		} else {
			newSearch.setDisciplineAvg(0);
		}
		if (request.getParameter("totalAverage").length() > 0) {
			newSearch.setTotalAvg(Float.parseFloat(request.getParameter("totalAverage")));
		}
		request.setAttribute("newSearch", newSearch);
	}

	private void addMark(ServletRequest req, ServletResponse res) throws ParseException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) req;

		Mark newMark = new Mark();
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new Date();
		String createdDate = df.format(date);
		newMark.setCreatedDate(df.parse(createdDate));
		Discipline discipline = new Discipline();
		discipline.setDiscipline_id(Integer.parseInt(request.getParameter("discipline")));
		newMark.setDiscipline(discipline);
		newMark.setMark(Float.parseFloat(request.getParameter("mark")));
		newMark.setStudent_id(Integer.parseInt(request.getParameter("studentIdMark")));
		newMark.setProfessor_id(Integer.parseInt(request.getParameter("professor")));
		request.setAttribute("newMark", newMark);
	}

	private void updateAbonament(ServletRequest req, ServletResponse res) {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		LibraryAbonament newAbonament = new LibraryAbonament();
		try {
			newAbonament.setAbonament_id(Integer.parseInt((String) request.getParameter("abonament_id")));
			newAbonament.setStatus(AbonamentStatus.valueOf((String) request.getParameter("status")));

			String date1 = (((String) request.getParameter("startDate")).replaceAll("/", "-"));
			Date date;
			DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
			date = (Date) formatter.parse(date1);
			newAbonament.setStartDate(date);
			String date3 = (((String) request.getParameter("endDate")).replaceAll("/", "-"));
			Date date2;
			date2 = (Date) formatter.parse(date3);
			newAbonament.setEndDate(date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("newAbonament", newAbonament);

	}

	public void addData(ServletRequest req, ServletResponse res) {
		HttpServletRequest request = (HttpServletRequest) req;

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (isMultipart) {
			Student student = new Student();
			Adress newAdress = new Adress();
			Group newGroup = new Group();
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			Map<String, String> phoneList = new HashMap<String, String>();
			List<FileItem> items = null;
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Iterator<FileItem> iter = items.iterator();
			try {
				while (iter.hasNext()) {
					FileItem item = iter.next();
					if (item.isFormField()) {
						String name = item.getFieldName();
						switch (name) {
						case "idStudent":
							try {
								student.setStudent_id(Integer.parseInt(item.getString()));
							} catch (NumberFormatException e) {
								student.setStudent_id(0);
							}
							break;
						case "firstName":
							student.setFirstName(item.getString());
							break;
						case "lastName":
							student.setLastName(item.getString());
							break;
						case "dob":
							String date1 = item.getString().replaceAll("/", "-");
							Date date;
							DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
							date = (Date) formatter.parse(date1);
							student.setDob(date);
							break;
						case "gender":
							student.setGenger(item.getString());
							break;
						case "Country":
							newAdress.setCountry(item.getString());
							break;
						case "City":
							newAdress.setCity(item.getString());
							break;
						case "adressLine":
							newAdress.setStreet(item.getString());
							student.setAdress(newAdress);
							break;
						case "group":
							newGroup.setGroup_id(Integer.parseInt(item.getString()));
							student.setGroup(newGroup);
							break;
						default:
							phoneList.put(item.getFieldName(), item.getString());
							break;
						}
					} else {
						byte[] data = item.get();
						if (item.getSize() > 0) {
							student.setPicture(data);
						} else if (student.getStudent_id() != 0) {
							@SuppressWarnings("unchecked")
							List<Student> listSt = (List<Student>) request.getSession().getAttribute("listStudents");
							for (Student newStudent : listSt) {
								if (student.getStudent_id() == newStudent.getStudent_id()) {
									student.setPicture(newStudent.getPicture());
								}
							}
						} else {
							File img = new File("/home/user/downloads/noimg.jpg");
							BufferedImage bufferedImage = ImageIO.read(img);
							WritableRaster raster = bufferedImage.getRaster();
							DataBufferByte noImg = (DataBufferByte) raster.getDataBuffer();
							student.setPicture(noImg.getData());
						}
					}
				}
				List<Phone> listPhones = new ArrayList<>();
				for (String keyType : phoneList.keySet()) {
					String valueType = phoneList.get(keyType);
					if (keyType.startsWith("sele")) {
						char endType = keyType.charAt(keyType.length() - 1);
						for (String keyPhone : phoneList.keySet()) {
							Phone newPhone = new Phone();
							String valuePhone = phoneList.get(keyPhone);
							if (keyPhone.startsWith("addP") && keyPhone.endsWith(String.valueOf(endType))) {
								newPhone.setNumber(valuePhone);
								PhoneType newPhoneType = new PhoneType();
								newPhoneType.setType_id(Integer.parseInt(valueType));
								newPhone.setPhonePype(newPhoneType);
								if (newPhone.getNumber().length() > 0 && newPhone.getPhoneType().getType_id() > 0) {
									listPhones.add(newPhone);
								}
							}
						}
					}
					valueType = null;
				}
				student.setPhoneList(listPhones);
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("newStudent", student);

		}
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
