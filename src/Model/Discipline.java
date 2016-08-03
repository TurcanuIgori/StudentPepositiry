package Model;

import java.util.ArrayList;

public class Discipline {
	private int discipline_id;
	private String title;
	private double scholarshipThreshold;
	private ArrayList<Professor> professorList;
	
	public int getDiscipline_id() {
		return discipline_id;
	}
	public void setDiscipline_id(int discipline_id) {
		this.discipline_id = discipline_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getScholarshipThreshold() {
		return scholarshipThreshold;
	}
	public void setScholarshipThreshold(double scholarshipThreshold) {
		this.scholarshipThreshold = scholarshipThreshold;
	}
	public ArrayList<Professor> getProfessorList() {
		return professorList;
	}
	public void setProfessorList(ArrayList<Professor> professorList) {
		this.professorList = professorList;
	}

}
