package dto;

import javafx.beans.property.SimpleIntegerProperty;

public class ScholarAmount {
	private SimpleIntegerProperty scholarship_amount_id;
	private SimpleIntegerProperty university_id;
	private SimpleIntegerProperty attendance_year_id;
	private SimpleIntegerProperty amount;
	
	public ScholarAmount(int amount) {
		this.amount = new SimpleIntegerProperty(amount);
	}
	
	public ScholarAmount(Integer id, Integer university_id, Integer attendance_year_id, Integer amount) {
		this.scholarship_amount_id = new SimpleIntegerProperty(id);
		this.university_id = new SimpleIntegerProperty(university_id);
		this.attendance_year_id = new SimpleIntegerProperty(attendance_year_id);
		this.amount = new SimpleIntegerProperty(amount);
	}

	public int getScholarship_amount_id() {
		return scholarship_amount_id.get();
	}

	public void setScholarship_amount_id(int scholarship_amount_id) {
		this.scholarship_amount_id.set(scholarship_amount_id);
	}

	public int getUniversity_id() {
		return university_id.get();
	}

	public void setUniversity_id(int university_id) {
		this.university_id.set(university_id);
	}

	public int getAttendance_year_id() {
		return attendance_year_id.get();
	}

	public void setAttendance_year_id(int attendance_year_id) {
		this.attendance_year_id.set(attendance_year_id);
	}

	public int getAmount() {
		return amount.get();
	}

	public void setAmount(int amount) {
		this.amount.set(amount);
	}
}
