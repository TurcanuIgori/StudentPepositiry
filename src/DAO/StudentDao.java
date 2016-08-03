package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.AbonamentStatus;
import Model.LibraryAbonament;
import Model.Student;

public class StudentDao {

	public StudentDao(Connection con) {
		this.con = con;
	}

	Connection con;

	public List<Student> getAllStudents() {

		List<Student> listStudents = new ArrayList<>();
		try {

			PreparedStatement pstmt = con.prepareStatement(
					"Select * from student left join person on person_id = student_id left join libraryabonament on libraryabonament.person_id=person.person_id left join person_adress on person_adress.person_id=person.person_id;");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Student newStudent = new Student();
				newStudent.setStudent_id(rs.getInt("student_id"));
				newStudent.setFirstName(rs.getString("firstname"));
				newStudent.setLastName(rs.getString("lastname"));
				newStudent.setDob(rs.getDate("dob"));
				newStudent.setGenger(rs.getString("gender"));
				newStudent.setPicture(rs.getBytes("picture"));
				LibraryAbonament newAbonament = new LibraryAbonament();
				newAbonament.setAbonament_id(rs.getInt("abonament_id"));
				newAbonament.setStartDate(rs.getDate("startdate"));
				newAbonament.setEndDate(rs.getDate("enddate"));
				newAbonament.setStatus(AbonamentStatus.valueOf(rs.getString("status")));
				newStudent.setAbonament(newAbonament);
				newStudent.setAdress_id(rs.getInt("adress_id"));
				listStudents.add(newStudent);
			}
		} catch (SQLException e) {

		}
		return listStudents;
	}

	public boolean addStudent(int student_id, int group_id) {
		boolean eventStatus = false;
		try {
			String sql = "INSERT INTO student (student_id, group_id) values (?, ?)";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, student_id);
			statement.setInt(2, group_id);
			if (statement.executeUpdate() != 0) {
				eventStatus = true;
			}
		} catch (SQLException e) {
		}
		return eventStatus;
	}

	public boolean updateStudent(int group_id, int student_id) {
		boolean eventStatus = false;
		try {
			PreparedStatement pstmt = con.prepareStatement("update student set group_id=? where student_id=?;");
			pstmt.setInt(1, group_id);
			pstmt.setInt(2, student_id);
			if (pstmt.executeUpdate() != 0) {
				eventStatus = true;
			}
		} catch (SQLException e) {
		}
		return eventStatus;
	}

	public boolean deleteStudent(int student_id) {
		boolean eventStatus = false;
		try {
			PreparedStatement pstmt = con.prepareStatement("delete from student where student_id=?;");
			pstmt.setInt(1, student_id);
			if (pstmt.executeUpdate() != 0) {
				eventStatus = true;
			}
		} catch (SQLException e) {
		}
		return eventStatus;
	}

	public boolean deleteStudentData(int student_id) {
		boolean eventStatus = false;
		try {
			PreparedStatement pstmt = con.prepareStatement("delete from person where person_id=?;");
			pstmt.setInt(1, student_id);
			if (pstmt.executeUpdate() != 0) {
				eventStatus = true;
			}
		} catch (SQLException e) {
		}
		return eventStatus;
	}

	public boolean addStudentData(Student student) {
		boolean eventStatus = false;
		try {
			PreparedStatement pstmt = con.prepareStatement(
					"INSERT INTO person (lastname, firstname, dob, gender, picture) values (?, ?, ?, ?, ?)");
			pstmt.setString(1, student.getLastName());
			pstmt.setString(2, student.getFirstName());
			pstmt.setDate(3, new java.sql.Date(student.getDob().getTime()));
			pstmt.setString(4, student.getGenger());
			pstmt.setBytes(5, student.getPicture());
			if (pstmt.executeUpdate() != 0) {
				eventStatus = true;
			}
		} catch (SQLException e) {
		}
		return eventStatus;
	}

	public Student getStudentById(int student_id) {

		Student newStudent = new Student();
		try {

			PreparedStatement pstmt = con.prepareStatement(
					"Select * from student left join person on person_id = student_id left join libraryabonament on libraryabonament.person_id=person.person_id "
							+ "left join person_adress on person_adress.person_id=person.person_id where student_id=?;");
			pstmt.setInt(1, student_id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				newStudent.setStudent_id(rs.getInt("student_id"));
				newStudent.setFirstName(rs.getString("firstname"));
				newStudent.setLastName(rs.getString("lastname"));
				newStudent.setDob(rs.getDate("dob"));
				newStudent.setGenger(rs.getString("gender"));
				newStudent.setPicture(rs.getBytes("picture"));
				LibraryAbonament newAbonament = new LibraryAbonament();
				newAbonament.setAbonament_id(rs.getInt("abonament_id"));
				newAbonament.setStartDate(rs.getDate("startdate"));
				newAbonament.setEndDate(rs.getDate("enddate"));
				newAbonament.setStatus(AbonamentStatus.valueOf(rs.getString("status")));
				newStudent.setAbonament(newAbonament);
				newStudent.setAdress_id(rs.getInt("adress_id"));
			}
		} catch (SQLException e) {

		}
		return newStudent;
	}

	public int getStudentID() {
		int student_id = 0;
		try {
			PreparedStatement stmt = con.prepareStatement("select MAX(person_id) as m from person");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				student_id = rs.getInt("m");
				System.out.println(student_id);
			}
		} catch (SQLException e) {
		}
		return student_id;
	}

	public boolean updateStudent(Student student) {
		boolean eventStatus = false;
		try {
			PreparedStatement pstmt = con.prepareStatement(
					"update person set lastname=?, firstname=?, dob=?, gender=?, picture=? where person_id=?");
			pstmt.setString(1, student.getLastName());
			pstmt.setString(2, student.getFirstName());
			pstmt.setDate(3, new java.sql.Date(student.getDob().getTime()));
			pstmt.setString(4, student.getGenger());
			pstmt.setBytes(5, student.getPicture());
			pstmt.setInt(6, student.getStudent_id());
			if (pstmt.executeUpdate() != 0) {
				eventStatus = true;
			}
		} catch (SQLException e) {
		}
		return eventStatus;
	}
}
