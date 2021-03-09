package dto;

import java.sql.Date;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student {

	private SimpleIntegerProperty student_id;
	private SimpleStringProperty name;
	private SimpleIntegerProperty gender;
	private SimpleStringProperty nrc;
	private SimpleStringProperty birthday;
	private SimpleStringProperty phone;
	private SimpleStringProperty address;
	private SimpleStringProperty hostel_address;
	private SimpleIntegerProperty religion_id;
	private SimpleIntegerProperty township_id;
	private SimpleIntegerProperty ethcinity_id;
	private SimpleStringProperty photo_url;
	
	private SimpleStringProperty major;
	private SimpleStringProperty university;
	private SimpleStringProperty attendance_year;
	
	/*private String nrcPrefix;
	private String nrcTownship;
	private String nrcNational;
	private String nrcNo;
	private String ethcinity;*/
	
	public String getNrc() {
		return nrc.get();
	}
	
	public String getMajor() {
		return major.get();
	}

	

	public String getUniversity() {
		return university.get();
	}

	

	public String getAttendance_year() {
		return attendance_year.get();
	}

	//for student list table
	public Student(Integer student_id, String name, String phone, String attendance_year, String major,String university ) {
		super();
		this.student_id = new SimpleIntegerProperty(student_id);
		this.name = new SimpleStringProperty(name);
		this.phone = new SimpleStringProperty(phone);
		this.attendance_year = new SimpleStringProperty(attendance_year);
		this.major = new SimpleStringProperty(major);
		this.university = new SimpleStringProperty(university);
		
	}
	
	//for new student form 
	public Student(String name, Integer gender_id, String nrc, String birthday, String phone, String address,String hostel_address,Integer religion_id ,Integer township_id,Integer ethcinity_id ) {
		super();
		this.name = new SimpleStringProperty(name);
		this.gender = new SimpleIntegerProperty(gender_id);
		this.nrc = new SimpleStringProperty(nrc);
		this.birthday = new SimpleStringProperty(birthday);
		this.phone = new SimpleStringProperty(phone);
		this.address = new SimpleStringProperty(address);
		this.hostel_address = new SimpleStringProperty(hostel_address);
		this.religion_id = new SimpleIntegerProperty(religion_id);
		this.religion_id = new SimpleIntegerProperty(religion_id);
		this.township_id = new SimpleIntegerProperty(township_id);
		this.ethcinity_id = new SimpleIntegerProperty(ethcinity_id);
		
	}
	
	public Integer getStudent_id() {
		return student_id.get();
	}
	
	public void setStudentId(int student_id) {
		this.student_id.set(student_id);
	}
	
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	
	public Integer getGender() {
		return gender.get();
	}
	
	/*(public String getNrc() {
		return nrc.get();
	}*/
	
	public String getBirthday() {
		return birthday.get();
	}
	
	public String getPhone() {
		return phone.get();
	}
	
	public void setPhone(String phone) {
		this.name.set(phone);
	}
	
	public String getAddress() {
		return address.get();
	}
	
	public String getHostel_address() {
		return hostel_address.get();
	}
	
	public Integer getReligion_id() {
		return religion_id.get();
	}
	
	public Integer getTownship_id() {
		return township_id.get();
	}
	
	public Integer getEthcinity_id() {
		return ethcinity_id.get();
	}
	
	public String getPhoto_url() {
		return photo_url.get();
	}
	
}
