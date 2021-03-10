package dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ScholarshipDto {

	private SimpleIntegerProperty countNo;
	
	private SimpleStringProperty studentName;
	
	private SimpleStringProperty universityName;
	
	private SimpleStringProperty studentPhone;
	
	private SimpleStringProperty transcationDate;

	
	public ScholarshipDto(Integer countNo, String studentName, String universityName, String studentPhone,
			String transcationDate) {
		super();
		this.countNo = new SimpleIntegerProperty(countNo);
		this.studentName = new SimpleStringProperty(studentName);
		this.universityName = new SimpleStringProperty(universityName);
		this.studentPhone = new SimpleStringProperty(studentPhone);
		this.transcationDate = new SimpleStringProperty(transcationDate);
	}

	public Integer getCountNo() {
		return countNo.get();
	}

	public String getStudentName() {
		return studentName.get();
	}

	public String getUniversityName() {
		return universityName.get();
	}

	public String getStudentPhone() {
		return studentPhone.get();
	}

	public String getTranscationDate() {
		return transcationDate.get();
	}

	}
