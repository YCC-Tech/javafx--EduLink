package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;
import dto.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UniversityModel1 {

	private Connection connection;
	// private PreparedStatement pStmt;
	private Statement stmt;
	private ResultSet rs;

	/*
	 * get university name from db according to university id public String
	 * getUniversityName(Integer universityId) throws SQLException { String
	 * universityName= ""; connection = DBConnection.getConnection(); stmt =
	 * connection.createStatement(); rs =
	 * stmt.executeQuery("select name from universities where university_id='"
	 * +universityId+"';"); while(rs.next()) { universityName=rs.getString("name");
	 * 
	 * } return universityName; }
	 */

	// get university name according to student_id
	public String getUniverstiyName(Integer student_id) throws SQLException {
		String universityName = "";
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();

		String sql = "SELECT u.name as university " + "FROM students s "
				+ "JOIN enrollments e ON s.student_id = e.student_id "
				+ "JOIN attendance_years a ON e.attendance_year_id = a.attendance_year_id "
				+ "JOIN universities u ON a.university_id = u.university_id "
				+ "JOIN majors m ON e.major_id = m.major_id and s.student_id='" + student_id + "';";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			rs = statement.executeQuery();

			while (rs.next()) {
				universityName = rs.getString("university");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return universityName;
	}

	// get university id from db according to university name
	public int getUniverstiyId(String universityName) throws SQLException {
		int universityId = 0;
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery("select university_id from universities where name='" + universityName + "';");
		while (rs.next()) {
			universityId = rs.getInt("university_id");

		}
		return universityId;
	}

	// get university list from db to university combo box
	public ObservableList<String> getUniList(String sql) throws SQLException {
		ObservableList<String> uniList = FXCollections.observableArrayList();
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery(sql);

		while (rs.next()) {
			uniList.add(rs.getString("name"));
		}
		System.out.println(uniList);
		return uniList;

	}

}
