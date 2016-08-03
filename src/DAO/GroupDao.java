package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Group;

public class GroupDao {
	public GroupDao(Connection con) {
		this.con = con;
	}

	Connection con;

	public List<Group> getAllGroups() {

		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<Group> listGroups = new ArrayList();
		try {

			PreparedStatement pstmt = con.prepareStatement("select * from grup");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Group newGroup = new Group();
				newGroup.setGroup_id(rs.getInt("group_id"));
				newGroup.setName(rs.getString("name"));
				listGroups.add(newGroup);
			}
		} catch (SQLException e) {

		}
		return listGroups;
	}

	public Group getGroupById(int student_id) {

		Group group = new Group();
		try {

			PreparedStatement pstmt = con
					.prepareStatement("select * from Student left join grup on student.group_id = grup.group_id "
							+ "where student_id = ?;");
			pstmt.setInt(1, student_id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				group.setGroup_id((rs.getInt("group_id")));
				group.setName(rs.getString("name"));
			}
		} catch (SQLException e) {

		}
		return group;
	}

}
