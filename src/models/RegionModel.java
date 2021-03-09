package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RegionModel {
	
	private Connection connection;
	//private PreparedStatement pStmt;
	private Statement stmt;
	private ResultSet rs;
	
	//get region id from db according to region name
	public int getRegionId(String regionName) throws SQLException {
		int regionId= 0;
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery("select region_id from regions where name='"+regionName+"';");
		while(rs.next()) {
			regionId=rs.getInt("region_id");
			
		}
		return regionId;
	}
	
	//get region from db to region combox
	public ObservableList<String> getReligionList(String sql) throws SQLException{
		ObservableList<String> regionList = FXCollections.observableArrayList();
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			regionList.add(rs.getString("name")
					);
		}
		System.out.println(regionList);
		return regionList;
		
	}

}
