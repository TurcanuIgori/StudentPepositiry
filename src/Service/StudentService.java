package Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import DAO.AdressDao;
import DAO.DisciplineDao;
import DAO.GroupDao;
import DAO.LibraryAbonamentDao;
import DAO.MarkDao;
import DAO.PhoneDao;
import DAO.ProfessorDao;
import DAO.SearchDao;
import DAO.StudentDao;
import Model.Adress;
import Model.Discipline;
import Model.Group;
import Model.LibraryAbonament;
import Model.Mark;
import Model.Phone;
import Model.PhoneType;
import Model.Professor;
import Model.Search;
import Model.Student;
import Utils.ConnectionDB;

public class StudentService {

	StudentDao studentDao;
	AdressDao adressDao;
	PhoneDao phoneDao;
	MarkDao markDao;
	DisciplineDao disciplineDao;
	GroupDao groupDao;
	ProfessorDao professorDao;
	LibraryAbonamentDao libraryAbonamentDao;
	SearchDao searchDao;
	ConnectionDB dbconn = new ConnectionDB();
	Connection con = dbconn.getConnect();

	public StudentService() {

		studentDao = new StudentDao(con);
		adressDao = new AdressDao(con);
		phoneDao = new PhoneDao(con);
		markDao = new MarkDao(con);
		disciplineDao = new DisciplineDao(con);
		groupDao = new GroupDao(con);
		professorDao = new ProfessorDao(con);
		libraryAbonamentDao = new LibraryAbonamentDao(con);
		searchDao = new SearchDao(con);
	}

	private List<Student> listStudents() {
		return studentDao.getAllStudents();
	}

	private List<Adress> listAdresses() {
		return adressDao.getAllAdresses();
	}

	private List<Phone> listPhones() {
		return phoneDao.getAllPhones();
	}

	public List<PhoneType> listPhoneTypes() {
		return phoneDao.getAllPhoneTypes();
	}

	private List<Mark> listMarks() {
		return markDao.getAllMarks();
	}

	public List<Discipline> listDisciplines() {
		return disciplineDao.getAllDisciplines();
	}

	public List<Group> listGroups() {
		return groupDao.getAllGroups();
	}

	public List<Professor> listProfessors() {
		return professorDao.getAllProfessors();
	}

	public List<Professor> getListProfessorsById(int discipline_id) {
		List<Professor> listProfessors = new ArrayList<>();
		for (Professor professor : professorDao.getProfessorsId(discipline_id)) {
			Professor newProfessor = professorDao.getProfessorById(professor.getProfessor_id());
			listProfessors.add(newProfessor);
		}
		return listProfessors;
	}

	public List<Student> getCompletListStudents() {

		List<Student> studentList = listStudents();
		List<Adress> adressList = listAdresses();
		List<Phone> phoneList1 = listPhones();
		List<Mark> markList1 = listMarks();

		for (Student student : studentList) {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			List<Phone> phoneList = new ArrayList<>();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<Mark> markList = new ArrayList<>();
			for (Adress adress : adressList) {
				if (student.getAdress_id() == adress.getAdress_id()) {
					student.setAdress(adress);
				}
			}
			for (Mark mark : markList1) {
				if (student.getStudent_id() == mark.getStudent_id()) {
					markList.add(mark);
				}
			}
			student.setMark(markList);

			for (Phone phone : phoneList1) {
				if (student.getStudent_id() == phone.getPerson_id()) {
					phoneList.add(phone);
				}

			}
			student.setPhoneList(phoneList);
		}
		return studentList;
	}

	public boolean updateAbonament(LibraryAbonament abonament) {
		int student_id = abonament.getAbonament_id();
		return libraryAbonamentDao.updateAbonament(abonament, student_id);
	}

	public LibraryAbonament getAbonamentById(int student_id) {
		return libraryAbonamentDao.getAbonamentByID(student_id);
	}

	public Group getGroupById(int student_id) {
		return groupDao.getGroupById(student_id);
	}

	public List<Student> getStudentsBySearch(Search search) {
		TreeSet<Integer> listIds = searchDao.listSearch(search);
		List<Student> listStudents = new ArrayList();
		for (int id : listIds) {
			Student newStudent = getStudentById(id);
			listStudents.add(newStudent);
		}
		return listStudents;
	}

