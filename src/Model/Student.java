package Model;

import java.util.Date;
import java.util.List;

public class Student extends Person {
	private int student_id;
	private Group group;
	private List<Discipline> disciplineList;

	public Student(int person_id, String firstName, String lastName, Date dob, String genger, Adress adress,
			List<Phone> phoneList, byte[] picture, LibraryAbonament abonament, int adress_id, List<Mark> mark,
			int student_id, Group group, List<Discipline> disciplineList) {
		super(person_id, firstName, lastName, dob, genger, adress, phoneList, picture, abonament, adress_id, mark);
		this.student_id = student_id;
		this.group = group;
		this.disciplineList = disciplineList;
	}

	public Student() {
		// TODO Auto-generated constructor stub
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	public List<Discipline> getDisciplineList() {
		return disciplineList;
	}

	public void setDisciplineList(List<Discipline> disciplineList) {
		this.disciplineList = disciplineList;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}
