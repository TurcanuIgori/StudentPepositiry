package Model;

import java.util.Date;
import java.util.List;

public class Person {
	private int person_id;
	private String firstName;
	private String lastName;
	private Date dob;
	private String genger;
	private Adress adress;
	private List<Phone> phoneList;
	private byte[] picture = null;
	private LibraryAbonament abonament;
	private int adress_id;
	private List<Mark> mark;

	public Person() {
	}

	public Person(int person_id, String firstName, String lastName, Date dob, String genger, Adress adress,
			List<Phone> phoneList, byte[] picture, LibraryAbonament abonament, int adress_id, List<Mark> mark) {
		super();
		this.person_id = person_id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.genger = genger;
		this.adress = adress;
		this.phoneList = phoneList;
		this.picture = picture;
		this.abonament = abonament;
		this.adress_id = adress_id;
		this.setMark(mark);
	}

	public int getPerson_id() {
		return person_id;
	}

	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGenger() {
		return genger;
	}

	public void setGenger(String genger) {
		this.genger = genger;
	}

	public List<Phone> getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(List<Phone> phoneList) {
		this.phoneList = phoneList;
	}

	public LibraryAbonament getAbonament() {
		return abonament;
	}

	public void setAbonament(LibraryAbonament abonament) {
		this.abonament = abonament;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}

	public int getAdress_id() {
		return adress_id;
	}

	public void setAdress_id(int adress_id) {
		this.adress_id = adress_id;
	}

	public List<Mark> getMark() {
		return mark;
	}

	public void setMark(List<Mark> mark) {
		this.mark = mark;
	}

}
