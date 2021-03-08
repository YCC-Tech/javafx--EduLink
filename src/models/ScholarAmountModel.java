package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnection;

public class ScholarAmountModel {
	private final DBConnection dbConnection;
	private Connection connection;
	private ResultSet resultSet;

	public ScholarAmountModel() {
		super();
		this.dbConnection = new DBConnection();
		this.connection = DBConnection.getConnection();
	}
	
	public int getScholarAmount(int universityId, String attendanceYear) {
		int amount = 0;
		
		String sql = "SELECT *\r\n"
				+ "FROM scholarship_amounts\r\n"
				+ "WHERE university_id = " + universityId + " AND attendance_year_id = (SELECT attendance_year_id FROM attendance_years WHERE name = '" + attendanceYear + "');";
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) amount = resultSet.getInt("amount");
			
			System.out.println(amount);
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return amount;
	}
}
