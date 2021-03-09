package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UniversityModel1 {
	
	private Connection connection;
	//private PreparedStatement pStmt;
	private Statement stmt;
	private ResultSet rs;
	
	//get university id from db according to university name
	public int getUniverstiyId(String universityName) throws SQLException {
		int universityId= 0;
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery("select university_id from universities where name='"+universityName+"';");
		while(rs.next()) {
			universityId=rs.getInt("university_id");
			
		}
		return universityId;
	}
	
	//get university list from db to university combo box
	public ObservableList<String> getUniList(String sql) throws SQLException{
		ObservableList<String> uniList = FXCollections.observableArrayList();
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			uniList.add(rs.getString("name")
					);
		}
		System.out.println(uniList);
		return uniList;
		
	}

}
