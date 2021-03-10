package dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Parent1 {
	
	private SimpleIntegerProperty parent_id;
	private SimpleIntegerProperty student_id;
	private SimpleStringProperty father_name;
	private SimpleStringProperty father_job;
	private SimpleStringProperty father_phone;
	private SimpleStringProperty mother_name;
	private SimpleStringProperty mother_job;
	private SimpleStringProperty mother_phone;
	private SimpleStringProperty parent_address;
	private SimpleStringProperty parent_township;
	private SimpleStringProperty parent_region;
	private SimpleIntegerProperty parent_township_id;
	
	//for new student form 
			public Parent1(Integer student_id,String father_name,String father_job,String father_phone,String mother_name,String mother_job,String mother_phone,String parent_address,Integer parent_township_id) {
				super();
				
				this.student_id = new SimpleIntegerProperty(student_id);
				this.father_name = new SimpleStringProperty(father_name);
				this.father_job = new SimpleStringProperty(father_job);
				this.father_phone = new SimpleStringProperty(father_phone);
				this.mother_name = new SimpleStringProperty(mother_name);
				this.mother_job = new SimpleStringProperty(mother_job);
				this.mother_phone = new SimpleStringProperty(mother_phone);
				this.parent_address = new SimpleStringProperty(parent_address);
				this.parent_township_id = new SimpleIntegerProperty(parent_township_id);
				
				
				
			}
			
			//for update student form 
			public Parent1(Integer student_id,String father_name,String father_job,String father_phone,String mother_name,String mother_job,String mother_phone,String parent_address,
					String parent_township, String parent_region) {
				super();
				
				this.student_id = new SimpleIntegerProperty(student_id);
				this.father_name = new SimpleStringProperty(father_name);
				this.father_job = new SimpleStringProperty(father_job);
				this.mother_name = new SimpleStringProperty(mother_name);
				this.mother_job = new SimpleStringProperty(mother_job);
				this.father_phone = new SimpleStringProperty(father_phone);
				this.mother_phone = new SimpleStringProperty(mother_phone);
				this.parent_address = new SimpleStringProperty(parent_address);
				this.parent_township = new SimpleStringProperty(parent_township);
				this.parent_region = new SimpleStringProperty(parent_region);
			}
			
			public String getParentTownship() {
				return parent_township.get();
			}
			
			public String getParentRegion() {
				return parent_region.get();
			}
			
			
	
	
	public Integer getParent_id() {
		return parent_id.get();
	}
	
	public Integer getStudent_id() {
		return student_id.get();
	}
	
	public String getFather_name() {
		return father_name.get();
	}
	
	public String getFather_job() {
		return father_job.get();
	}
	
	public String getFather_phone() {
		return father_phone.get();
	}
	
	public String getMother_name() {
		return mother_name.get();
	}
	
	public String getMother_job() {
		return mother_job.get();
	}
	
	public String getMother_phone() {
		return mother_phone.get();
	}
	
	public String getParent_address() {
		return parent_address.get();
	}
	
	public Integer getParent_township_id() {
		return parent_township_id.get();
	}
	
}
