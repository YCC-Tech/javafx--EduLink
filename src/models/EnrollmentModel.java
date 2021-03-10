package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import database.DBConnection;
import dto.Enrollment;
import dto.Parent1;
import dto.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

	public ObservableList<Enrollment> getEnrollmentInfoForUpdate(int studentId) {
		
		ObservableList<Enrollment> enrollment = FXCollections.observableArrayList();
		
		String sql = "SELECT u.name as university, a.name as attendanceYear, m.name as major "
				+"FROM enrollments e "
				+"JOIN attendance_years a ON e.attendance_year_id = a.attendance_year_id "
				+"JOIN universities u ON u.university_id = e.university_id " 
				+"JOIN majors m ON m.major_id = e.major_id and e.student_id='"+ studentId + "';";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				enrollment.add(new Enrollment(
						resultSet.getString("major"),
						resultSet.getString("university"),
						resultSet.getString("attendanceYear")
						)
						);
					}
				} 
				catch (SQLException e) {
					System.out.println(e.getMessage());
				}
		
		return enrollment;
	}

	public int updateStudent(Enrollment enrollment, int studentId) {
		int updated = 0;
		
		String sql = "UPDATE enrollments SET "
				+ "major_id = " + enrollment.getMajor_id() + ", "
				+ "attendance_year_id = " + enrollment.getAttendance_year_id() + ", "
				+ "is_active = " + enrollment.getIs_active() + ", "
				+ "university_id = " + enrollment.getUniversity_id() + " "
				+ "WHERE student_id = " + studentId + ";";
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			updated = statement.executeUpdate();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return updated;
	}

}
