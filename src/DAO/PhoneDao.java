package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Phone;
import Model.PhoneType;

public class PhoneDao {
	public PhoneDao(Connection con) {
		this.con = con;
	}

	Connection con;

	public List<Phone> getAllPhones() {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Phone> listPhones = new ArrayList();
		try {

			PreparedStatement pstmt = con.prepareStatement(
					"select * from person_phone left join phone on person_phone.phone_id=phone.phone_id left join phonetype on phonetype.type_id=phone.type_id;");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Phone newPhone = new Phone();
				newPhone.setPhone_id(rs.getInt("phone_id"));
				newPhone.setPerson_id(rs.getInt("person_id"));
				newPhone.setNumber(rs.getString("number"));
				PhoneType newPhoneType = new PhoneType();
				newPhoneType.setName(rs.getString("name"));
				newPhoneType.setType_id(rs.getInt("type_id"));
				newPhone.setPhonePype(newPhoneType);
				listPhones.add(newPhone);
			}
		} catch (SQLException e) {

		}
		return listPhones;
	}

	public int checkPhone(Phone phone) {
		int phone_id = 0;
		try {

			PreparedStatement pstmt = con
					.prepareStatement("select phone_id from phone where number like ? and type_id = ?;");
			pstmt.setString(1, phone.getNumber());
			pstmt.setInt(2, phone.getPhoneType().getType_id());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				phone_id = rs.getInt("phone_id");
			}
		} catch (SQLException e) {
		}
		return phone_id;
	}

	public boolean addPhone(Phone phone) {
		boolean eventStatus = false;
		try {

			PreparedStatement pstmt = con.prepareStatement("INSERT INTO phone (number, type_id) Values(?, ?)");
			pstmt.setString(1, phone.getNumber());
			pstmt.setInt(2, phone.getPhoneType().getType_id());
			if (pstmt.executeUpdate() != 0) {
				eventStatus = true;
			}
		} catch (SQLException e) {
		}
		return eventStatus;
	}

	public List<Phone> getPhonesByID(int student_id) {
		List<Phone> listPhones = new ArrayList();
		try {

			PreparedStatement pstmt = con.prepareStatement(
					"select * from person_phone left join phone on phone.phone_id = person_phone.phone_id"
							+ " left join phonetype on phone.type_id = phonetype.type_id where person_id = ?;");
			pstmt.setInt(1, student_id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Phone newPhone = new Phone();
				newPhone.setPhone_id(rs.getInt("phone_id"));
				newPhone.setPerson_id(rs.getInt("person_id"));
				newPhone.setNumber(rs.getString("number"));
				PhoneType newPhoneType = new PhoneType();
				newPhoneType.setName(rs.getString("name"));
				newPhoneType.setType_id(rs.getInt("type_id"));
				newPhone.setPhonePype(newPhoneType);
				listPhones.add(newPhone);
			}
		} catch (SQLException e) {

		}
		return listPhones;
	}

	public boolean addPersonPhone(int phone_id, int student_id) {
		boolean eventStatus = false;
		try {

			PreparedStatement pstmt = con
					.prepareStatement("INSERT INTO person_phone (phone_id, person_id) Values(?, ?)");
			pstmt.setInt(1, phone_id);
			pstmt.setInt(2, student_id);
			if (pstmt.executeUpdate() != 0) {
				eventStatus = true;
			}
		} catch (SQLException e) {
		}
		return eventStatus;
	}

	public boolean deletePersonPhone(int student_id) {
		boolean eventStatus = false;
		try {
			PreparedStatement pstmt = con.prepareStatement("delete from person_phone where person_id=?");
			pstmt.setInt(1, student_id);
			if (pstmt.executeUpdate() != 0) {
				eventStatus = true;
			}
		} catch (SQLException e) {
		}
		return eventStatus;
	}

	public List<PhoneType> getAllPhoneTypes() {
		List<PhoneType> listPhoneTypes = new ArrayList();
		try {

			PreparedStatement pstmt = con.prepareStatement("select * from phonetype;");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				PhoneType newPhoneType = new PhoneType();
				newPhoneType.setName(rs.getString("name"));
				newPhoneType.setType_id(rs.getInt("type_id"));
				listPhoneTypes.add(newPhoneType);
			}
		} catch (SQLException e) {

		}
		return listPhoneTypes;
	}

}
