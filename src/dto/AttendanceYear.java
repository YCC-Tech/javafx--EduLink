package dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AttendanceYear {
	private SimpleIntegerProperty attendance_year_id;
	private SimpleStringProperty name;
	
	public AttendanceYear(String name) {
		this.name = new SimpleStringProperty(name);
	}
	
	public AttendanceYear(Integer id, String name) {
		this.attendance_year_id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
	}

	public int getAttendanceYearId() {
		return attendance_year_id.get();
	}

	public void setAttendanceYearId(int attendance_year_id) {
		this.attendance_year_id.set(attendance_year_id);
	}
	
	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}
}
