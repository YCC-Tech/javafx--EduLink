package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

import Utilities.MutateMonth;
import dto.DonationDto;
import dto.ScholarshipDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import models.ReportsModel;

public class ScholarshipController implements Initializable{

	@FXML
    private AnchorPane scholarshipPane;
	
    @FXML
    private TableView<ScholarshipDto> tblScholarship;

    @FXML
    private TableColumn<ScholarshipDto, Integer> tblNo;

    @FXML
    private TableColumn<ScholarshipDto, String> tblName;

    @FXML
    private TableColumn<ScholarshipDto, String> tblUniversity;

    @FXML
    private TableColumn<ScholarshipDto, String> tblPhone;

    @FXML
    private TableColumn<ScholarshipDto, String> tblTransactionDate;

    @FXML
    private ComboBox<String> cmbUniversity;

    @FXML
    private ComboBox<String> cmbYear;

    @FXML
    private ComboBox<String> cmbMonth;

    private ReportsModel reportModel = new ReportsModel();
    
    @FXML
    void processDonation(ActionEvent event) throws IOException {

    	AnchorPane pane = FXMLLoader.load(getClass().getResource("/resources/pages/Reports.fxml"));
    	scholarshipPane.getChildren().setAll(pane);
    }
    
    @FXML
    void processRemainBalance(ActionEvent event) throws IOException {

    	AnchorPane pane = FXMLLoader.load(getClass().getResource("/resources/pages/ReportChart.fxml"));
    	scholarshipPane.getChildren().setAll(pane);
    }
    
    @FXML
    void processFilterMonth(ActionEvent event) {

    	String uniShortName = cmbUniversity.getValue();
    	String yearString = cmbYear.getValue();
    	String monthString = cmbMonth.getValue();
    	if(uniShortName.equals("All University")) {
    		uniShortName=null;
    	}
    	Integer year = 0;
    	Integer month = 0;
    	
    	if(yearString.equals("All year"))
    		year = 0;
    	else
    		year = Integer.parseInt(yearString);
    	
    	if(monthString.equals("All month"))
    		month = 0;
    	else
    		month= MutateMonth.getNumericMonth(monthString);
    	
    	showTable(reportModel.getScholarshipList(year, month, uniShortName));
    	
    }

    @FXML
    void processFilterUniversity(ActionEvent event) {

    	String uniShortName = cmbUniversity.getValue();
    	String yearString = cmbYear.getValue();
    	String monthString = cmbMonth.getValue();
    	if(uniShortName.equals("All University")) {
    		uniShortName=null;
    	}
    	Integer year = 0;
    	Integer month = 0;
    	
    	if(yearString.equals("All year"))
    		year = 0;
    	else
    		year = Integer.parseInt(yearString);
    	
    	if(monthString.equals("All month"))
    		month = 0;
    	else
    		month= MutateMonth.getNumericMonth(monthString);
    	
    	showTable(reportModel.getScholarshipList(year, month, uniShortName));
    }

    @FXML
    void processFilterYear(ActionEvent event) {

    	String uniShortName = cmbUniversity.getValue();
    	String yearString = cmbYear.getValue();
    	String monthString = cmbMonth.getValue();
    	if(uniShortName.equals("All University")) {
    		uniShortName=null;
    	}
    	Integer year = 0;
    	Integer month = 0;
    	
    	if(yearString.equals("All year"))
    		year = 0;
    	else
    		year = Integer.parseInt(yearString);
    	
    	if(monthString.equals("All month"))
    		month = 0;
    	else
    		month= MutateMonth.getNumericMonth(monthString);
    	
    	showTable(reportModel.getScholarshipList(year, month, uniShortName));
    	}
    
    public void showTable(ObservableList<ScholarshipDto> dataForTable) {
    	
    	tblNo.setCellValueFactory(new PropertyValueFactory<ScholarshipDto, Integer>("countNo"));
		tblName.setCellValueFactory(new PropertyValueFactory<ScholarshipDto, String>("studentName"));
		tblUniversity.setCellValueFactory(new PropertyValueFactory<ScholarshipDto, String>("universityName"));
		tblPhone.setCellValueFactory(new PropertyValueFactory<ScholarshipDto, String>("studentPhone"));
		tblTransactionDate.setCellValueFactory(new PropertyValueFactory<ScholarshipDto, String>("transcationDate"));
		
		tblScholarship.setItems(dataForTable);
		
			
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Initialize table
		showTable(reportModel.getScholarshipList(0,0,null));
		//University filter
		ObservableList<String> uniList = reportModel.getUniversityList();
		cmbUniversity.getItems().add("All University");
		for(String uni: uniList) {
			cmbUniversity.getItems().add(uni);
		}
		cmbUniversity.getSelectionModel().select(0);
		
		
		cmbYear.getItems().add("All year");
		cmbYear.getItems().add("2015");
		cmbYear.getItems().add("2016");
		cmbYear.getItems().add("2017");
		cmbYear.getItems().add("2021");
		
		cmbYear.getSelectionModel().select(0);
		
		//cmbYearFilter.getSelectionModel().select(year);
		
		cmbMonth.getItems().add("All month");
		cmbMonth.getItems().add("January");
		cmbMonth.getItems().add("February");
		cmbMonth.getItems().add("March");
		cmbMonth.getItems().add("April");
		cmbMonth.getItems().add("May");
		cmbMonth.getItems().add("Jun");
		cmbMonth.getItems().add("July");
		cmbMonth.getItems().add("August");
		cmbMonth.getItems().add("September");
		cmbMonth.getItems().add("October");
		cmbMonth.getItems().add("November");
		cmbMonth.getItems().add("December");
		
		cmbMonth.getSelectionModel().select(0);
	}

}
