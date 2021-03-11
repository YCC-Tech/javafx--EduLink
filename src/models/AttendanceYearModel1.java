package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AttendanceYearModel1 {

	private Connection connection;
	//private PreparedStatement pStmt;
	private Statement stmt;
	private ResultSet rs;
	
	//get attendance name  according to student_id
	public String getAttendanceYearName(Integer student_id) throws SQLException {
		String attendanceYearName= "";
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		
		String sql = "SELECT a.name as attendance_year "
				+"FROM students s " 
				+"JOIN enrollments e ON s.student_id = e.student_id " 
				+"JOIN attendance_years a ON e.attendance_year_id = a.attendance_year_id "
				+"JOIN universities u ON a.university_id = u.university_id " 
				+"JOIN majors m ON e.major_id = m.major_id and s.student_id='"+student_id+"';";
				
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			rs = statement.executeQuery();
			
			while (rs.next()) {
				attendanceYearName=rs.getString("attendance_year");
					}
				} 
				catch (SQLException e) {
					System.out.println(e.getMessage());
				}
		System.out.println(attendanceYearName);
		return attendanceYearName;
	}
	
	//get academic id from db according to university academic name
		public int getYearId(String yearName,Integer universtity_id) throws SQLException {
			int attendance_year_id= 0;
			connection = DBConnection.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT attendance_year_id FROM attendance_years where university_id='"+universtity_id+"';");
			while(rs.next()) {
				attendance_year_id=rs.getInt("attendance_year_id");
				
			}
			return attendance_year_id;
		}
	
	//get academic year list from db to year combo box filtered by univeristy
	public ObservableList<String> getYearList(String sql) throws SQLException{
		ObservableList<String> yearList = FXCollections.observableArrayList();
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			yearList.add(rs.getString("name")
					);
		}
		System.out.println(yearList);
		return yearList;
		
	}
	
	//get university id from db according to university name
	public int getUniId(String uniName) throws SQLException {
		int uniId= 0;
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery("select university_id from universities where name='"+uniName+"';");
		while(rs.next()) {
			uniId=rs.getInt("university_id");
			
		}
		return uniId;
	}
}
