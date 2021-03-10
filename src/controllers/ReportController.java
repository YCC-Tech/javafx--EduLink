package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import Utilities.MutateMonth;
import dto.DonationDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.ReportsModel;

public class ReportController implements Initializable{

	@FXML
    private AnchorPane reportPane;
    
    @FXML
    private TextField tfSearch;
    
    @FXML
    private Button btnScholarship;

    @FXML
    private Button btnRemainBalance;

    @FXML
    private Button btnDonation;

    @FXML
    private ComboBox<String> cmbYearFilter;

    @FXML
    private ComboBox<String> cmbMonthFilter;

    @FXML
    private TableView<DonationDto> tblDonation;

    @FXML
    private TableColumn<DonationDto, Integer> tblDonationNo;

    @FXML
    private TableColumn<DonationDto, String> tblDonationDate;

    @FXML
    private TableColumn<DonationDto, String> tblDonationName;

    @FXML
    private TableColumn<DonationDto, Integer> tblDonationAmount;

    @FXML
    private TableColumn<DonationDto, String> tblDonationPhone;

    @FXML
    private TableColumn<DonationDto, String> tblDonationDescription;
   
    
    private ReportsModel reportModel = new ReportsModel();

    @FXML
    void processDonation(ActionEvent event) {

    }

    @FXML
    void processRemainBalance(ActionEvent event) throws IOException {
    	
    	AnchorPane pane = FXMLLoader.load(getClass().getResource("/resources/pages/ReportChart.fxml"));
        reportPane.getChildren().setAll(pane);
    	
    }

    @FXML
    void processScholarship(ActionEvent event) throws IOException {
    	
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/resources/pages/Scholarship.fxml"));
        reportPane.getChildren().setAll(pane);
    }
    
    @FXML
    void processSearch(ActionEvent event) {

    }
    
    @FXML
    void processFilterMonth(ActionEvent event) {

    	String yearString = cmbYearFilter.getValue();
    	String monthString = cmbMonthFilter.getValue();
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
    	
    	showTable(reportModel.getDonationList(year, month));
    }

    @FXML
    void processFilterYear(ActionEvent event) {

    	String yearString = cmbYearFilter.getValue();
    	String monthString = cmbMonthFilter.getValue();
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
    	
    	showTable(reportModel.getDonationList(year, month));
    	
    }

    public void showTable(ObservableList<DonationDto> dataForTable) {
    	ObservableList<DonationDto> donationList = FXCollections.observableArrayList();
    	
    	tblDonationNo.setCellValueFactory(new PropertyValueFactory<DonationDto, Integer>("countNo"));
		tblDonationDate.setCellValueFactory(new PropertyValueFactory<DonationDto, String>("donated_at"));
		tblDonationName.setCellValueFactory(new PropertyValueFactory<DonationDto, String>("name"));
		tblDonationAmount.setCellValueFactory(new PropertyValueFactory<DonationDto, Integer>("amount"));
		tblDonationPhone.setCellValueFactory(new PropertyValueFactory<DonationDto, String>("phone"));
		tblDonationDescription.setCellValueFactory(new PropertyValueFactory<DonationDto, String>("description"));
		
		
		//tblDonation.setItems(donationModel.getDonationList());
		donationList.addAll(dataForTable);
		
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<DonationDto> filteredData = new FilteredList<>(donationList, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(donation -> {
			// If filter text is empty, display all persons.
			if (newValue == null || newValue.isEmpty()) return true;

			// Compare every table columns fields with lowercase filter text
			String lowerCaseFilter = newValue.toLowerCase();

						// Filter with all table columns
						if (donation.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) return true; 
						else if (donation.getPhone().toString().indexOf(lowerCaseFilter) != -1) return true;
						else if (String.valueOf(donation.getAmount()).indexOf(lowerCaseFilter) != -1) return true;
						else return false; // Does not match
					});
				});

				// 3. Wrap the FilteredList in a SortedList.
				SortedList<DonationDto> sortedData = new SortedList<>(filteredData);

				// 4. Bind the SortedList comparator to the TableView comparator.
				// Otherwise, sorting the TableView would have no effect.
				sortedData.comparatorProperty().bind(tblDonation.comparatorProperty());

				// 5. Add sorted (and filtered) data to the table.
				tblDonation.setItems(sortedData);		
    }
    
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		showTable(reportModel.getDonationList(0, 0));
		cmbYearFilter.getItems().add("All year");
		cmbYearFilter.getItems().add("2015");
		cmbYearFilter.getItems().add("2016");
		cmbYearFilter.getItems().add("2017");
		cmbYearFilter.getItems().add("2021");
		
		cmbYearFilter.getSelectionModel().select(0);
		
		//cmbYearFilter.getSelectionModel().select(year);
		
		cmbMonthFilter.getItems().add("All month");
		cmbMonthFilter.getItems().add("January");
		cmbMonthFilter.getItems().add("February");
		cmbMonthFilter.getItems().add("March");
		cmbMonthFilter.getItems().add("April");
		cmbMonthFilter.getItems().add("May");
		cmbMonthFilter.getItems().add("Jun");
		cmbMonthFilter.getItems().add("July");
		cmbMonthFilter.getItems().add("August");
		cmbMonthFilter.getItems().add("September");
		cmbMonthFilter.getItems().add("October");
		cmbMonthFilter.getItems().add("November");
		cmbMonthFilter.getItems().add("December");
		
		cmbMonthFilter.getSelectionModel().select(0);
		
		
	}

}