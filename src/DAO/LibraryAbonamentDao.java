package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.AbonamentStatus;
import Model.LibraryAbonament;

public class LibraryAbonamentDao {
	public LibraryAbonamentDao(Connection con) {
		this.con = con;
	}

	Connection con;

	public void addLibraryAbonament(int student_id) {
		try {
			PreparedStatement pstmt = con.prepareStatement(
					"INSERT INTO libraryabonament (abonament_id, status, person_id) values (?, ?, ?)");
			pstmt.setInt(1, student_id);
			pstmt.setString(2, "None");
			pstmt.setInt(3, student_id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
		}
	}

	public LibraryAbonament getAbonamentByID(int student_id) {
		LibraryAbonament newAbonament = new LibraryAbonament();
		try {
			PreparedStatement pstmt = con.prepareStatement("select * from libraryabonament where abonament_id =?;");
			pstmt.setInt(1, student_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				newAbonament.setAbonament_id(rs.getInt("abonament_id"));
				newAbonament.setStatus(AbonamentStatus.valueOf(rs.getString("status")));

				newAbonament.setStartDate(rs.getDate("startdate"));
				newAbonament.setEndDate(rs.getDate("enddate"));
			}
		} catch (SQLException e) {
		}
		return newAbonament;
	}

	public boolean updateAbonament(LibraryAbonament abonament, int student_id) {
		boolean eventStatus = false;
		try {

			PreparedStatement pstmt = con.prepareStatement(
					"Update libraryabonament set status=?, startdate=?, enddate=? where abonament_id=?;");
			pstmt.setString(1, abonament.getStatus().toString());
			pstmt.setDate(2, new java.sql.Date(abonament.getStartDate().getTime()));
			pstmt.setDate(3, new java.sql.Date(abonament.getEndDate().getTime()));
			pstmt.setInt(4, student_id);
			if (pstmt.executeUpdate() != 0) {
				eventStatus = true;
			}
			;
		} catch (SQLException e) {
		}
		return eventStatus;
	}
}
