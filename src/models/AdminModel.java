package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnection;
import dto.Admin;
import dto.University;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminModel {
	private final DBConnection dbConnection;
	private Connection connection;
	private ResultSet resultSet;

	public AdminModel() {
		super();
		this.dbConnection = new DBConnection();
		this.connection = DBConnection.getConnection();
	}
	
	public ObservableList<Admin> getAdminList() {
		ObservableList<Admin> admins = FXCollections.observableArrayList();
		
		String sql = "SELECT * FROM users";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				admins.add(new Admin(
						resultSet.getInt("user_id"),
						resultSet.getString("name"),
						resultSet.getString("username")
						)
				);
			}
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return admins;
	}
	
	public int deleteAdmin(int userId) {
		int deleted = 0;
		
		String sql = "DELETE FROM users WHERE user_id = '" + userId + "'";
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			deleted = statement.executeUpdate();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return deleted;
	}
	
	public int createAdmin(Admin admin) {
		int inserted = 0;
		Date sqlDate = new Date(System.currentTimeMillis());
		
		String sql = ""
				+ "INSERT INTO users (name, username, password, created_at) "
				+ "VALUES ('" + admin.getName() + "', '" + admin.getUsername() + "', '" + admin.getPassword() + "', '" + sqlDate + "');";
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			inserted = statement.executeUpdate();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return inserted;
	}
	
	public int updateAdmin(Admin admin) {
		int updated = 0;
		
		String sql = "UPDATE users SET name = '" + admin.getName() + "', username = '" + admin.getUsername() + "' WHERE user_id = " + admin.getUserId() + ";";
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			updated = statement.executeUpdate();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return updated;
	}
	
	public Admin getAdmin(int userId) {
		Admin admin = null;
		
		String sql = "SELECT name, username FROM users WHERE user_id = " + userId + ";";
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				admin = (new Admin(
						resultSet.getString("name"),
						resultSet.getString("username")
					));
			}
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return admin;
	}
}
