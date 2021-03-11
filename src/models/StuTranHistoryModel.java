package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;
import dto.StuTranHistory;
import dto.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class StuTranHistoryModel {
	
	private final DBConnection dbConnection;
	private Connection connection;
	private ResultSet resultSet;
	private PreparedStatement pStmt;
	private Statement stmt;
	
	public StuTranHistoryModel() {
		super();
		this.dbConnection = new DBConnection();
		this.connection = DBConnection.getConnection();
	}
	
	public ObservableList<StuTranHistory> getTranHis(int student_id) throws SQLException, FileNotFoundException{
		
			
		ObservableList<StuTranHistory> stuTranHistory = FXCollections.observableArrayList();
			
			String sql = "SELECT t.taken_out_at as t_date,h.amount as t_amt,t.remark as t_des "
							+"FROM students s "
							+"JOIN enrollments e ON s.student_id = e.student_id "
							+"JOIN attendance_years a ON e.attendance_year_id = a.attendance_year_id "
							+"JOIN universities u ON a.university_id = u.university_id "
							+"JOIN scholarship_amounts h ON h.attendance_year_id = e.attendance_year_id "
			                +"JOIN transactions t ON t.student_id = s.student_id and s.student_id='"+student_id+"';";

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				resultSet = statement.executeQuery();
				
				while (resultSet.next()) {
					stuTranHistory.add(new StuTranHistory(
							resultSet.getString("t_date"),
							resultSet.getInt("t_amt"),
							resultSet.getString("t_des")
							)
							);
						}
					} 
					catch (SQLException e) {
						System.out.println(e.getMessage());
					}
					
					return stuTranHistory;
		}
	
	public ObservableList<StuTranHistory> getTranHis(int student_id,String year,String month) throws SQLException, FileNotFoundException{
		
		
		ObservableList<StuTranHistory> stuTranHistory = FXCollections.observableArrayList();
			
			String sql = "SELECT t.taken_out_at as t_date,h.amount as t_amt,t.remark as t_des "
							+"FROM students s "
							+"JOIN enrollments e ON s.student_id = e.student_id "
							+"JOIN attendance_years a ON e.attendance_year_id = a.attendance_year_id "
							+"JOIN universities u ON a.university_id = u.university_id "
							+"JOIN scholarship_amounts h ON h.attendance_year_id = e.attendance_year_id "
			                +"JOIN transactions t ON t.student_id = s.student_id "
			                +"AND t.taken_out_at between '" + year + "-" + month + "-01' AND '" + year + "-" + month + "-30' "
							+"and s.student_id="+student_id+";";

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				resultSet = statement.executeQuery();
				
				while (resultSet.next()) {
					stuTranHistory.add(new StuTranHistory(
							resultSet.getString("t_date"),
							resultSet.getInt("t_amt"),
							resultSet.getString("t_des")
							)
							);
						}
					} 
					catch (SQLException e) {
						System.out.println(e.getMessage());
					}
					
					return stuTranHistory;
		}
}
