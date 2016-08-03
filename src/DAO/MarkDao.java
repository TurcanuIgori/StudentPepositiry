package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Discipline;
import Model.Mark;

public class MarkDao {
	public MarkDao(Connection con) {
		this.con = con;
	}

	Connection con;

	public List<Mark> getAllMarks() {

		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<Mark> listMarks = new ArrayList();
		try {

			PreparedStatement pstmt = con.prepareStatement(
					"select mark.student_id, discipline.title, discipline.discipline_id, discipline.scolarshipthreshold, round(AVG(mark), 2) "
							+ "from student left join mark on mark.student_id = student.student_id left join discipline on discipline.discipline_id=mark.discipline_id "
							+ "group by mark.student_id, discipline.title, discipline.discipline_id, discipline.scolarshipthreshold;");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Mark newMark = new Mark();
				newMark.setMark(rs.getFloat("round"));
				newMark.setStudent_id(rs.getInt("student_id"));
				Discipline newDiscipline = new Discipline();
				newDiscipline.setDiscipline_id(rs.getInt("discipline_id"));
				newDiscipline.setTitle(rs.getString("title"));
				newDiscipline.setScholarshipThreshold(rs.getDouble("scolarshipthreshold"));
				newMark.setDiscipline(newDiscipline);
				listMarks.add(newMark);
			}
		} catch (SQLException e) {

		}
		return listMarks;
	}

	public boolean deleteMark(int student_id) {
		boolean eventStatus = false;
		try {
			PreparedStatement pstmt = con.prepareStatement("delete from mark where student_id=?;");
			pstmt.setInt(1, student_id);
			if (pstmt.executeUpdate() != 0) {
				eventStatus = true;
			}
		} catch (SQLException e) {
		}
		return eventStatus;
	}

	public boolean addMark(Mark newMark) {
		boolean eventStatus = false;
		try {
			PreparedStatement pstmt = con.prepareStatement(
					"INSERT INTO mark (mark, student_id, discipline_id, professor_id, created) values (?, ?, ?, ?, ?);");
			pstmt.setFloat(1, newMark.getMark());
			pstmt.setInt(2, newMark.getStudent_id());
			pstmt.setInt(3, newMark.getDiscipline().getDiscipline_id());
			pstmt.setInt(4, newMark.getProfessor_id());
			pstmt.setDate(5, new java.sql.Date(newMark.getCreatedDate().getTime()));
			if (pstmt.executeUpdate() != 0) {
				eventStatus = true;
			}
		} catch (SQLException e) {
		}
		return eventStatus;
	}

}
