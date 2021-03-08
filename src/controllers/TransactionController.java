package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.kieferlam.javafxblur.Blur;

import Utilities.MutateMonth;
import dto.University;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import models.UniversityModel;

public class TransactionController implements Initializable {

	@FXML
	private ComboBox<String> cbMonths;

	@FXML
	private ComboBox<String> cbYears;

	@FXML
	private TableView<University> tbUniversities;

	@FXML
	private TableColumn<University, Integer> tbcolNo;

	@FXML
	private TableColumn<University, String> tbcolUniversity;

	@FXML
	private TableColumn<University, String> tbcolUniversityShortName;

	@FXML
	private TableColumn<University, String> tbcolRegion;

	@FXML
	private TableColumn<University, Integer> tbcolStudentCount;

	@FXML
	private TextField tfSearchUniversity;

	@FXML
	private AnchorPane apTransactionsPage;

	@FXML
	private Label lblUniversityName;

	@FXML
	private Label lblUniversityShortName;

	@FXML
	private ComboBox<String> cbAttendanceYears;
	
	@FXML
	private Label lblErrorMsgOnTransactionPage;

	TranslateTransition slide = new TranslateTransition();
	String currentSelectedYear;
	String currentSelectedMonth;

	private void loadUniversityTable(String year, String month) {
		tbcolNo.setCellValueFactory(new PropertyValueFactory<>("universityId"));
		tbcolUniversity.setCellValueFactory(new PropertyValueFactory<>("name"));
		tbcolUniversityShortName.setCellValueFactory(new PropertyValueFactory<>("shortName"));
		tbcolRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
		tbcolStudentCount.setCellValueFactory(new PropertyValueFactory<>("studentCount"));

		UniversityModel universityModel = new UniversityModel();

		/* Get data from database */
		ObservableList<University> universities = universityModel.getTransactionUniversities(year, month);

		/* Wrap the ObservableList in a FilteredList (initially display all data). */
		FilteredList<University> filteredData = new FilteredList<>(universities, b -> true);

		/* 2. Set the filter Predicate whenever the filter changes. */
		tfSearchUniversity.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(university -> {
				/* If filter text is empty, display all universities */
				if (newValue == null || newValue.isEmpty())
					return true;

				/* Compare every table columns fields with lowercase filter text */
				String lowerCaseFilter = newValue.toLowerCase();

				/* Filter with all table columns */
				if (university.getName().toLowerCase().indexOf(lowerCaseFilter) != -1)
					return true;
				else if (university.getShortName().toLowerCase().indexOf(lowerCaseFilter) != -1)
					return true;
				else if (university.getRegion().toLowerCase().indexOf(lowerCaseFilter) != -1)
					return true;
				else
					return false; // Does not match
			});
		});

		/* 3. Wrap the FilteredList in a SortedList. */
		SortedList<University> sortedData = new SortedList<>(filteredData);

		/*
		 * 4. Bind the SortedList comparator to the TableView comparator. Otherwise,
		 * sorting the TableView would have no effect.
		 */
		sortedData.comparatorProperty().bind(tbUniversities.comparatorProperty());

		/* 5. Add sorted (and filtered) data to the table. */
		tbUniversities.setItems(sortedData);

	}

	private void detectDoubleClickOnTableRow() {
		tbUniversities.setOnMouseClicked((MouseEvent event) -> {
			if (event.getClickCount() == 2) {
				University selectedData = tbUniversities.getSelectionModel().getSelectedItem();

				lblUniversityName.setText(selectedData.getName());
				lblUniversityShortName.setText(selectedData.getShortName());

				translatePane();
			}
		});
	};

	private void translatePane() {
		slide.setDuration(Duration.seconds(0.3));
		slide.setNode(apTransactionsPage);

		slide.setToY(-30);
		slide.play();
		apTransactionsPage.setTranslateY(-700);

	}

	@FXML
	void processCloseUpSlide(MouseEvent event) {
		slide.setDuration(Duration.seconds(0.3));
		slide.setNode(apTransactionsPage);

		slide.setToY(-700);
		slide.play();

		apTransactionsPage.setTranslateY(-30);
	}
	
    @FXML
    void processFilter(ActionEvent event) {
    	currentSelectedYear = cbYears.getSelectionModel().getSelectedItem();
    	currentSelectedMonth = cbMonths.getSelectionModel().getSelectedItem();
    	
    	if (currentSelectedYear != null && currentSelectedMonth != null) {
    		lblErrorMsgOnTransactionPage.setText("");
    		
			/* Mutate Month */
			String month = String.valueOf(MutateMonth.getNumericMonth(currentSelectedMonth));
		
			/* load data into university table */
			loadUniversityTable(currentSelectedYear, month);			
		}
    	else {
    		lblErrorMsgOnTransactionPage.setText("Please select both year and month.");
    		lblErrorMsgOnTransactionPage.setStyle("-fx-text-fill: #FF0000");
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/* ဓသဓေသLoading years data to year combo box */
		ObservableList<String> years = FXCollections.observableArrayList("2020", "2019", "2018", "2017", "2016", "2015");
		cbYears.setItems(years);

		/* ဓသဓေသLoading months data to month combo box */
		ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April",
				"May", "June", "July", "August", "September", "October", "November", "December");
		cbMonths.setItems(months);
		
		/* Initially, set combo boxs to default value */
		cbYears.getSelectionModel().select("2015");
		cbMonths.getSelectionModel().select("December");
		
		loadUniversityTable("2015", "12");	

		/* deteact double click on table row */
		detectDoubleClickOnTableRow();

		/* ------------------ */
		apTransactionsPage.setTranslateY(-700);
	}

}
