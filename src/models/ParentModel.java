package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnection;
import dto.Parent1;
public class ParentModel {
	
	private final DBConnection dbConnection;
	private Connection connection;
	private ResultSet resultSet;
	private PreparedStatement pStmt;

	public ParentModel() {
		super();
		this.dbConnection = new DBConnection();
		this.connection = DBConnection.getConnection();
	}
	
	public boolean saveStudent(Parent1 parent) throws SQLException {
		var isSave = true;
		connection = DBConnection.getConnection();
		
		pStmt = connection.prepareStatement("INSERT INTO `parents` "
				+ "(`student_id`, `father_name`, `father_job`, `father_phone`, `mother_name`,`mother_job`,`mother_phone`,`parent_address`,`parent_township_id`)"
				+ "VALUES (?, ?, ?, ?, ?,?,?,?,?);"
				);
		
		pStmt.setInt(1, parent.getStudent_id());
		pStmt.setString(2, parent.getFather_name());
		pStmt.setString(3, parent.getFather_job());
		pStmt.setString(4, parent.getFather_phone());
		pStmt.setString(5, parent.getMother_name());
		pStmt.setString(6, parent.getMother_job());
		pStmt.setString(7, parent.getMother_phone());
		pStmt.setString(8, parent.getParent_address());
		pStmt.setInt(9, parent.getParent_township_id());
		
		isSave = pStmt.execute();
		connection.close();

		return isSave;
		
		
	}

}
