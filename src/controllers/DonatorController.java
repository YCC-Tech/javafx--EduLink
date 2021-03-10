package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import dto.Donation;
import dto.Donator;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    private ComboBox<String> cobUpdateDivision;

    @FXML
    private ComboBox<String> cobUpdateTownship;

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
    
    @FXML
    private AnchorPane apUpdateDonatorForm;

    @FXML
    private Label txtUpdateDate;

    @FXML
    private TextField txtUpdateName;

    @FXML
    private TextField txtUpdatePhone;

    @FXML
    private TextArea taUpdateAddress;

    @FXML
    private Button btnUpdateDonator;

    private final DonatorModel donatorModel = new DonatorModel();
    
    TranslateTransition slide = new TranslateTransition();

    int regionId;
    
    String township;
    
    String region;
    
    String updatedTownship;
    
    String updatedRegion;
    
    String monthId;
    
    int donationCount;
    
    int donatorUpdatedId;
    
    //New Donator 
    
    //Add New Donator Button from first page
    @FXML
    void processNewDonator(ActionEvent event) throws SQLException {
    	
    	txtDate.setText(LocalDate.now().toString());
    	translatePane(apNewDonatorApplicationForm);
    }
    
    //New Donator Form's Save Button
    @FXML
    void processSaveNewDonator(ActionEvent event) throws SQLException, IOException {
    	
    	Donator donator = new Donator(txtNewDonatorName.getText(),txtNewDonatorPhone.getText(),
    			region + township + taFullAddress.getText(),0);
    	
    	var isSave = donatorModel.saveDonator(donator);
    	
    	if ( !isSave ) {
    		
    		System.out.println(" Saved new donator! ");
    		
    		int donatorId = donatorModel.lastAddedDonatorId().getDonatorId();
        	donatorModel.increaseDonationCount(donatorId);
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
    	
    	closePane(apNewDonatorApplicationForm);
    	
    	showDonatorTable("select * from donators;"); // to refresh table 
    	
    	txtNewDonatorName.clear();
    	txtNewDonatorPhone.clear();
    	taFullAddress.clear();
    	txtNewDonatorAmount.clear();
    	taNewDescription.clear();
    	cbMonth.setPromptText("Month");
    	cbDonationTime.setPromptText("Donation Time");
    }
    
    //close new donator form
    @FXML
    void processCloseNewDonatorForm(MouseEvent event) {

    	txtNewDonatorName.clear();
    	txtNewDonatorPhone.clear();
    	taFullAddress.clear();
    	txtNewDonatorAmount.clear();
    	taNewDescription.clear();
    	
    	closePane(apNewDonatorApplicationForm);
    	
    }
    //End of New Donator 
    
    
    //New Donation
    
    //New Donation Button from first page 
    @FXML
    void processNewDonation(ActionEvent event) throws IOException {

		Donator donator = tbDonator.getSelectionModel().getSelectedItem();
		
		 lblName.setText(donator.getName());
		 
		 translatePane(apTransactionsPage);
    }
    
    //New Donation Form's Donate Button
    @FXML
    void processNewDonationDonate(ActionEvent event) throws SQLException {
    	
		Donator donator = tbDonator.getSelectionModel().getSelectedItem();

		LocalDate date = LocalDate.now();
		
    	Donation donation = new Donation(donator.getDonatorId(),Integer.parseInt(txtAmount.getText()), 
    			date.toString(), txtDescription.getText().trim());
    	
    	var isSave = donatorModel.saveDonation(donation);
    	
    	donatorModel.increaseDonationCount(donator.getDonatorId());
    	
    	if ( !isSave ) {
    		
    		System.out.println(" Saved! ");

    	}
    	else { 
    		System.out.println(" Donation Fail! ");
    	}
    	
    	closePane(apTransactionsPage);
    	
    	txtAmount.clear();
    	txtDescription.clear();

    	cbMonth.setPromptText("Month");
    	cbDonationTime.setPromptText("Donation Time");
    }
    
    //close New Donation Page
    @FXML
    void processNewDonationClose(MouseEvent event) {

    	txtAmount.clear();
    	txtDescription.clear();
    	closePane(apTransactionsPage);
    	
    }
    //End of New donation 
    
    
    //Update Donator
    
    //open update donator form 
    @FXML
    void processOpenUpdateForm(ActionEvent event) throws SQLException {

    	translatePane(apUpdateDonatorForm);
    	
    	Donator donator = tbDonator.getSelectionModel().getSelectedItem();
    	
    	Donator donatorDB = donatorModel.getDonator(donator.getDonatorId());
    	
    	txtUpdateName.setText(donator.getName());
    	txtUpdatePhone.setText(donator.getPhone());
    	
    	String[] array = donator.getAddress().split(",");
		/*
		 * for(int i = 0; i < array.length; i++ ) { System.out.println(array[i]); }
		 */
    	taUpdateAddress.setText(array[2]);
    	
    	txtUpdateDate.setText(LocalDate.now().toString());
    	
    	this.donatorUpdatedId = donatorDB.getDonatorId();
    }
    
    //Update Donator Form's update button
    @FXML
    void processUpdateDonator(ActionEvent event) throws SQLException {
    	
    	Donator donatorUpdated = new Donator(this.donatorUpdatedId,txtUpdateName.getText(),txtUpdatePhone.getText(),
    			updatedRegion + updatedTownship + taUpdateAddress.getText());
		
		var rowUpdated = donatorModel.updateDonator(donatorUpdated);
		
		if( rowUpdated > 0 ) {
			System.out.println(" Donator Updated! ");
		}
		else {
			System.out.println(" Donator Update Failed! ");
		}
		
		closePane(apUpdateDonatorForm);
    	
    	showDonatorTable("select * from donators;"); // to refresh table 

    	cbMonth.setPromptText("Month");
    	cbDonationTime.setPromptText("Donation Time");
    }
    
    //close Update Donator Form 
    @FXML
    void processCloseUpdateDonatorForm(MouseEvent event) {
    	closePane(apUpdateDonatorForm);
    }
    //End of Update Donator
    
    
    //Donator detail information and donation history tabs 
    
    //process Donator's Detail info and donation history Tabs
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
				/*
				 * for(int i = 0; i < array.length; i++ ) { System.out.println(array[i]); }
				 */
				txtRegionHistory.setText(array[0]);
				
				try {
					Donation selectedDonatorDonation = donatorModel.lastAddedDonation(selectedDonator.getDonatorId());
					txtLastDonateDate.setText(selectedDonatorDonation.getDonatedAt());
					txtLastDonateAmount.setText(selectedDonatorDonation.getAmount() + "");
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				try {
					int totalAmount = donatorModel.totalAmountDonation(selectedDonator.getDonatorId());
					txtTotalAmount.setText(totalAmount + "");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				try {
					int totalCount = donatorModel.totalDonationCount(selectedDonator.getDonatorId());
					txtTotalTime.setText(totalCount + "");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
                try {
					showDonationHistoryTable("select * from donations where donator_id = '" + selectedDonator.getDonatorId() + "';");
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
               
            	translatePane(apTab);
            }
            else if (event.getClickCount() == 1) {
    			btnNewDonation.setVisible(true);
    			btnUpdateDonator.setVisible(true);

            }
        });
    };
    
    //close detail and history tabs
    @FXML
    void processCloseDonationHistory(MouseEvent event) {
    	
    	closePane(apTab);
    	
    }
    //End of Donator detail information and donation history tabs 
    
 
    //Donator list and Donation list Tables
    
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
		
		tbHistory.setItems(donatorModel.getDonationList(sql));
    }
    //End of Donation History List Table
    //End of Donator list and Donation list Tables
    
    //Page Transaction
    
    //Page Transaction >> Open
    private void translatePane(Node a) {
    	slide.setDuration(Duration.seconds(0.5));
    	slide.setNode(a);
    	
    	slide.setToY(-30);
    	slide.play();
    	a.setTranslateY(-700);
    	
    }
    
    //Page Transaction >> Close
    private void closePane(Node a) {
    	slide.setDuration(Duration.seconds(0.5));
    	slide.setNode(a);
    	
    	slide.setToY(-700);
    	slide.play();
    	
    	a.setTranslateY(-30);
    }
    //End of Page Transaction
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		
		//show Donator list 
		try {
			showDonatorTable("select * from donators; ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Invisible Button
		btnNewDonation.setVisible(false);
		btnUpdateDonator.setVisible(false);
		
		//Invisible Anchor Pane
		apTab.setTranslateY(-700);
		apTransactionsPage.setTranslateY(-700);
		apNewDonatorApplicationForm.setTranslateY(-700);
		apUpdateDonatorForm.setTranslateY(-700);
		
		//Visible Button
		detectDoubleClickOnTableRow();  

		
		// Donation time comboBox
		ObservableList<String> donationTime = FXCollections.observableArrayList("1 time and above","3 times and above","5 times and above","7 times and above","10 times and above");
		
		cbDonationTime.setItems(donationTime);
		
		cbDonationTime.valueProperty().addListener(new ChangeListener<String>() {
			  
			  @Override public void changed(ObservableValue<? extends String>
			  observeRegion, String oldRegion, String newRegion) {
			  
				 String donationTime = cbDonationTime.getValue();
				 //System.out.println(donationTime);
				 
				  switch(donationTime) {
				  case "1 time and above":
					  donationCount = 1; break;
				  case "3 times and above":
					  donationCount = 3; break;
				  case "5 times and above":
					  donationCount = 5; break;
				  case "7 times and above":
					  donationCount = 7; break;
				  case "10 times and above":
					  donationCount = 10; break;
				  }
				  
				try {
					showDonatorTable("select donator_id, name, address, phone from donators where donation_count >= '" + donationCount + "';");
				} catch (SQLException e) {
					e.printStackTrace();
				}

			  } 
		});
		//End of Donation time combo
		
		
		//Month filter comboBox
		ObservableList<String> month = FXCollections.observableArrayList("January" , "February" , "March" ,
				"April", "May" , "June", "July" , "August", "September", "October", "November", "December");
		
		cbMonth.setItems(month);
		
		cbMonth.valueProperty().addListener(new ChangeListener<String>() {
			  
			  @Override public void changed(ObservableValue<? extends String>
			  observeRegion, String oldRegion, String newRegion) {
			  
				  String month = cbMonth.getValue();
				  //System.out.println(month);
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
				  
				  String startDate = "2021-"+monthId+"-01";
				  String endDate = "2021-"+monthId+"-31";
				  
				  try {
					showDonatorTable("SELECT donators.donator_id, donators.name, donators.address, donators.phone FROM donators INNER JOIN donations ON donators.donator_id=donations.donator_id WHERE donations.donated_at between '"+ startDate +"' and '"+ endDate + "';");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			  
			  } 
		});
		//End of Month filter comboBox
		
		
		// choose region name + township full name for New Donator and Update Donator Forms
 		// set region list from db to UI
 		try {
 			cobDivision.setItems(donatorModel.getRegionNameList("select name from regions;"));
 			cobUpdateDivision.setItems(donatorModel.getRegionNameList("select name from regions;"));
 		} catch (SQLException e) {
 			e.printStackTrace();
 		}
 		
 		//Region and Township ComboBox for New Donator Form 
 		// choose region and listen chosen region code
 		cobDivision.valueProperty().addListener(new ChangeListener<String>() {

 			@Override
 			public void changed(ObservableValue<? extends String> observeRegion, String oldRegion, String newRegion) {
 				
 				region = cobDivision.getValue() + ", ";
 				
 				try {
 					regionId = donatorModel.getRegionId(newRegion);
 				} catch (SQLException e1) {
 					e1.printStackTrace();
 				}

 				// set township name according to chosen region
 				try {
 					cobTownship.setItems(donatorModel.getTownshipLongList("select name from townships where region_id='" + regionId + "';"));
 				} catch (SQLException e) {

 					e.printStackTrace();
 				}
 			}
 		});
 		//choose township 
 		cobTownship.valueProperty().addListener(new ChangeListener<String>() {
 			  
			  @Override public void changed(ObservableValue<? extends String>
			  observeRegion, String oldRegion, String newRegion) {
			  
				  township = cobTownship.getValue() + ", "; 
				 // System.out.println(township);
			  
			  } 
		});
 		//End of Region and Township ComboBox for New Donator Form 
 		
 		//Region and Township ComboBox for Update Donator Form 
 		// choose update region and listen chosen region code
 		cobUpdateDivision.valueProperty().addListener(new ChangeListener<String>() {

 			@Override
 			public void changed(ObservableValue<? extends String> observeRegion, String oldRegion, String newRegion) {
 				
 				updatedRegion = cobUpdateDivision.getValue() + ", ";
 				
 				try {
 					regionId = donatorModel.getRegionId(newRegion);
 				} catch (SQLException e1) {
 					e1.printStackTrace();
 				}

 				// set township name according to chosen region
 				try {
 					cobUpdateTownship.setItems(donatorModel.getTownshipLongList("select name from townships where region_id='" + regionId + "';"));
 				} catch (SQLException e) {

 					e.printStackTrace();
 				}
 			}
 		});
 		//choose update township
		cobUpdateTownship.valueProperty().addListener(new ChangeListener<String>() {
			  
			  @Override public void changed(ObservableValue<? extends String>
			  observeRegion, String oldRegion, String newRegion) {
			  
				  updatedTownship = cobUpdateTownship.getValue() + ", "; 
				  //System.out.println(township);
			  
			  } 
		});
 		//End of Region and Township ComboBox for Update Donator Form 

		//End of choose region name + township full name for New Donator and Update Donator Forms
  	
	}

}
