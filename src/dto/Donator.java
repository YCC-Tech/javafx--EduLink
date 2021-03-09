package dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Donator {

	private SimpleIntegerProperty donatorId;
	private SimpleStringProperty name;
	private SimpleStringProperty phone;
	private SimpleStringProperty address;
	private SimpleStringProperty organizationName;
	private SimpleStringProperty job;
	private SimpleIntegerProperty donationCount;
	
	public Donator(String name, String phone,String address, Integer donationCount) {
		super();
		this.name = new SimpleStringProperty(name);
		this.phone = new SimpleStringProperty(phone);
		this.address = new SimpleStringProperty(address);
		this.donationCount = new SimpleIntegerProperty(donationCount);
	}

	public Integer getDonationCount() {
		return donationCount.get();
	}

	public Donator(Integer donatorId, String name, String phone,String address) {
		super();
		this.donatorId = new SimpleIntegerProperty(donatorId);
		this.name = new SimpleStringProperty(name);
		this.phone = new SimpleStringProperty(phone);
		this.address = new SimpleStringProperty(address);
	}

	public Donator(Integer donatorId, String name, String address,
			String phone, String organizationName, String job) {
		super();
		this.donatorId = new SimpleIntegerProperty(donatorId);
		this.name = new SimpleStringProperty(name);
		this.phone = new SimpleStringProperty(phone);
		this.address = new SimpleStringProperty(address);
		this.organizationName = new SimpleStringProperty(organizationName);
		this.job = new SimpleStringProperty(job);
	}

	

	public int getDonatorId() {
		return donatorId.get();
	}
	
	public String getName() {
		return name.get();
	}
	
	public String getPhone() {
		return phone.get();
	}
	
	public String getAddress() {
		return address.get();
	}
	
	public String getOrganizationName() {
		return organizationName.get();
	}
	
	public String getJob() {
		return job.get();
	}

	@Override
	public String toString() {
		return "Donator [donatorId=" + donatorId + ", name=" + name + ", phone=" + phone + ", address=" + address
				+ ", organizationName=" + organizationName + ", job=" + job + "]";
	}
	
	
	
}
