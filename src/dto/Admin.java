package dto;

import java.sql.Date;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Admin {
	private SimpleIntegerProperty user_id;
	private SimpleStringProperty name;
	private SimpleStringProperty username;
	private SimpleStringProperty password;
	private Date created_at;

	public Admin(Integer id, String name, String username) {
		this.user_id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.username = new SimpleStringProperty(username);
	}
	
	public Admin(String name, String username, String password) {
		this.name = new SimpleStringProperty(name);
		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
	}
	
	public Admin(String name, String username) {
		this.name = new SimpleStringProperty(name);
		this.username = new SimpleStringProperty(username);
	}

	public int getUserId() {
		return user_id.get();
	}

	public String getName() {
		return name.get();
	}
	
	public String getUsername() {
		return username.get();
	}
	
	public String getPassword() {
		return password.get();
	}
}
