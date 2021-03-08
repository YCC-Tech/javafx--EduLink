package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DBConnection;
import dto.University;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UniversityModel {

	private final DBConnection dbConnection;
	private Connection connection;
	private ResultSet resultSet;

	public UniversityModel() {
		super();
		this.dbConnection = new DBConnection();
		this.connection = DBConnection.getConnection();
	}

	public ObservableList<University> getTransactionUniversities(String year, String month) {
		ObservableList<University> universities = FXCollections.observableArrayList();
		
		String sql = "SELECT "
				+ "u.*, "
				+ "r.name as region, "
				+ "count(e.student_id) AS student_count "
				+ "FROM universities u "
				+ "JOIN regions r ON u.region_id = r.region_id "
				+ "JOIN enrollments e ON e.university_id = u.university_id "
				+ "JOIN transactions t ON t.student_id = e.student_id "
				+ "WHERE u.region_id = r.region_id "
				+ "AND t.taken_out_at between '" + year + "-" + month + "-01' AND '" + year + "-" + month + "-30' "
				+ "GROUP BY university_id;";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				universities.add(new University(
						resultSet.getInt("university_id"),
						resultSet.getString("name"),
						resultSet.getString("short_name"),
						resultSet.getString("region"),
						resultSet.getInt("student_count")
						)
				);
			}
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return universities;
	}
}
