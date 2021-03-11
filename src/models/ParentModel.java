package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnection;
import dto.Parent1;
import dto.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	
	public Parent1 getParent(int student_id) throws SQLException{
		
		Parent1 parent = null;
			
			String sql = "select * from parents where student_id = '" + student_id + "';";

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				ResultSet resultSet = statement.executeQuery();
				
				while(resultSet.next()) {
					parent = new Parent1(resultSet.getInt("student_id"),
							resultSet.getString("father_name"),
							resultSet.getString("father_job"),
							resultSet.getString("father_phone"),
							resultSet.getString("mother_name"),
							resultSet.getString("mother_job"),
							resultSet.getString("mother_phone"),
							resultSet.getString("parent_address"));
				}

			} 
			catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			
			return parent;
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
//		connection.close();

		return isSave;
		
		
	}

	public ObservableList<Parent1> getParentInfoForUpdate(int studentId) {
		ObservableList<Parent1> parent = FXCollections.observableArrayList();
		
		String sql = "SELECT "
				+ "p.*, "
				+ "t.name as township, "
				+ "re.name as region "
				+ "FROM parents p "
				+ "JOIN townships t ON t.township_id = p.parent_township_id "
				+ "JOIN regions re ON re.region_id = t.region_id "
				+ "WHERE p.student_id = " + studentId + ";";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				parent.add(new Parent1(
						resultSet.getInt("student_id"),
						resultSet.getString("father_name"),
						resultSet.getString("father_job"),
						resultSet.getString("mother_name"),
						resultSet.getString("mother_job"),
						resultSet.getString("father_phone"),
						resultSet.getString("mother_phone"),
						resultSet.getString("parent_address"),
						resultSet.getString("township"),
						resultSet.getString("region")
						)
						);
					}
				} 
				catch (SQLException e) {
					System.out.println(e.getMessage());
				}
		
		return parent;
	}

	public int updateStudent(Parent1 parent, int studentId) {
		int updated = 0;
		
		String sql = "UPDATE parents SET "
				+ "father_name = '" + parent.getFather_name() + "', "
				+ "father_job = '" + parent.getFather_job() + "', "
				+ "father_phone = '" + parent.getFather_phone() + "', "
				+ "mother_name = '" + parent.getMother_name() + "', "
				+ "mother_job = '" + parent.getMother_job() + "', "
				+ "mother_phone = '" + parent.getMother_phone() + "', "
				+ "parent_address = '" + parent.getParent_address() + "', "
				+ "parent_township_id = '" + parent.getParent_township_id() + "' "
				+ "WHERE student_id = " + studentId + ";";
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			updated = statement.executeUpdate();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return updated;
	}

}
