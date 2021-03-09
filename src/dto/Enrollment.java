package dto;

import javafx.beans.property.SimpleIntegerProperty;

public class Enrollment {
	
	private SimpleIntegerProperty enrollment_id;
	private SimpleIntegerProperty student_id;
	private SimpleIntegerProperty major_id;
	private SimpleIntegerProperty attendance_year_id;
	private SimpleIntegerProperty is_active;
	private SimpleIntegerProperty university_id;
	
	//for new student form 
		public Enrollment(Integer student_id,Integer university_id,Integer attendance_year_id,Integer major_id,Integer is_active ) {
			super();
			
			this.student_id = new SimpleIntegerProperty(student_id);
			this.major_id = new SimpleIntegerProperty(major_id);
			this.attendance_year_id = new SimpleIntegerProperty(attendance_year_id);
			this.is_active = new SimpleIntegerProperty(is_active);
			this.university_id = new SimpleIntegerProperty(university_id);
			
			
		}
	
	
	public Integer getEnrollment_id() {
		return enrollment_id.get();
	}
	
	public Integer getStudent_id() {
		return student_id.get();
	}
	
	public Integer getMajor_id() {
		return major_id.get();
	}
	
	public Integer getAttendance_year_id() {
		return attendance_year_id.get();
	}
	
	public Integer getIs_active() {
		return is_active.get();
	}
	
	public Integer getUniversity_id() {
		return university_id.get();
	}
	
	
	

}
