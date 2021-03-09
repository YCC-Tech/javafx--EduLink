package controllers;

import java.io.IOException;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import dto.Donation;
import dto.Donator;
import dto.University;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.DonatorModel;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


public class DonatorController implements Initializable{

    @FXML
    private TableView<Donator> tbDonator;

    @FXML
    private TableColumn<Donator, Integer> clNo;

    @FXML
    private TableColumn<Donator, String> clName;

    @FXML
    private TableColumn<Donator, String> clEmail;

    @FXML
    private TableColumn<Donator, String> clPhone;

    @FXML
    private ComboBox<String> cbDonationTime;

    @FXML
    private ComboBox<String> cbMonth;
    
    @FXML
    private TextField filterField;
    
    @FXML
    private Button btnNewDonation;
    
    @FXML
    private AnchorPane apTransactionsPage;

    @FXML
    private Label lblName;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextArea txtDescription;
    
    @FXML
    private TextField txtNewDonatorName;

    @FXML
    private TextField txtNewDonatorEmail;

    @FXML
    private TextField txtNewDonatorPhone;

    @FXML
    private ComboBox<String> cobDivision;

    @FXML
    private ComboBox<String> cobTownship;

    @FXML
    private TextArea taFullAddress;

    @FXML
    private TextField txtNewDonatorAmount;

    @FXML
    private TextArea taNewDescription;
    
    @FXML
    private AnchorPane apNewDonatorApplicationForm;
   
    @FXML
    private Label txtDate;

    @FXML
    private AnchorPane apTab;
    
    @FXML
    private TableView<Donation> tbHistory;

    @FXML
    private TableColumn<Donation, String> clHistoryDate;

    @FXML
    private TableColumn<Donation, Integer> clHistoryAmount;

    @FXML
    private TableColumn<Donation, String> clHistoryDescription;

    @FXML
    private TextField tfSearchHistory;

    @FXML
    private Label txtHistoryName;
    
    @FXML
    private Label txtIdHistory;

    @FXML
    private Label txtNameHistory;

    @FXML
    private Label txtPhoneHistory;

    @FXML
    private Label txtRegionHistory;

    @FXML
    private Label txtAddressHistory;

    @FXML
    private Label txtLastDonateDate;

    @FXML
    private Label txtLastDonateAmount;

    @FXML
    private Label txtTotalAmount;

    @FXML
    private Label txtTotalTime;


    private final DonatorModel donatorModel = new DonatorModel();
    
    TranslateTransition slide = new TranslateTransition();

    int regionId;
    
    String township;
    
    String region;
    
    String monthId;
    
    int donationCount;
    
    //New Donator Button
    @FXML
    void processNewDonator(ActionEvent event) throws SQLException {
    	
    	//txtIdNo.setText(donatorModel.lastAddedDonatorId()+"");
    	txtDate.setText(LocalDate.now().toString());

    	translatePane(apNewDonatorApplicationForm);
    	
    	
    }
    
    //New Donator Form Save Button
    @FXML
    void processSaveNewDonator(ActionEvent event) throws SQLException, IOException {

    	Donator donator = new Donator(txtNewDonatorName.getText(),txtNewDonatorPhone.getText(),
    			region + township + taFullAddress.getText(),0);
    	
    	var isSave = donatorModel.saveDonator(donator);
    	
    	if ( !isSave ) {
    		
    		System.out.println(" Saved new donator! ");
    		
    		int donatorId = donatorModel.lastAddedDonatorId().getDonatorId();
        	donatorModel.donatorCount(donatorId);
    		LocalDate date = LocalDate.now();
        	Donation donation = new Donation(donatorId,Integer.parseInt(txtNewDonatorAmount.getText()), 
        			date.toString(), taNewDescription.getText().trim());
        	var isSave1 = donatorModel.saveDonation(donation);
        	if ( !isSave1 ) {
        		System.out.println(" Saved new donator donation! ");
        	}
        	else { 
        		System.out.println(" Donation Fail! ");
        	}
        	
    	}
    	else { 
    		System.out.println(" Save new donator Failed! ");
    	}
    	
    	slide.setDuration(Duration.seconds(0.5));
    	slide.setNode(apNewDonatorApplicationForm);
    	
    	slide.setToY(-700);
    	slide.play();
    	
    	apNewDonatorApplicationForm.setTranslateY(-30);
    	
    	showDonatorTable("select * from donators;"); // to refresh table 
    	
    	txtNewDonatorName.clear();
    	txtNewDonatorPhone.clear();
    	taFullAddress.clear();
    	txtNewDonatorAmount.clear();
    	taNewDescription.clear();
    	// cbMonth.getSelectionModel().clearSelection();
    	cbMonth.setPromptText("Month");
    }
    
