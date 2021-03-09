package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TownshipModel {
	
	private Connection connection;
	//private PreparedStatement pStmt;
	private Statement stmt;
	private ResultSet rs;
	
	//get towhship id from db according to township name
		public Integer getTownshipId(String townshipName) throws SQLException {
			int townshipId= 0;
			connection = DBConnection.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select township_id from townships where name='"+townshipName+"';");
			while(rs.next()) {
				townshipId=rs.getInt("township_id");
				
			}
			return townshipId;
		}
	
	//get township short name from db to nrc combo box
	public ObservableList<String> getTownshipNameShortList(String sql) throws SQLException{
		ObservableList<String> townshipShortList = FXCollections.observableArrayList();
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			townshipShortList.add(rs.getString("short_name")
					);
		}
		System.out.println(townshipShortList);
		return townshipShortList;
		
	}
	
	//get township name from db to township combo box
		public ObservableList<String> getTownshipList(String sql) throws SQLException{
			ObservableList<String> townshipList = FXCollections.observableArrayList();
			connection = DBConnection.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				townshipList.add(rs.getString("name")
						);
			}
			System.out.println(townshipList);
			return townshipList;
			
		}

}
