package Model;

import java.util.Date;

public class LibraryAbonament {
	private int abonament_id;
	private AbonamentStatus status;
	private Date startDate;
	private Date endDate;

	public LibraryAbonament() {
		super();

	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public AbonamentStatus getStatus() {
		return status;
	}

	public void setStatus(AbonamentStatus status) {
		this.status = status;
	}

	public int getAbonament_id() {
		return abonament_id;
	}

	public void setAbonament_id(int abonament_id) {
		this.abonament_id = abonament_id;
	}

}