    //Edit Donator Form
    @FXML
    void processEditDonator(ActionEvent event) {

    }
    
    @FXML
    void processCloseDonationHistory(MouseEvent event) {
    	
    	slide.setDuration(Duration.seconds(0.5));
    	slide.setNode(apTab);
    	
    	slide.setToY(-700);
    	slide.play();
    	
    	apTab.setTranslateY(-30);
    	
    }
    
    //close new donator form
    @FXML
    void processCloseNewDonatorForm(MouseEvent event) {

    	txtNewDonatorName.clear();
    	txtNewDonatorPhone.clear();
    	taFullAddress.clear();
    	txtNewDonatorAmount.clear();
    	taNewDescription.clear();
    	
    	slide.setDuration(Duration.seconds(0.5));
    	slide.setNode(apNewDonatorApplicationForm);
    	
    	slide.setToY(-700);
    	slide.play();
    	
    	apNewDonatorApplicationForm.setTranslateY(-30);
    	
    }
    
    //New Donation Button
    @FXML
    void processNewDonation(ActionEvent event) throws IOException {

		Donator donator = tbDonator.getSelectionModel().getSelectedItem();
		
		 lblName.setText(donator.getName());
		 
		 translatePane(apTransactionsPage);
    }
    
    //New Donation Page Close
    @FXML
    void processNewDonationClose(MouseEvent event) {

    	txtAmount.clear();
    	txtDescription.clear();
    	
    	slide.setDuration(Duration.seconds(0.5));
    	slide.setNode(apTransactionsPage);
    	
    	slide.setToY(-700);
    	slide.play();
    	
    	apTransactionsPage.setTranslateY(-30);
    	
    }
    
    //New Donation Page Donate Button
    @FXML
    void processNewDonationDonate(ActionEvent event) throws SQLException {
    	
		Donator donator = tbDonator.getSelectionModel().getSelectedItem();

		LocalDate date = LocalDate.now();
		
    	Donation donation = new Donation(donator.getDonatorId(),Integer.parseInt(txtAmount.getText()), 
    			date.toString(), txtDescription.getText().trim());
    	
    	var isSave = donatorModel.saveDonation(donation);
    	
    	donatorModel.donatorCount(donator.getDonatorId());
    	
    	if ( !isSave ) {
    		
    		System.out.println(" Saved! ");

    	}
    	else { 
    		System.out.println(" Donation Fail! ");
    	}
    	
    	slide.setDuration(Duration.seconds(0.5));
    	slide.setNode(apTransactionsPage);
    	
    	slide.setToY(-700);
    	slide.play();
    	
    	apTransactionsPage.setTranslateY(-30);
    	
    	txtAmount.clear();
    	txtDescription.clear();
    }
    
