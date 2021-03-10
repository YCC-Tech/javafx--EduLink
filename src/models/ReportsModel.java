package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import database.DBConnection;
import dto.University;
import dto.DonationDto;
import dto.ScholarshipDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReportsModel {
	
	private Integer lastRemainingOf2020Dec = 100000;

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
	
	
	public  ObservableList<ScholarshipDto> getScholarshipList(Integer year, Integer month, String uniShortName){
		ObservableList<ScholarshipDto> scholarshipList = FXCollections.observableArrayList();
		String sql="";
		String dateString="";
		String uniDateString="";
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
		
		
		  if(uniShortName==null) { 
			  uniDateString +=dateString;
		  }
		  else { 
			  uniDateString += dateString+"' and u.short_name='"+uniShortName;
		  }
		 
		if(year==0 && month==0 && uniShortName==null) {
			System.out.println("work 1if");
			sql = "select t.transaction_id, s.name, u.name, s.phone, t.taken_out_at\r\n"
					+ "from transactions t, students s, universities u, enrollments e, majors m\r\n"
					+ "where t.student_id = s.student_id and s.student_id = e.student_id and e.major_id = m.major_id and m.university_id = u.university_id;";
		}
		
		else {
			System.out.println("work 4if");
			sql = "select t.transaction_id, s.name, u.name, s.phone, t.taken_out_at\r\n"
					+ "from transactions t, students s, universities u, enrollments e, majors m\r\n"
					+ "where t.student_id = s.student_id and s.student_id = e.student_id and\r\n"
					+ "e.major_id = m.major_id and m.university_id = u.university_id and\r\n"
					+ "t.taken_out_at like '"+uniDateString+"';";
		}
				
		
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
	
	public ObservableList<String> getUniversityList(){
		ObservableList<String> universityList = FXCollections.observableArrayList();
		
		String sql = "SELECT short_name FROM universities;";
				
		
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
	
	public ObservableList<ScholarshipDto> getUniversityFilter(String short_name){
		ObservableList<ScholarshipDto> scholarshipList = FXCollections.observableArrayList();
		String sql="";
		
		if(short_name == null) {
			sql = "select t.transaction_id, s.name, u.name, s.phone, t.taken_out_at\r\n"
					+ "from transactions t, students s, universities u, enrollments e, majors m\r\n"
					+ "where t.student_id = s.student_id and s.student_id = e.student_id and e.major_id = m.major_id and m.university_id = u.university_id;";
		}
		else {
			sql = "select t.transaction_id, s.name, u.name, s.phone, t.taken_out_at\r\n"
					+ "from transactions t, students s, universities u, enrollments e, majors m\r\n"
					+ "where t.student_id = s.student_id and s.student_id = e.student_id and e.major_id = m.major_id and m.university_id = u.university_id\r\n"
					+ "and u.short_name='"+short_name+"';";
		}
		
				
		
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
	
}
