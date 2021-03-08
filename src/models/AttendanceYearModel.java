package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnection;
import dto.AttendanceYear;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AttendanceYearModel {

	private final DBConnection dbConnection;
	private Connection connection;
	private ResultSet resultSet;

	public AttendanceYearModel() {
		super();
		this.dbConnection = new DBConnection();
		this.connection = DBConnection.getConnection();
	}
	
	public ObservableList<String> getAttendanceYearsByUniversityId(int universityId) {
		ObservableList<String> attendanceYears = FXCollections.observableArrayList();
		
		String sql = "SELECT * FROM attendance_years WHERE university_id = " + universityId + ";";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				attendanceYears.add(resultSet.getString("name"));
			}
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return attendanceYears;
	}
}
