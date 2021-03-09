package dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Donation {

	private SimpleIntegerProperty donationId;
	private SimpleIntegerProperty donatorId;
	private SimpleIntegerProperty amount;
	private SimpleStringProperty donatedAt;
	private SimpleStringProperty description;
	
	public Donation(int donationId, int donatorId, int amount, String donatedAt, String description) {
		super();
		this.donationId = new SimpleIntegerProperty(donationId);
		this.donatorId = new SimpleIntegerProperty(donatorId);
		this.amount = new SimpleIntegerProperty(amount);
		this.donatedAt = new SimpleStringProperty(donatedAt);
		this.description = new SimpleStringProperty(description);
	}
	
	public Donation(int donatorId, int amount,String donatedAt, String description) {
		super();
		this.donatorId = new SimpleIntegerProperty(donatorId);
		this.amount = new SimpleIntegerProperty(amount);
		this.donatedAt = new SimpleStringProperty(donatedAt);
		this.description = new SimpleStringProperty(description);
	}
	
	public int getDonationId() {
		return donationId.get();
	}

	public int getDonatorId() {
		return donatorId.get();
	}

	public int getAmount() {
		return amount.get();
	}

	public String getDonatedAt() {
		return donatedAt.get();
	}

	public String getDescription() {
		return description.get();
	}

	@Override
	public String toString() {
		return "Donation [donationId=" + donationId + ", donatorId=" + donatorId + ", amount=" + amount + ", donatedAt="
				+ donatedAt + ", description=" + description + "]";
	}

	
	
	
}
