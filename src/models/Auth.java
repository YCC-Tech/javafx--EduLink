package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnection;

public class Auth {

	private final DBConnection dbConnection;
	private Connection connection;
	private ResultSet resultSet;

	public Auth() {
		super();
		this.dbConnection = new DBConnection();
		this.connection = DBConnection.getConnection();
	}

	public boolean checkLogin(String username, String password) {
		boolean isOk = false;
		
		String sql = "SELECT * FROM users "
				+ "WHERE username = '" + username + "' AND password = '" + password + "';";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			resultSet = statement.executeQuery();

			while(resultSet.next()) {
				if (resultSet.getString("username").equals(username) && resultSet.getString("password").equals(password)) {
					isOk = true;
				}
				else isOk = false;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return isOk;
	}

}