    //Donator Detail info and donation history page visible
    private void detectDoubleClickOnTableRow() {
    	
    	tbDonator.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                Donator selectedDonator = tbDonator.getSelectionModel().getSelectedItem();
                
				txtHistoryName.setText(selectedDonator.getName());
				txtIdHistory.setText(selectedDonator.getDonatorId() + "");
				txtNameHistory.setText(selectedDonator.getName());
				txtAddressHistory.setText(selectedDonator.getAddress());
				txtPhoneHistory.setText(selectedDonator.getPhone());
				
				String[] array = selectedDonator.getAddress().split(",");
				for(int i = 0; i < array.length; i++ ) {
				System.out.println(array[i]);
				}
				txtRegionHistory.setText(array[0]);
				
				try {
					Donation selectedDonatorDonation = donatorModel.lastAddedDonation(selectedDonator.getDonatorId());
					txtLastDonateDate.setText(selectedDonatorDonation.getDonatedAt());
					txtLastDonateAmount.setText(selectedDonatorDonation.getAmount() + "");
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					int totalAmount = donatorModel.totalAmountDonation(selectedDonator.getDonatorId());
					txtTotalAmount.setText(totalAmount + "");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					int totalCount = donatorModel.totalDonationCount(selectedDonator.getDonatorId());
					txtTotalTime.setText(totalCount + "");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

                try {
					showDonationHistoryTable("select * from donations where donator_id = '" + selectedDonator.getDonatorId() + "';");
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
               
            	
            	translatePane(apTab);
            }
            else if (event.getClickCount() == 1) {
    			btnNewDonation.setVisible(true);

            }
        });
    };
    
    //Page Transaction
    private void translatePane(Node a) {
    	slide.setDuration(Duration.seconds(0.5));
    	slide.setNode(a);
    	
    	slide.setToY(-30);
    	slide.play();
    	a.setTranslateY(-700);
    	
    }
    
    //Donators List Table
    private void showDonatorTable(String sql) throws SQLException {
    	
    	clNo.setCellValueFactory(new PropertyValueFactory<Donator,Integer>("donatorId"));
		clName.setCellValueFactory(new PropertyValueFactory<Donator,String>("name"));
		clEmail.setCellValueFactory(new PropertyValueFactory<Donator,String>("address"));
		clPhone.setCellValueFactory(new PropertyValueFactory<Donator,String>("phone"));
		
		
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<Donator> filteredData = new FilteredList<>(donatorModel.getDonatorList(sql), b -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
		filteredData.setPredicate(donator -> {
		// If filter text is empty, display all persons.
		if (newValue == null || newValue.isEmpty()) return true;

			// Compare every table columns fields with lowercase filter text
			String lowerCaseFilter = newValue.toLowerCase();
			// Filter with all table columns
			if (donator.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) return true; 
			else if (donator.getAddress().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
			else if (donator.getPhone().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
			else return false; // Does not match
			});
		
		});
	
		// 3. Wrap the FilteredList in a SortedList.
		SortedList<Donator> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tbDonator.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tbDonator.setItems(sortedData);
		
    }
    //End of Donators List Table

  //Donation History List Table
    private void showDonationHistoryTable(String sql) throws SQLException {
		
		clHistoryDate.setCellValueFactory(new PropertyValueFactory<Donation,String>("donatedAt"));
		clHistoryAmount.setCellValueFactory(new PropertyValueFactory<Donation,Integer>("amount"));
		clHistoryDescription.setCellValueFactory(new PropertyValueFactory<Donation,String>("description"));
		
		
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<Donation> filteredData = new FilteredList<>(donatorModel.getDonationList(sql), b -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		tfSearchHistory.textProperty().addListener((observable, oldValue, newValue) -> {
		filteredData.setPredicate(donation -> {
		// If filter text is empty, display all persons.
		if (newValue == null || newValue.isEmpty()) return true;

			// Compare every table columns fields with lowercase filter text
			String lowerCaseFilter = newValue.toLowerCase();
			// Filter with all table columns
			if (donation.getAmount() != -1) return true; 
			else if (donation.getDonatedAt().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
			else if (donation.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
			else return false; // Does not match
			});
		
		});
	
		// 3. Wrap the FilteredList in a SortedList.
		SortedList<Donation> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tbHistory.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tbHistory.setItems(sortedData);
		
    }
    //End of Donators List Table
    
    
 
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		
		//show Donator list 
		try {
			showDonatorTable("select * from donators; ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Invisible New Donation Button
		btnNewDonation.setVisible(false);
		
		detectDoubleClickOnTableRow();  

		
		// Donation time combo 
		ObservableList<String> donationTime = FXCollections.observableArrayList("1 time","2 times","3 times","4 times","5 times","6 times","7 times","8 times","9 times","10 times and above");
		
		cbDonationTime.setItems(donationTime);
		
		cbDonationTime.valueProperty().addListener(new ChangeListener<String>() {
			  
			  @Override public void changed(ObservableValue<? extends String>
			  observeRegion, String oldRegion, String newRegion) {
			  
				 String donationTime = cbDonationTime.getValue();
				 System.out.println(donationTime);
				 
				  switch(donationTime) {
				  case "1 time":
					  donationCount = 1; break;
				  case "2 times":
					  donationCount = 2; break;
				  case "3 times":
					  donationCount = 3; break;
				  case "4 times":
					  donationCount = 4; break;
				  case "5 times":
					  donationCount = 5; break;
				  case "6 times":
					  donationCount = 6; break;
				  case "7 times":
					  donationCount = 7; break;
				  case "8 times":
					  donationCount = 8; break;
				  case "9 times":
					  donationCount = 9; break;
				  case "10 times and above":
					  donationCount = 10; break;
				  }
				  
				try {
					showDonatorTable("select donator_id, name, address, phone from donators where donation_count = '" + donationCount + "';");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			  
			  } 
		});
		
		//End of Donation time combo
		
		
		//Month filter combo of donator list 
		ObservableList<String> month = FXCollections.observableArrayList("January" , "February" , "March" ,
				"April", "May" , "June", "July" , "August", "September", "October", "November", "December");
		
		cbMonth.setItems(month);
		
		cbMonth.valueProperty().addListener(new ChangeListener<String>() {
			  
			  @Override public void changed(ObservableValue<? extends String>
			  observeRegion, String oldRegion, String newRegion) {
			  
				  String month = cbMonth.getValue();
				  System.out.println(month);
				  switch(month) {
				  case "January":
					  monthId = "01"; break;
				  case "February":
					  monthId = "02"; break;
				  case "March":
					  monthId = "03"; break;
				  case "April":
					  monthId = "04"; break;
				  case "May":
					  monthId = "05"; break;
				  case "June":
					  monthId = "06"; break;
				  case "July":
					  monthId = "07"; break;
				  case "August":
					  monthId = "08"; break;
				  case "September":
					  monthId = "09"; break;
				  case "October":
					  monthId = "10"; break;
				  case "November":
					  monthId = "11"; break;
				  case "December":
					  monthId = "12"; break;
				  }
				  //System.out.println(monthId);
				  String startDate = "2021-"+monthId+"-01";
				  //System.out.println(startDate);
				  String endDate = "2021-"+monthId+"-31";
				  //System.out.println(endDate);

				  try {
					showDonatorTable("SELECT donators.donator_id, donators.name, donators.address, donators.phone FROM donators INNER JOIN donations ON donators.donator_id=donations.donator_id WHERE donations.donated_at between '"+ startDate +"' and '"+ endDate + "';");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  
			  } 
		});
		//End of Month filter combo of donator list 
		
		// choose region name + township full name for New Donator Page's Address
 		// set region list from db to UI
 		try {
 			cobDivision.setItems(donatorModel.getRegionNameList("select name from regions;"));
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		// choose region and listen chosen region code
 		cobDivision.valueProperty().addListener(new ChangeListener<String>() {

 			@Override
 			public void changed(ObservableValue<? extends String> observeRegion, String oldRegion, String newRegion) {
 				//lblRegion.setText(cobRegion.getValue());
 				region = cobDivision.getValue() + ", ";
 				//System.out.println(region);
 				
 				try {
 					regionId = donatorModel.getRegionId(newRegion);
 				} catch (SQLException e1) {
 					// TODO Auto-generated catch block
 					e1.printStackTrace();
 				}

 				// set township name according to chosen region
 				try {
 					//cobTownship.setValue("Choose Township");
 					cobTownship.setItems(donatorModel.getTownshipLongList(
 							"select name from townships where region_id='" + regionId + "';"));
 				} catch (SQLException e) {

 					e.printStackTrace();
 				}
 			}
 		});
		cobTownship.valueProperty().addListener(new ChangeListener<String>() {
		  
			  @Override public void changed(ObservableValue<? extends String>
			  observeRegion, String oldRegion, String newRegion) {
			  
				  township = cobTownship.getValue() + ", "; 
				  System.out.println(township);
			  
			  } 
		});
		
		//End of choose region name + township full name for New Donator Page's Address
  
		apTab.setTranslateY(-700);
		apTransactionsPage.setTranslateY(-700);
		apNewDonatorApplicationForm.setTranslateY(-700);

				
	}

}
