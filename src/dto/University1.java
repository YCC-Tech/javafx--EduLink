package dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class University1 {
	private SimpleIntegerProperty university_id;
	private SimpleStringProperty name;
	private SimpleStringProperty short_name;
	private SimpleStringProperty region;
	private SimpleIntegerProperty student_count;

	public University1(Integer id, String name, String short_name, String region, Integer count) {
		this.university_id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.short_name = new SimpleStringProperty(short_name);
		this.region = new SimpleStringProperty(region);
		this.student_count = new SimpleIntegerProperty(count);
	}

	public int getUniversityId() {
		return university_id.get();
	}

	public void setUniversityId(int university_id) {
		this.university_id.set(university_id);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getShortName() {
		return short_name.get();
	}

	public void setShortName(String shortName) {
		this.short_name.set(shortName);
	}

	public String getRegion() {
		return region.get();
	}

	public void setRegion(String region) {
		this.region.set(region);
	}

	public int getStudentCount() {
		return student_count.get();
	}

	public void setStudentCount(int studentCount) {
		this.student_count.set(studentCount);
	}

}
