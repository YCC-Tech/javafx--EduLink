package models;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import database.DBConnection;
import dto.Donation;
import dto.Donator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DonatorModel {
	
	private Connection connection;
	
	private PreparedStatement pStmt;
		
	private Statement stmt;
	
	private ResultSet rs;

	//Get Donators List from DB
	public ObservableList<Donator> getDonatorList(String sql) throws SQLException{
		
		ObservableList<Donator> donatorList = FXCollections.observableArrayList();
		connection = DBConnection.getConnection();
		
		stmt = connection.createStatement();
		
		rs = stmt.executeQuery(sql);
		
		while ( rs.next() ) {
			donatorList.add(new Donator(rs.getInt("donator_id"), 
					rs.getString("name"),
					rs.getString("phone"), 
					rs.getString("address")));
		}
		return donatorList;
	}
	
	//Get Donations List from DB
	public ObservableList<Donation> getDonationList(String sql) throws SQLException{
		
		ObservableList<Donation> donationList = FXCollections.observableArrayList();
		connection = DBConnection.getConnection();
		
		stmt = connection.createStatement();
		
		rs = stmt.executeQuery(sql);
		
		while ( rs.next() ) {
			donationList.add(new Donation(rs.getInt("donation_id"), 
					rs.getInt("donator_id"),
					rs.getInt("amount"),
					rs.getString("donated_at"), 
					rs.getString("description")));
				
		}
		
		return donationList;
	}
	
	//For Saving New Donation
	public boolean saveDonation(Donation donation) throws SQLException {
		
		var isSave = true;
		connection = DBConnection.getConnection();
		
		pStmt = connection.prepareStatement("INSERT INTO `donations` "
				+ "(`donator_id`, `amount`, `donated_at`, `description`) "
				+ "VALUES (?,?,?,?);"
				);
		
		pStmt.setInt(1, donation.getDonatorId());
		pStmt.setInt(2, donation.getAmount());

		LocalDate date = LocalDate.parse(donation.getDonatedAt());
		java.sql.Date publishedDate = java.sql.Date.valueOf(date);
		pStmt.setDate(3, publishedDate);
		
		pStmt.setString(4, donation.getDescription());
		
		isSave = pStmt.execute();
		
		connection.close();
		return isSave;

	}
	
	
	//For saving New Donator
	public boolean saveDonator(Donator donator) throws SQLException {
		
		var isSave = true;
		connection = DBConnection.getConnection();
		
		pStmt = connection.prepareStatement("INSERT INTO `donators` "
				+ "(`name`, `phone`, `address`,`donation_count`) "
				+ "VALUES (?,?,?,?);"
				);
		pStmt.setString(1, donator.getName());
		pStmt.setString(2, donator.getPhone());
		pStmt.setString(3, donator.getAddress());
		pStmt.setInt(4, donator.getDonationCount());
		isSave = pStmt.execute();
		
		connection.close();
		return isSave;
	}
	
	//Get a Donator from DB
	public Donator getDonator(int donatorId) throws SQLException {
		
		Donator donator = null;
		
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		
		rs = stmt.executeQuery("select * from donators where donator_id = '" + donatorId + "';");
		
		while(rs.next()) {
			donator = new Donator(rs.getInt("donator_id"),
					rs.getString("name"),
					rs.getString("phone"),
					rs.getString("address"));
		}
		return donator;
	}
	
	//Last added Donator
	public Donator lastAddedDonatorId() throws SQLException {
		connection = DBConnection.getConnection();
		
		stmt = connection.createStatement();
		
		rs = stmt.executeQuery("SELECT * FROM donators ORDER BY donator_id DESC LIMIT 1;");
		
		Donator donator = null;
		
		while(rs.next()) {
			donator = new Donator(rs.getInt("donator_id"),
					rs.getString("name"),
					rs.getString("phone"),
					rs.getString("address"));
		}
				
		return donator;
	}
	
	//Last added Donation of selected Donator
	public Donation lastAddedDonation(int id) throws SQLException {
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery("select * from donations where donator_id = '" + id + "'order by donation_id desc limit 1;");
		
		Donation donation = null;
		
		while(rs.next()) {
			donation = new Donation(rs.getInt("donation_id"),
					rs.getInt("donator_id"),
					rs.getInt("amount"),
					rs.getString("donated_at"),
					rs.getString("description"));
		}
		return donation;
	}
	
	//Total Donation Amount of selected Donator
	public int totalAmountDonation(int id) throws SQLException {
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery("select sum(amount) from donations where donator_id = '" + id + "';");
		int a = 0; 
		while(rs.next()) {
			a = rs.getInt("sum(amount)");
		}
		return a;
	}
	
	//Total Donation Count of selected Donator
	public int totalDonationCount(int id) throws SQLException {
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery("select donation_count from donators where donator_id = '" + id + "';");
		int a = 0; 
		while(rs.next()) {
			a = rs.getInt("donation_count");
		}
		return a;
	}
	
	//Get region list from DB to region combo box used in Address
	public ObservableList<String> getRegionNameList(String sql) throws SQLException{
		
		ObservableList<String> regionNameList = FXCollections.observableArrayList();
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			regionNameList.add(rs.getString("name")
					);
		}
		System.out.println(regionNameList);
		return regionNameList;
		
	}
	//Get township full list from DB to township combo box used in address
	public ObservableList<String> getTownshipLongList(String sql) throws SQLException{
		
		ObservableList<String> townshipLongList = FXCollections.observableArrayList();
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			townshipLongList.add(rs.getString("name")
					);
		}
		System.out.println(townshipLongList);
		return townshipLongList;
		
	}
	
	//Get region Id from DB according to region's name
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
	
	//Increase donation count of selected Donator
	public void increaseDonationCount(int id) throws SQLException {
		
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		stmt.executeUpdate("update donators set donation_count = donation_count+1 where donator_id = '"+ id +"';");		
		
	}
	
	//For Updating Donator
	public int updateDonator(Donator donator) throws SQLException {
		
		var rowUpdated = 0;
		connection = DBConnection.getConnection();
		
		pStmt = connection.prepareStatement("UPDATE `donators` SET "
				+ "`name` = ?, "
				+ "`phone` = ?, "
				+ "`address` = ?"
				+ " WHERE (`donator_id` = ?);"
				+ ""
				);
		
		pStmt.setString(1,donator.getName());
		pStmt.setString(2,donator.getPhone());
		pStmt.setString(3,donator.getAddress());
		pStmt.setInt(4, donator.getDonatorId());
		
		
		rowUpdated = pStmt.executeUpdate();
		
		return rowUpdated;
	}

	
}
