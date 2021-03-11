package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MajorModel {
	
	private Connection connection;
	//private PreparedStatement pStmt;
	private Statement stmt;
	private ResultSet rs;
	
	//get major name  according to student_id
		public String getMajorName(Integer student_id) throws SQLException {
			String majorName= "";
			connection = DBConnection.getConnection();
			stmt = connection.createStatement();
			
			String sql = "SELECT m.name as major "
					+"FROM students s " 
					+"JOIN enrollments e ON s.student_id = e.student_id " 
					+"JOIN attendance_years a ON e.attendance_year_id = a.attendance_year_id "
					+"JOIN universities u ON a.university_id = u.university_id " 
					+"JOIN majors m ON e.major_id = m.major_id and s.student_id='"+student_id+"';";
					
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				rs = statement.executeQuery();
				
				while (rs.next()) {
					majorName=rs.getString("major");
						}
					} 
					catch (SQLException e) {
						System.out.println(e.getMessage());
					}
			System.out.println(majorName);
			return majorName;
		}
	
	//get major id from db according to major name
	public int getMajorId(String majorName,Integer attendance_year_id,Integer university_id) throws SQLException {
		int majorId= 0;
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery("select major_id from majors where name='"+majorName+"'and attendance_year_id='"+attendance_year_id+
				"'and university_id='"+university_id+"';");
		while(rs.next()) {
			majorId=rs.getInt("major_id");
			
		}
		return majorId;
	}
	
	//get major list from db to major combo box filtered by university and academic year
		public ObservableList<String> getMajorList(String sql) throws SQLException{
			ObservableList<String> majorList = FXCollections.observableArrayList();
			connection = DBConnection.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				majorList.add(rs.getString("name")
						);
			}
			System.out.println(majorList);
			return majorList;
			
		}

}
