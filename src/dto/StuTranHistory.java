package dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StuTranHistory {
	
	private SimpleIntegerProperty student_id;
	private SimpleStringProperty tran_date;
	private SimpleIntegerProperty tran_amt;
	private SimpleStringProperty tran_des;
	
	public StuTranHistory(String tran_date, Integer tran_amt, String tran_des) {
		super();
		
		this.tran_date = new SimpleStringProperty(tran_date);
		this.tran_amt = new SimpleIntegerProperty(tran_amt);
		this.tran_des = new SimpleStringProperty(tran_des);
		
	}
	
	public StuTranHistory(Integer student_id,String tran_date, Integer tran_amt, String tran_des) {
		super();
		
		this.student_id = new SimpleIntegerProperty(student_id);
		this.tran_date = new SimpleStringProperty(tran_date);
		this.tran_amt = new SimpleIntegerProperty(tran_amt);
		this.tran_des = new SimpleStringProperty(tran_des);
		
	}
	
	
	
	public Integer getStudent_id() {
		return student_id.get();
	}

	

	public String getTran_date() {
		return tran_date.get();
	}

	public Integer getTran_amt() {
		return tran_amt.get();
	}
	
	public String getTran_des() {
		return tran_des.get();
	}

}
