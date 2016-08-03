package Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import Model.Student;

/**
 * Servlet implementation class picture
 */
public class picture extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {

	}

	public picture() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		@SuppressWarnings("unused")
		HttpSession session = request.getSession();
		int studId = Integer.parseInt(request.getParameter("id"));

		@SuppressWarnings("unchecked")
		List<Student> listSt = (List<Student>) request.getSession().getAttribute("listStudents");
		for (Student student : listSt) {
			if (student.getStudent_id() == studId) {
				try {
					response.setContentType("image/jpeg");
					response.setContentLength(student.getPicture().length);
					response.getOutputStream().write(student.getPicture());
					response.getOutputStream().flush();
					response.getOutputStream().close();
				} catch (Exception e) {
				}
			}
		}
	}
}