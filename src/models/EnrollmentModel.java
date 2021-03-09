package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import database.DBConnection;
import dto.Enrollment;
import dto.Student;

public class EnrollmentModel {
	
	private final DBConnection dbConnection;
	private Connection connection;
	private ResultSet resultSet;
	private PreparedStatement pStmt;

	public EnrollmentModel() {
		super();
		this.dbConnection = new DBConnection();
		this.connection = DBConnection.getConnection();
	}
	
	public boolean saveStudent(Enrollment enrollment) throws SQLException {
		var isSave = true;
		connection = DBConnection.getConnection();
		
		System.out.println("before");
		
		pStmt = connection.prepareStatement("INSERT INTO `enrollments` "
				+ "(`student_id`, `major_id`, `attendance_year_id`, `is_active`, `university_id`)"
				+ "VALUES (?, ?, ?, ?, ?);"
				);
		
		pStmt.setInt(1, enrollment.getStudent_id());
		pStmt.setInt(2, enrollment.getMajor_id());
		pStmt.setInt(3, enrollment.getAttendance_year_id());
		pStmt.setInt(4, enrollment.getIs_active());
		pStmt.setInt(5, enrollment.getUniversity_id());
		
		isSave = pStmt.execute();
		connection.close();

		return isSave;
		
		
	}

}
