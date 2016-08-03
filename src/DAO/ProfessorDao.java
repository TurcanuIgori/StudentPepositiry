package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Professor;

public class ProfessorDao {
	public ProfessorDao(Connection con) {
		this.con = con;
	}

	Connection con;

	public List<Professor> getAllProfessors() {

		List<Professor> listProfessors = new ArrayList();
		try {

			PreparedStatement pstmt = con.prepareStatement(
					"select * from professor left join person on person.person_id=professor.professor_id;");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Professor newProfessor = new Professor();
				newProfessor.setProfessor_id(rs.getInt("professor_id"));
				newProfessor.setFirstName(rs.getString("firstName"));
				newProfessor.setLastName(rs.getString("lastName"));
				newProfessor.setDob(rs.getDate("dob"));
				newProfessor.setSalary(rs.getDouble("salary"));
				newProfessor.setPicture(rs.getBytes("picture"));
				listProfessors.add(newProfessor);
			}
		} catch (SQLException e) {

		}
		return listProfessors;
	}

	public Professor getProfessorById(int person_id) {
		Professor newProfessor = new Professor();
		try {

			PreparedStatement pstmt = con.prepareStatement("select * from person where person_id = ?;");
			pstmt.setInt(1, person_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				newProfessor.setProfessor_id(rs.getInt("person_id"));
				newProfessor.setFirstName(rs.getString("firstName"));
				newProfessor.setLastName(rs.getString("lastName"));

			}
		} catch (SQLException e) {
		}
		return newProfessor;
	}

	public List<Professor> getProfessorsId(int discipline_id) {
		List<Professor> professors_id = new ArrayList();
		try {

			PreparedStatement pstmt = con.prepareStatement("select * from professor_discipline where discipline_id=?;");
			pstmt.setInt(1, discipline_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Professor newProfessor = new Professor();
				newProfessor.setProfessor_id(rs.getInt("professor_id"));
				professors_id.add(newProfessor);
			}
		} catch (SQLException e) {
		}
		return professors_id;
	}
}
