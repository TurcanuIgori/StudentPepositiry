package Model;

import java.util.ArrayList;

public class Group {
	private int group_id;
	private String name;
	private ArrayList<Student> sudentList;
	
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Student> getSudentList() {
		return sudentList;
	}
	public void setSudentList(ArrayList<Student> sudentList) {
		this.sudentList = sudentList;
	}

}
