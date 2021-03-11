package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EthcinityModel {
	
	private Connection connection;
	//private PreparedStatement pStmt;
	private Statement stmt;
	private ResultSet rs;
	
	//get ethcinity id from db according to ethcinity name
	public int getEthcinityId(String ethcinityName) throws SQLException {
		int ethcinityId= 0;
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery("select ethcinity_id from ethcinities where name='"+ethcinityName+"';");
		while(rs.next()) {
			ethcinityId=rs.getInt("ethcinity_id");
			
		}
		return ethcinityId;
	}
	
	//get ethcinity name from db according to ethcinity id
	public String getEthcinityName(Integer ethcinityId) throws SQLException {
		String ethcinityName= "Bamar";
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery("select name from ethcinities where ethcinity_id='"+ethcinityId+"';");
		while(rs.next()) {
			ethcinityName=rs.getString("name");
			
		}
		return ethcinityName;
	}
	
	//get ethcinity from db to ethcinity combox
	public ObservableList<String> getEthcinityList(String sql) throws SQLException{
		ObservableList<String> ethcinityList = FXCollections.observableArrayList();
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			ethcinityList.add(rs.getString("name")
					);
		}
		System.out.println(ethcinityList);
		return ethcinityList;
		
	}

}
