package Model;

import java.util.Date;

public class Search {
	private String name;
	private String adress;
	private int group_id;
	private char gender;
	private Date startDob;
	private Date endDob;
	private int discipline_id;
	private float disciplineAvg;
	private float totalAvg;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public Date getStartDob() {
		return startDob;
	}

	public void setStartDob(Date startDob) {
		this.startDob = startDob;
	}

	public Date getEndDob() {
		return endDob;
	}

	public void setEndDob(Date endDob) {
		this.endDob = endDob;
	}

	public int getDiscipline_id() {
		return discipline_id;
	}

	public void setDiscipline_id(int discipline_id) {
		this.discipline_id = discipline_id;
	}

	public float getDisciplineAvg() {
		return disciplineAvg;
	}

	public void setDisciplineAvg(float disciplineAvg) {
		this.disciplineAvg = disciplineAvg;
	}

	public float getTotalAvg() {
		return totalAvg;
	}

	public void setTotalAvg(float totalAvg) {
		this.totalAvg = totalAvg;
	}

}
