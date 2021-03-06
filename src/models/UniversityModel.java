package models;

import java.sql.SQLException;

import dto.University;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UniversityModel {
	public ObservableList<University> getTransactionUniversities() throws SQLException {
		ObservableList<University> universities = FXCollections.observableArrayList();
		
		University u1 = new University(1, "University of Technology (Yatanarpon Cyber City)", "UT-YCC", "Mandalay", 3);
		University u2 = new University(2, "Mandalay Technological University", "MTU", "Mandalay", 3);
		University u3 = new University(3, "University of Education (Mandalay)", "UoE", "Mandalay", 3);
		University u4 = new University(4, "University of Information Technology", "UIT", "Yangon", 3);
		
		universities.addAll(u1, u2, u3, u4);
		
		return universities;

	}
}
