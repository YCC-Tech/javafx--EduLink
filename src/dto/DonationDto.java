package dto;

import java.sql.Date;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DonationDto {

	private SimpleStringProperty name;

	public DonationDto(Integer countNo, String donated_at, String name, Integer amount, String phone,
			String description) {
		super();
		this.countNo = new SimpleIntegerProperty(countNo);
		this.donated_at = new SimpleStringProperty(donated_at);
		this.name = new SimpleStringProperty(name);
		this.amount = new SimpleIntegerProperty(amount);
		this.phone = new SimpleStringProperty(phone);
		this.description = new SimpleStringProperty(description);
	}

	private SimpleIntegerProperty countNo;

	private SimpleStringProperty phone;
	
	private SimpleStringProperty donated_at;
	
	private SimpleIntegerProperty amount;
	
	private SimpleStringProperty description;
	
	public Integer getCountNo() {
		return countNo.get();
	}
	
	public String getName() {
		return name.get();
	}

	public String getPhone() {
		return phone.get();
	}

	public String getDonated_at() {
		return donated_at.get();
	}

	public Integer getAmount() {
		return amount.get();
	}

	public String getDescription() {
		return description.get();
	}

	
}
