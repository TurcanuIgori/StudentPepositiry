package Model;

import java.sql.Date;
import java.util.List;

public class Professor extends Person {
	private int professor_id;
	private double salary;

	public Professor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Professor(int person_id, String firstName, String lastName, Date dob, String genger, Adress adress,
			List<Phone> phoneList, byte[] picture, LibraryAbonament abonament, int adress_id, List<Mark> mark) {
		super(person_id, firstName, lastName, dob, genger, adress, phoneList, picture, abonament, adress_id, mark);
		// TODO Auto-generated constructor stub
	}

	public int getProfessor_id() {
		return professor_id;
	}

	public void setProfessor_id(int professor_id) {
		this.professor_id = professor_id;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

}
