package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Discipline;

public class DisciplineDao {
	public DisciplineDao(Connection con) {
		this.con = con;
	}

	Connection con;

	public List<Discipline> getAllDisciplines() {

		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<Discipline> listDisciplines = new ArrayList();
		try {

			PreparedStatement pstmt = con.prepareStatement("select * from discipline");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Discipline newDiscipline = new Discipline();
				newDiscipline.setDiscipline_id(rs.getInt("discipline_id"));
				newDiscipline.setTitle(rs.getString("title"));
				newDiscipline.setScholarshipThreshold(rs.getDouble("scolarshipthreshold"));
				listDisciplines.add(newDiscipline);
			}
		} catch (SQLException e) {

		}
		return listDisciplines;
	}

	public List<Discipline> getDisciplinesById(int student_id) {
		List<Discipline> listDisciplines = new ArrayList();
		try {

			PreparedStatement pstmt = con.prepareStatement(
					"Select * from student_discipline left join discipline on discipline.discipline_id = student_discipline.discipline_id where student_id=?;");
			pstmt.setInt(1, student_id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Discipline newDiscipline = new Discipline();
				newDiscipline.setDiscipline_id(rs.getInt("discipline_id"));
				newDiscipline.setTitle(rs.getString("title"));
				listDisciplines.add(newDiscipline);
			}
		} catch (SQLException e) {

		}
		return listDisciplines;
	}

	public boolean deleteStudentDiscipline(int student_id) {
		boolean eventStatus = false;
		try {
			PreparedStatement pstmt = con.prepareStatement("delete from student_discipline where student_id=?;");
			pstmt.setInt(1, student_id);
			if (pstmt.executeUpdate() != 0) {
				eventStatus = true;
			}
		} catch (SQLException e) {
		}
		return eventStatus;
	}

	public boolean addStudentDiscipline(int student_id, int discipline_id) {
		boolean eventStatus = false;
		try {
			PreparedStatement pstmt = con
					.prepareStatement("insert into student_discipline (student_id, discipline_id) values(?, ?);");
			pstmt.setInt(1, student_id);
			pstmt.setInt(2, discipline_id);
			if (pstmt.executeUpdate() != 0) {
				eventStatus = true;
			}
		} catch (SQLException e) {
		}
		return eventStatus;
	}

}
