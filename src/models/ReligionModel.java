package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReligionModel {
	
	private Connection connection;
	//private PreparedStatement pStmt;
	private Statement stmt;
	private ResultSet rs;
	
	//get religion from db to religion combox
	public ObservableList<String> getReligionList(String sql) throws SQLException{
		ObservableList<String> religionList = FXCollections.observableArrayList();
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			religionList.add(rs.getString("name")
					);
		}
		System.out.println(religionList);
		return religionList;
		
	}
	
	//get religion name from db according to religion id
	public String getReligionName(Integer religionId) throws SQLException {
		String religionName= "Buddhism";
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery("select name from religions where religion_id='"+religionId+"';");
		while(rs.next()) {
			religionName=rs.getString("name");
			
		}
		return religionName;
	}
	
	
	//get religion id from db according to religion name
	public int getReligionId(String religionName) throws SQLException {
		int religionId= 0;
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery("select religion_id from religions where name='"+religionName+"';");
		while(rs.next()) {
			religionId=rs.getInt("religion_id");
			
		}
		return religionId;
	}

}
