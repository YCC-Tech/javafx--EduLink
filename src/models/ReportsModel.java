package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Utilities.MutateMonth;
import database.DBConnection;
import dto.DonationDto;
import dto.ScholarshipDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

public class ReportsModel {
	
	//private Integer lastRemainingOf2020Dec = 100000;

	// To connect to database
	private final DBConnection dbConnection;
	private Connection connection;
	private ResultSet resultSet;

	public ReportsModel() {
		super();
		this.dbConnection = new DBConnection();
		this.connection = DBConnection.getConnection();
	}
	
	
	public ObservableList<DonationDto> getDonationList(Integer year, Integer month){
		
		ObservableList<DonationDto> donationList = FXCollections.observableArrayList();
		
		String sql="";
		String dateString="";
		if(year!=0) {
			dateString +=year;
		}
		else if(year==0){
			dateString +="____";
		}
		if(month !=0 && 0<month && month<10) {
			dateString +="-0"+month+"%";
		}
		else if(month!=0 && month>11) {
			dateString +="-"+month+"%";
		}
		else if(month==0) {
			dateString +="%";
		}
		if(year==0 && month==0) {
			sql = "select donations.donated_at,donations.amount,donations.description,donators.name, donators.phone\r\n"
					+ "from donations, donators\r\n"
					+ "where donations.donator_id=donators.donator_id;";
		}
		else {
			sql = "select donations.donated_at,donations.amount,donations.description,donators.name, donators.phone\r\n"
					+ "from donations, donators\r\n"
					+ "where donations.donator_id=donators.donator_id and donations.donated_at like '"+dateString+"';";
		}
		
		
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			resultSet = statement.executeQuery();
			
			Integer countNo=1;
			while(resultSet.next()) {
				

				donationList.add(new DonationDto(
						countNo,
						resultSet.getString("donated_at").toString(),
						resultSet.getString("name"),
						resultSet.getInt("amount"),
						resultSet.getString("phone"),				
						resultSet.getString("Description")
						)
				);

				countNo++;
			}
			
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return donationList;
	}
	
	// To retrieve data about Scholarship from database
	public  ObservableList<ScholarshipDto> getScholarshipList(Integer year, Integer month, String uniShortName){
		
		ObservableList<ScholarshipDto> scholarshipList = FXCollections.observableArrayList();
		
		String sql=""; // to set query
		String dateString=""; /* In the database, Date format is yyyy-mm-dd, for such these months (01 and 12)
								 See in Utilities.MutateMonth.java
								*/
		String uniDateString="";
		
		// variable to put in queries for year and month
		if(year!=0) {
			dateString +=year;
		}
		else if(year==0){
			dateString +="____";
		}
		if(month !=0 && 0<month && month<10) {
			dateString +="-0"+month+"%";
		}
		else if(month!=0 && month>11) {
			dateString +="-"+month+"%";
		}
		else if(month==0) {
			dateString +="%";
		}
		
		// variable to put in queries for year, month and university
		  if(uniShortName==null) { 
			  uniDateString +=dateString;
		  }
		  else { 
			  uniDateString += dateString+"' and u.short_name='"+uniShortName;
		  }
		 
		if(year==0 && month==0 && uniShortName==null) {
			// No drop down is selected
			sql = "select t.transaction_id, s.name, u.name, s.phone, t.taken_out_at\r\n"
					+ "from transactions t, students s, universities u, enrollments e, majors m\r\n"
					+ "where t.student_id = s.student_id and s.student_id = e.student_id and e.major_id = m.major_id and m.university_id = u.university_id;";
		}
		
		else {
			// One of the drop downs is selected
			sql = "select t.transaction_id, s.name, u.name, s.phone, t.taken_out_at\r\n"
					+ "from transactions t, students s, universities u, enrollments e, majors m\r\n"
					+ "where t.student_id = s.student_id and s.student_id = e.student_id and\r\n"
					+ "e.major_id = m.major_id and m.university_id = u.university_id and\r\n"
					+ "t.taken_out_at like '"+uniDateString+"';";
		}
				
		// Prepare statement
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			resultSet = statement.executeQuery();
			
			Integer countNo=1;
			while(resultSet.next()) {
				

				scholarshipList.add(new ScholarshipDto(
						countNo,
						//resultSet.getInt("transaction_id"),
						resultSet.getString("name"),
						resultSet.getString("u.name"),
						resultSet.getString("phone"),				
						resultSet.getString("taken_out_at")
						)
				);
				countNo++;

			}
			
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return scholarshipList;
	}
	
	// To retrieve data from universities table
	public ObservableList<String> getUniversityList(){
		ObservableList<String> universityList = FXCollections.observableArrayList();
		
		String sql = "SELECT short_name FROM foundation.universities;";
				
		// Prepare statement
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			resultSet = statement.executeQuery();			
			
			while(resultSet.next()) {

				universityList.add(resultSet.getString("short_name"));
			}
			
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return universityList;
	}
	
	// To retrieve data from database for PieCharts
	public ObservableList<PieChart.Data> getChartData(String sql, String firstString, String secondString, boolean isMonthNameIn){
		 ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
	    		if (isMonthNameIn) {
					pieData.add(new PieChart.Data(MutateMonth.getStringMonth(resultSet.getString(firstString)), resultSet.getInt(secondString)));
				}
				else{
					pieData.add(new PieChart.Data(resultSet.getString(firstString), resultSet.getInt(secondString)));
				}

	    	}
			
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return pieData;
	}
	
}