	public Student getStudentById(int student_id) {
		// get from student
		Student newStudent = studentDao.getStudentById(student_id);
		// get from adress
		newStudent.setAdress(adressDao.getAdressByID(newStudent.getAdress_id()));
		// get from phone
		newStudent.setPhoneList(phoneDao.getPhonesByID(student_id));
		// get from group
		newStudent.setGroup(groupDao.getGroupById(student_id));
		// get from discipline
		newStudent.setDisciplineList(disciplineDao.getDisciplinesById(student_id));
		List<Mark> marks = new ArrayList<>();
		for (Mark mark : listMarks()) {
			if (student_id == mark.getStudent_id()) {
				marks.add(mark);
			}
		}
		newStudent.setMark(marks);
		return newStudent;
	}

	public boolean addMark(Mark newMark) {
		return markDao.addMark(newMark);
	}

	public boolean updateStudent(Student student) throws SQLException {
		boolean eventStatus = false;
		try {
			con.setAutoCommit(false);
			eventStatus = studentDao.updateStudent(student);
			if (adressDao.checkAdress(student) != 0) {
				eventStatus = adressDao.updatePersonAdress(adressDao.checkAdress(student), student.getStudent_id());
			} else if (adressDao.checkAdress(student) == 0) {
				adressDao.addAdress(student);
				eventStatus = adressDao.updatePersonAdress(adressDao.checkAdress(student), student.getStudent_id());
			}
			phoneDao.deletePersonPhone(student.getStudent_id());
			for (Phone phone : student.getPhoneList()) {
				if (phoneDao.checkPhone(phone) != 0) {
					eventStatus = phoneDao.addPersonPhone(phoneDao.checkPhone(phone), student.getStudent_id());
				} else if (phoneDao.checkPhone(phone) == 0) {
					phoneDao.addPhone(phone);
					eventStatus = phoneDao.addPersonPhone(phoneDao.checkPhone(phone), student.getStudent_id());
				}
			}
			eventStatus = studentDao.updateStudent(student.getGroup().getGroup_id(), student.getStudent_id());
			con.commit();
		} catch (SQLException e) {
			con.rollback();
		}
		return eventStatus;
	}

	public boolean deleteStudents(List<Integer> idList) throws SQLException {
		boolean eventStatus = false;
		for (int id : idList) {
			if (deleteStudent(id)) {
				eventStatus = true;
			}
		}
		return eventStatus;
	}

	public boolean deleteStudent(int student_id) throws SQLException {
		boolean eventStatus = false;
		try {
			con.setAutoCommit(false);
			// delete from mark
			markDao.deleteMark(student_id);
			// delete from student_discipline
			disciplineDao.deleteStudentDiscipline(student_id);
			// delete from student
			studentDao.deleteStudent(student_id);
			// delete from person_adress
			adressDao.deletePersonAdress(student_id);
			for (Adress adress : listAdresses()) {
				boolean delete = false;
				for (int adress_id : adressDao.getAdressIds()) {
					if (adress_id == adress.getAdress_id()) {
						delete = true;
					}
				}
				if (!delete) {
					adressDao.deleteAdress(adress.getAdress_id());
				}
			}
			// delete from person_phone
			phoneDao.deletePersonPhone(student_id);
			// delete from person
			studentDao.deleteStudentData(student_id);
			eventStatus = true;
			con.commit();
		} catch (SQLException e) {
			con.rollback();
		}
		return eventStatus;
	}

	public boolean addStudent(Student newStudent) throws SQLException {
		boolean eventStatus = false;
		try {
			con.setAutoCommit(false);
			studentDao.addStudentData(newStudent);
			Student student = new Student();
			student.setStudent_id(studentDao.getStudentID());
			if (adressDao.checkAdress(newStudent) != 0) {
				adressDao.addPersonAdress(adressDao.checkAdress(newStudent), student.getStudent_id());
			} else if (adressDao.checkAdress(newStudent) == 0) {
				adressDao.addAdress(newStudent);
				adressDao.addPersonAdress(adressDao.checkAdress(newStudent), student.getStudent_id());
			}
			for (Phone phone : newStudent.getPhoneList()) {
				if (phoneDao.checkPhone(phone) != 0) {
					phoneDao.addPersonPhone(phoneDao.checkPhone(phone), student.getStudent_id());
				} else if (phoneDao.checkPhone(phone) == 0) {
					phoneDao.addPhone(phone);
					phoneDao.addPersonPhone(phoneDao.checkPhone(phone), student.getStudent_id());
				}
			}
			studentDao.addStudent(student.getStudent_id(), newStudent.getGroup().getGroup_id());
			libraryAbonamentDao.addLibraryAbonament(student.getStudent_id());
			eventStatus = true;
			con.commit();
		} catch (SQLException e) {
			con.rollback();
		}
		return eventStatus;
	}

}
