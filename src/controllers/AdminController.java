package controllers;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.sun.javafx.image.IntToIntPixelConverter;

import dto.Admin;
import dto.University;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import models.AdminModel;

public class AdminController implements Initializable {

    @FXML
    private TableView<Admin> tbAdmin;

    @FXML
    private TableColumn<Admin, Integer> tbcolNo;

    @FXML
    private TableColumn<Admin, String> tbcolName;

    @FXML
    private TableColumn<Admin, Date> tbcolUsername;

    @FXML
    private TextField tfSearchAdmin;

    @FXML
    private Label lblErrorMsgOnTransactionPage;
    
    @FXML
    private Button btnDeleteAdmin;

    @FXML
    private Button btnUpdateAdmin;

    @FXML
    private Button btnCreateAdmin;
    
    @FXML
    private Label lblUserName;
    
    @FXML
    private AnchorPane apDeleteConfirm;
    
    @FXML
    private AnchorPane apCreateAdmin;
    
    @FXML
    private Pane btnCloseCreatePane;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private Button btnCreate;
    
    @FXML
    private AnchorPane apUpdateUser;

    @FXML
    private TextField tfUpdateName;

    @FXML
    private TextField tfUpdateUsername;

    @FXML
    private Pane btnCloseUpdatePane;
    
    @FXML
    private Label lblUserUpdateId;
    
    TranslateTransition slide = new TranslateTransition();
    
    private void loadAdminTable() {
    	tbcolNo.setCellValueFactory(new PropertyValueFactory<>("userId"));
		tbcolName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tbcolUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
		
		AdminModel adminModel = new AdminModel();
		ObservableList<Admin> admins = adminModel.getAdminList();
		
		/* Wrap the ObservableList in a FilteredList (initially display all data). */
		FilteredList<Admin> filteredData = new FilteredList<Admin>(admins, b -> true);

		/* 2. Set the filter Predicate whenever the filter changes. */
		tfSearchAdmin.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(admin -> {
				/* If filter text is empty, display all universities */
				if (newValue == null || newValue.isEmpty())
					return true;

				/* Compare every table columns fields with lowercase filter text */
				String lowerCaseFilter = newValue.toLowerCase();

				/* Filter with all table columns */
				if (admin.getName().toLowerCase().indexOf(lowerCaseFilter) != -1)
					return true;
				else if (admin.getUsername().toLowerCase().indexOf(lowerCaseFilter) != -1)
					return true;
				else
					return false; // Does not match
			});
		});

		/* 3. Wrap the FilteredList in a SortedList. */
		SortedList<Admin> sortedData = new SortedList<>(filteredData);

		/*
		 * 4. Bind the SortedList comparator to the TableView comparator. Otherwise,
		 * sorting the TableView would have no effect.
		 */
		sortedData.comparatorProperty().bind(tbAdmin.comparatorProperty());

		/* 5. Add sorted (and filtered) data to the table. */
		tbAdmin.setItems(sortedData);
    }

    private void detectClickOnTable() {
    	tbAdmin.setOnMouseClicked((MouseEvent event) -> {
    		if (event.getClickCount() == 1) {
    			btnUpdateAdmin.setVisible(true);
    			btnDeleteAdmin.setVisible(true);
    		}
    	});
    	
	}
    
    @FXML
    void processCreateAdmin(ActionEvent event) {
    	translatePane(apCreateAdmin);
    }

    @FXML
    void processDeleteAdmin(ActionEvent event) {
    	translatePane(apDeleteConfirm);
    	
    	Admin selectedData = tbAdmin.getSelectionModel().getSelectedItem();
    	lblUserName.setText(selectedData.getName());
    }
    
    @FXML
    void processConfirmDelete(ActionEvent event) {
    	Admin selectedData = tbAdmin.getSelectionModel().getSelectedItem();
    	int userId = selectedData.getUserId();
    	
    	AdminModel adminModel = new AdminModel();
    	int deleted = adminModel.deleteAdmin(userId);
    	
    	if (deleted == 1) {
    		closeSlide(apDeleteConfirm);
    		loadAdminTable();
    	}
    }

    @FXML
    void processUpdateAdmin(ActionEvent event) {
    	translatePane(apUpdateUser);
    	
    	Admin selectedData = tbAdmin.getSelectionModel().getSelectedItem();
    	int userId = selectedData.getUserId();
    	
    	lblUserUpdateId.setText(String.valueOf(userId));
    	
    	AdminModel adminModel = new AdminModel();
    	Admin admin = adminModel.getAdmin(userId);
    	
    	tfUpdateName.setText(admin.getName());
    	tfUpdateUsername.setText(admin.getUsername());
    }
    
    @FXML
    void processUpdate(ActionEvent event) {
    	String name = tfUpdateName.getText();
    	String username = tfUpdateUsername.getText();
    	int userId = Integer.parseInt(lblUserUpdateId.getText());
    	
    	if (name != "" && username != "") {
    		Admin admin = new Admin(userId, name, username);
        	AdminModel adminModel = new AdminModel();
        	int updated = adminModel.updateAdmin(admin);
        	
        	if (updated > 0) {
        		closeSlide(apUpdateUser);
        		loadAdminTable();
        	}
    	}
    }
    
    @FXML
    void processCloseUpdatePane(MouseEvent event) {
    	closeSlide(apUpdateUser);
    	loadAdminTable();
    }
    
    @FXML
    void processCreate(ActionEvent event) {
    	String name = tfName.getText();
    	String username = tfUsername.getText();
    	String password = pfPassword.getText();
    	
    	Admin admin = new Admin(name, username, password);
    	AdminModel adminModel = new AdminModel();
    	int inserted = adminModel.createAdmin(admin);
    	
    	if (inserted > 0) {
    		closeSlide(apCreateAdmin);
    		loadAdminTable();
    	}
    }
    
    private void translatePane(AnchorPane pane) {
		slide.setDuration(Duration.seconds(0.3));
		slide.setNode(pane);

		slide.setToY(-30);
		slide.play();
		pane.setTranslateY(-700);

	}

	@FXML
	void processCloseUpSlide(MouseEvent event) {
		closeSlide(apDeleteConfirm);
	}
	
    @FXML
    void processCloseCreatePane(MouseEvent event) {
    	closeSlide(apCreateAdmin);
    }
	
	private void closeSlide(AnchorPane pane) {
		slide.setDuration(Duration.seconds(0.3));
		slide.setNode(pane);

		slide.setToY(-700);
		slide.play();

		pane.setTranslateY(-30);
	}
	
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		apDeleteConfirm.setTranslateY(-700);
		apCreateAdmin.setTranslateY(-700);
		apUpdateUser.setTranslateY(-700);
		
		loadAdminTable();
		
		detectClickOnTable();
		
		btnUpdateAdmin.setVisible(false);
		btnDeleteAdmin.setVisible(false);
		lblUserUpdateId.setVisible(false);
		
	}

}
