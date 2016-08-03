package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeSet;

import Model.Search;

public class SearchDao {
	public SearchDao(Connection con) {
		this.con = con;
	}

	Connection con;

	public TreeSet<Integer> listSearch(Search search) {
		TreeSet<Integer> listIds = new TreeSet();
		try {
			StringBuilder query = new StringBuilder(
					"select student.student_id from student inner join person on person.person_id = student.student_id ");
			if (search.getName() != null) {
				query.append(" and (upper(lastname) like '%" + search.getName().toUpperCase()
						+ "%' or upper(firstname) like '%" + search.getName().toUpperCase() + "%')");
			}
			if (search.getGender() != 'A') {
				query.append(" and person.gender = '" + search.getGender() + "' ");
			}

			if (search.getStartDob() != null) {
				query.append(" and(dob >= '" + new java.sql.Date(search.getStartDob().getTime()) + "') ");
			}
			if (search.getEndDob() != null) {
				query.append(" and(dob <= '" + new java.sql.Date(search.getEndDob().getTime()) + "') ");
			}
			if (search.getGroup_id() > 0) {
				query.append(" inner join grup on grup.group_id = student.group_id and grup.group_id = "
						+ search.getGroup_id() + " ");
				if (search.getDisciplineAvg() != 0) {
					query.append("and scolarshipthreshold > " + search.getDisciplineAvg() + " ");
				}
			}

			if (search.getAdress() != null) {
				query.append(" inner join person_adress on person_adress.person_id = person.person_id "
						+ "inner join adress on adress.adress_id = person_adress.adress_id and ((upper(country) || upper(city) || upper(street) like '%"
						+ search.getAdress().toUpperCase()
						+ "%') or (upper(city) || upper(country) || upper(street) like '%"
						+ search.getAdress().toUpperCase()
						+ "%') or (upper(city) || upper(street) || upper(country) like '%"
						+ search.getAdress().toUpperCase()
						+ "%') or (upper(country) || upper(street) || upper(city) like '%"
						+ search.getAdress().toUpperCase()
						+ "%') or (upper(street) || upper(country) || upper(city) like '%"
						+ search.getAdress().toUpperCase()
						+ "%') or (upper(street) || upper(city) || upper(country) like '%"
						+ search.getAdress().toUpperCase() + "%')) ");
			}
			if (search.getDiscipline_id() != 0) {
				if (search.getDisciplineAvg() != 0) {
					query.append(
							" inner join discipline_average on discipline_average.student_id=student.student_id and discipline_average.discipline_id="
									+ search.getDiscipline_id() + " and avg >= " + search.getDisciplineAvg() + " ");
				} else {
					query.append(
							" inner join student_discipline on student_discipline.student_id = student.student_id inner join discipline on discipline.discipline_id = student_discipline.discipline_id and discipline.discipline_id = "
									+ search.getDiscipline_id() + " ");
				}
			} else if (search.getDisciplineAvg() != 0) {
				query.append(
						"inner join discipline_average on discipline_average.student_id=student.student_id where discipline_average.avg >= "
								+ search.getDisciplineAvg());
			}
			if (search.getTotalAvg() != 0) {
				query.append(
						" inner join discipline_average on (discipline_average.student_id=student.student_id) group by student.student_id having avg(avg) >= "
								+ search.getTotalAvg() + " ");
			}

			// query.append(" order by person.firstname, person.lastname");
			PreparedStatement pstmt = con.prepareStatement(query.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = 0;
				id = rs.getInt("student_id");
				listIds.add(id);
			}
		} catch (SQLException e) {

		}
		return listIds;

	}

}
