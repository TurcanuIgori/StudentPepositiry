package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import Model.Adress;
import Model.Student;

public class AdressDao {
	public AdressDao(Connection con) {
		this.con = con;
	}

	Connection con;

	public List<Adress> getAllAdresses() {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Adress> listAdresses = new ArrayList();
		try {

			PreparedStatement pstmt = con.prepareStatement("select * from adress;");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Adress newAdress = new Adress();
				newAdress.setAdress_id(rs.getInt("adress_id"));
				newAdress.setCountry(rs.getString("country"));
				newAdress.setCity(rs.getString("city"));
				newAdress.setStreet(rs.getString("street"));
				listAdresses.add(newAdress);
			}
		} catch (SQLException e) {

		}
		return listAdresses;
	}

	public int checkAdress(Student student) {
		int adress_id = 0;
		try {

			PreparedStatement pstmt = con.prepareStatement(
					"select adress_id from adress where country like ? and city like ? and street like ?;");
			pstmt.setString(1, student.getAdress().getCountry());
			pstmt.setString(2, student.getAdress().getCity());
			pstmt.setString(3, student.getAdress().getStreet());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				adress_id = rs.getInt("adress_id");
			}
		} catch (SQLException e) {

		}
		return adress_id;
	}

	public boolean addAdress(Student student) {
		boolean eventStatus = false;
		try {

			PreparedStatement pstmt = con
					.prepareStatement("INSERT INTO adress (country, city, street) Values(?, ?, ?)");
			pstmt.setString(1, student.getAdress().getCountry());
			pstmt.setString(2, student.getAdress().getCity());
			pstmt.setString(3, student.getAdress().getStreet());
			if (pstmt.executeUpdate() != 0) {
				eventStatus = true;
			}
		} catch (SQLException e) {
		}
		return eventStatus;
	}

	public Adress getAdressByID(int adress_id) {
		Adress newAdress = new Adress();
		try {

			PreparedStatement pstmt = con.prepareStatement("select * from adress where adress_id = ?;");
			pstmt.setInt(1, adress_id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				newAdress.setAdress_id(rs.getInt("adress_id"));
				newAdress.setCountry(rs.getString("country"));
				newAdress.setCity(rs.getString("city"));
				newAdress.setStreet(rs.getString("street"));
			}
		} catch (SQLException e) {

		}
		return newAdress;
	}

	public boolean addPersonAdress(int adress_id, int student_id) {
		boolean eventStatus = false;
		try {

			PreparedStatement pstmt = con
					.prepareStatement("INSERT INTO person_adress (adress_id, person_id) Values(?, ?)");
			pstmt.setInt(1, adress_id);
			pstmt.setInt(2, student_id);
			if (pstmt.executeUpdate() != 0) {
				eventStatus = true;
			}
		} catch (SQLException e) {
		}
		return eventStatus;
	}

	public boolean deletePersonAdress(int student_id) {
		boolean eventStatus = false;
		try {
			PreparedStatement pstmt = con.prepareStatement("delete from person_adress where person_id=?");
			pstmt.setInt(1, student_id);
			if (pstmt.executeUpdate() != 0) {
				eventStatus = true;
			}
		} catch (SQLException e) {
		}
		return eventStatus;
	}

	public boolean deleteAdress(int adress_id) {
		boolean eventStatus = false;
		try {
			PreparedStatement pstmt = con.prepareStatement("delete from adress where adress_id=?");
			pstmt.setInt(1, adress_id);
			if (pstmt.executeUpdate() != 0) {
				eventStatus = true;
			}
		} catch (SQLException e) {
		}
		return eventStatus;
	}

	public boolean updatePersonAdress(int adress_id, int student_id) {
		boolean eventStatus = false;
		try {

			PreparedStatement pstmt = con.prepareStatement("update person_adress set adress_id=? where person_id=?;");
			pstmt.setInt(1, adress_id);
			pstmt.setInt(2, student_id);
			if (pstmt.executeUpdate() != 0) {
				eventStatus = true;
			}
		} catch (SQLException e) {
		}
		return eventStatus;
	}

	public TreeSet<Integer> getAdressIds() {
		TreeSet<Integer> adressIds = new TreeSet();
		try {

			PreparedStatement pstmt = con.prepareStatement("select adress_id from person_adress;");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id;
				id = rs.getInt("adress_id");
				adressIds.add(id);
			}
		} catch (SQLException e) {

		}
		return adressIds;
	}
}
