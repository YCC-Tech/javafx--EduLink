package controllers;

import java.io.IOException;



import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import dto.Enrollment;
import dto.Student;
import dto.Parent1;
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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.AttendanceYearModel1;
import models.EnrollmentModel;
import models.EthcinityModel;
import models.MajorModel;
import models.ParentModel;
import models.RegionModel;
import models.ReligionModel;
import models.StudentModel;
import models.TownshipModel;
import models.UniversityModel1;

public class StudentController implements Initializable {
	@FXML
    private BorderPane bp;

    @FXML
    private Pane leftSideMenu;

    @FXML
    private Pane studentsMenuItem;

    @FXML
    private Pane menuItemIconStudents;

    @FXML
    private Label lblStudents;

    @FXML
    private Pane transactionsMenuItem;

    @FXML
    private Label lblTransactions;

    @FXML
    private Pane menuItemIconTransactions;

    @FXML
    private Pane donatorsMenuItem;

    @FXML
    private Label lblDonators;

    @FXML
    private Pane menuItemIconDonators;

    @FXML
    private Pane reportsMenuItem;

    @FXML
    private Label lblReports;

    @FXML
    private Pane menuItemIconReports;

    @FXML
    private Pane adminsMenuItem;

    @FXML
    private Label lblAdmins;

    @FXML
    private Pane menuItemIconAdmins;

    @FXML
    private AnchorPane ap;

    @FXML
    private BorderPane bpInside;
    
    @FXML
    private Pane btnCloseWindow;

    @FXML
    private Pane btnMinimizeWindow;

	private List<Pane> menuItems;
	private List<Label> menuLabels;
	private List<Pane> menuIcons;
	
	//for student main
	
    @FXML
    private TextField tfSearchStu;
    
    @FXML
    private ComboBox<String> cobAttYear;

    @FXML
    private ComboBox<String> cobUniversity;

    @FXML
    private TableView<Student> tbStu;

    @FXML
    private TableColumn<Student, Integer> tbcolNo;

    @FXML
    private TableColumn<Student, String> tbcolName;

    @FXML
    private TableColumn<Student, String> tbcolAttYear;

    @FXML
    private TableColumn<Student, String> tbcolMajor;

    @FXML
    private TableColumn<Student, String> tbcolUniverstiy;

    @FXML
    private TableColumn<Student, String> tbcolPhno;

    @FXML
    private TableColumn<?, ?> tbcolDetail;
    
    //for new student form
    
    @FXML
    private AnchorPane apNewStudentPage;

    @FXML
    private TextField tfName;
    
    @FXML
    private ComboBox<String> cobUniversity1;

    @FXML
    private ComboBox<String> cobYear;

    @FXML
    private ComboBox<String> cobMajor;

    @FXML
    private ComboBox<String> cobNRCNo;

    @FXML
    private ComboBox<String> cobNRCTownship;

    @FXML
    private ComboBox<String> cobNRCNational;

    @FXML
    private TextField tfNRCNo;

    @FXML
    private ComboBox<String> cobGender;

    @FXML
    private DatePicker dpBirthday;

    @FXML
    private TextField tfPhone;

    @FXML
    private TextArea taHostelAdd;

    @FXML
    private ComboBox<String> cobRegion;

    @FXML
    private ComboBox<String> cobTownship;
    
    @FXML
    private ComboBox<String> cobEthcinity;

    @FXML
    private ComboBox<String> cobReligion;

    @FXML
    private TextField tfFatherName;

    @FXML
    private TextField tfMotherName;

    @FXML
    private TextField tfFatherPhone;

    @FXML
    private TextField tfMotherPhone;

    @FXML
    private TextField tfFatherJob;

    @FXML
    private TextField tfMotherJob;
    
    @FXML
    private ComboBox<String> cobParTownship;
    
    @FXML
    private TextArea taParAddress;

    @FXML
    private TextArea taAddress;
    
    private StudentModel studentModel = new StudentModel();
    private EnrollmentModel enrollmentModel = new EnrollmentModel();
    private ReligionModel religionModel = new ReligionModel();
	private EthcinityModel ethcinityModel = new EthcinityModel();
	private TownshipModel townshipModel = new TownshipModel();
	private RegionModel regionModel = new RegionModel();
	private UniversityModel1 universityModel = new UniversityModel1();
	private MajorModel majorModel = new MajorModel();
	private AttendanceYearModel1 attendanceYearModel = new AttendanceYearModel1();
	private ParentModel parentModel = new ParentModel();
    
    TranslateTransition slide = new TranslateTransition();
    
    private String nrc;
    
    private Integer gender_id;
    
    int uniId;
    int regionId;
    
    @FXML
    void processClear(ActionEvent event) {
    	allFieldClear();
    }
    
    @FXML
    void processFilter(MouseEvent event) {

    }
    
    //form add new student form
    
    @FXML
    void processCancel(MouseEvent event) {

    }
    
    //from student list
    @FXML
    void processAddStu(ActionEvent event) {

    	translatePane();
    	
    	/*for university combox*/
	    
	    try {
	    	cobUniversity1.setValue("Choose University");
			cobUniversity1.setItems(universityModel.getUniList("select * from universities;"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	 // listen chosen university
	 		cobUniversity1.valueProperty().addListener(new ChangeListener<String>() {



	 			@Override
	 			public void changed(ObservableValue<? extends String> observeUni, String oldUni, String newUni) {

	 				AttendanceYearModel1 attendanceYearModel = new AttendanceYearModel1();
	 				try {
	 					uniId = attendanceYearModel.getUniId(newUni);
	 				} catch (SQLException e) {
	 					// TODO Auto-generated catch block
	 					e.printStackTrace();
	 				}
	 				// set academic year according to chosen university name

	 				try {
	 					cobYear.setValue("Choose Year");
	 					cobYear.setItems(
	 							attendanceYearModel.getYearList("select * from attendance_years where university_id='" + uniId + "';"));

	 				} catch (SQLException e) {

	 					e.printStackTrace();
	 				}
	 				// choose academic year(listen chosen year)
				cobYear.valueProperty().addListener(new ChangeListener<String>() {


					@Override
					public void changed(ObservableValue<? extends String> observeYear, String oldYear, String newYear) {
						MajorModel majorModel = new MajorModel();

						try {
							cobMajor.setValue("Choose Major");
							cobMajor.setItems(majorModel.getMajorList("select m.name from majors m"
									+ " JOIN attendance_years as a ON a.attendance_year_id = m.attendance_year_id"
									+ " JOIN universities u ON a.university_id = u.university_id"
									+ " where a.name='"
									+ newYear + "' and u.university_id='" + uniId + "';"));

							//
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

			}
		});// uni+year+major

	 		
	 		/*NRC combo*/
	 		cobNRCNo.setValue("1/");
	 		ObservableList<String> nrcPrefix = FXCollections.observableArrayList("1/", "2/","3/","4/","5/","6/","7/","8/","9/","10/","11/","12/","13/","14/");
	 		cobNRCNo.setItems(nrcPrefix);
	 		
	    	/*for township short name  combox in NRC*/
		    
		    try {
		    	cobNRCTownship.setValue("MaYaNa");
		    	cobNRCTownship.setItems(townshipModel.getTownshipNameShortList("select * from townships;"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    cobNRCNational.setValue("(N)");
	 		ObservableList<String> nrcNational = FXCollections.observableArrayList("(N)", "(E)","(P)");
	 		cobNRCNational.setItems(nrcNational);
	 		
	 		
	 		/*Gender combo*/
	 		cobGender.setValue("Choose Gender");
	 		ObservableList<String> gender = FXCollections.observableArrayList("Male", "Female");
			cobGender.setItems(gender);
			
			/*for ethnicity combox*/
			EthcinityModel ethcinityModel = new EthcinityModel();
		    try {
		    	cobEthcinity.setValue("Choose Ethcinity");
		    	cobEthcinity.setItems(ethcinityModel.getEthcinityList("select * from ethcinities;"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    /*for religion combox*/
			ReligionModel religionModel = new ReligionModel();
		    try {
		    	cobReligion.setValue("Choose Religion");
		    	cobReligion.setItems(religionModel.getReligionList("select * from religions;"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		    
		    /*for region combox*/
		    try {
		    	cobRegion.setValue("Choose Region");
		    	cobRegion.setItems(religionModel.getReligionList("select * from regions;"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    
		 // choose region and listen chosen region code
			cobRegion.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observeRegion, String oldRegion, String newRegion) {
					
					try {
	 					regionId = regionModel.getRegionId(newRegion);
	 				} catch (SQLException e) {
	 					// TODO Auto-generated catch block
	 					e.printStackTrace();
	 				}

					// set township name according to chosen region
					try {
						cobTownship.setValue("Choose Township");
						cobTownship.setItems(
						townshipModel.getTownshipList("select * from townships where region_id='" + regionId + "';"));
					} catch (SQLException e) {

						e.printStackTrace();
					}
				}
			});
			
			/* for parent township combox*/
			try {
		    	cobParTownship.setValue("Choose Township");
		    	cobParTownship.setItems(townshipModel.getTownshipList("select * from townships;"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
	 		
    }
    
    
	private void translatePane() {
		slide.setDuration(Duration.seconds(0.3));
		slide.setNode(apNewStudentPage);

		slide.setToY(-30);
		slide.play();
		apNewStudentPage.setTranslateY(-700);

	}
	
	@FXML
	void processCloseUpSlide(MouseEvent event) {
		slide.setDuration(Duration.seconds(0.3));
		slide.setNode(apNewStudentPage);

		slide.setToY(-700);
		slide.play();

		apNewStudentPage.setTranslateY(-30);
	}
    
    
    @FXML
    void processSave(ActionEvent event) throws SQLException {
    	
    	Integer university_id = 1;
    	Integer attendance_year_id = 1;
    	Integer major_id = 1;
    	Integer isActive = 1;
    	Integer student_id = studentModel.getLatestStuId();
    	
  
    	nrc = cobNRCNo.getValue().toString()+ cobNRCTownship.getValue().toString()+cobNRCNational.getValue().toString()+tfNRCNo.getText().trim();
    	
    	if (cobGender.getValue().toString()=="Male") {
    		
    		gender_id =0;
			
		}else gender_id =1;
    	
    	Student student = new Student(tfName.getText().trim(),
    								gender_id,
    								nrc,
    								dpBirthday.getValue().toString(),
    								tfPhone.getText().trim(),
    								taAddress.getText().trim(),
    								taHostelAdd.getText().trim(),
    								religionModel.getReligionId(cobReligion.getValue()),
    								townshipModel.getTownshipId(cobTownship.getValue()),
    								ethcinityModel.getEthcinityId(cobEthcinity.getValue())
    			);
    	
    	/* enrollment data from new student form*/
    	university_id = universityModel.getUniverstiyId(cobUniversity1.getValue());
    	attendance_year_id = attendanceYearModel.getYearId(cobYear.getValue(), university_id);
    	major_id = majorModel.getMajorId(cobMajor.getValue(), attendance_year_id, university_id);
    
    	Enrollment enrollment = new Enrollment(
    			student_id,
    			university_id,
    			attendance_year_id,
    			major_id,
    			isActive
    			);
    	
    	Parent1 parent = new Parent1(
    			student_id,
    			tfFatherName.getText().trim(),
    			tfFatherJob.getText().trim(),
    			tfFatherPhone.getText().trim(),
    			tfMotherName.getText().trim(),
    			tfMotherJob.getText().trim(),
    			tfMotherPhone.getText().trim(),
    			taParAddress.getText().trim(),
    			townshipModel.getTownshipId(cobParTownship.getValue())
    			);
    	
    	
    	studentModel.saveStudent(student);
    	enrollmentModel.saveStudent(enrollment);
    	parentModel.saveStudent(parent);
    	

    }
    
    private void allFieldClear() {
    	
    	tfName.clear();
    	tfNRCNo.clear();
    	tfPhone.clear();
    	taHostelAdd.clear();
    	taAddress.clear();
    	
    	tfFatherName.clear();
    	tfFatherJob.clear();
    	tfFatherPhone.clear();
    	tfMotherName.clear();
    	tfMotherJob.clear();
    	tfMotherPhone.clear();
    	taParAddress.clear();
    	
    }
    
    //load student list table
    private void showStudentTable() {
    	
    	tbcolNo.setCellValueFactory(new PropertyValueFactory<Student, Integer>("student_id"));
    	tbcolName.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
    	tbcolPhno.setCellValueFactory(new PropertyValueFactory<Student, String>("phone"));
    	tbcolAttYear.setCellValueFactory(new PropertyValueFactory<Student, String>("attendance_year"));
    	tbcolMajor.setCellValueFactory(new PropertyValueFactory<Student, String>("major"));
    	tbcolUniverstiy.setCellValueFactory(new PropertyValueFactory<Student, String>("university"));
		
		try {
			StudentModel studentModel = new StudentModel();
			
			/* Get data from database */
			ObservableList<Student> students = studentModel.getStudentList();
			
			/* Wrap the ObservableList in a FilteredList (initially display all data). */
			FilteredList<Student> filteredData = new FilteredList<>(students, b -> true);
			
			/* 2. Set the filter Predicate whenever the filter changes. */
			tfSearchStu.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(student -> {
					/* If filter text is empty, display all universities */
					if (newValue == null || newValue.isEmpty()) return true;

					/* Compare every table columns fields with lowercase filter text */
					String lowerCaseFilter = newValue.toLowerCase();

					/* Filter with all table columns */
					if (student.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
					else if (student.getPhone().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
					else return false; // Does not match
				});
			});
			
			/* 3. Wrap the FilteredList in a SortedList. */
			SortedList<Student> sortedData = new SortedList<>(filteredData);
			
			/* 4. Bind the SortedList comparator to the TableView comparator.
			      Otherwise, sorting the TableView would have no effect. */
			sortedData.comparatorProperty().bind(tbStu.comparatorProperty());
			
			/* 5. Add sorted (and filtered) data to the table. */
			tbStu.setItems(sortedData);
		}catch(SQLException e) {
			e.printStackTrace();
		}
    }
    
    
    
	/* Close and Minimize Buttons */
    @FXML
    void processCloseWindow(MouseEvent event) {
    	Stage stage = (Stage) btnCloseWindow.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void processMinimizeWindow(MouseEvent event) {
    	Stage stage = (Stage) btnMinimizeWindow.getScene().getWindow(); 
    	stage.setIconified(true);
    }

    /* Left Side Bar Menu Buttons */
	@FXML
	void processStudentsMenuItem(MouseEvent event) {
		 bp.setCenter(ap);
		changeMenuItemsApperance(studentsMenuItem, lblStudents, menuItemIconStudents);
	}

	@FXML
	void processTransactionsMenuItem(MouseEvent event) {
		loadPage("Transactions", transactionsMenuItem, lblTransactions, menuItemIconTransactions);
	}

	@FXML
	void processDonatorsMenuItem(MouseEvent event) {
		loadPage("Donators", donatorsMenuItem, lblDonators, menuItemIconDonators);
	}

	@FXML
	void processReportsMenuItem(MouseEvent event) {
		loadPage("Reports", reportsMenuItem, lblReports, menuItemIconReports);
	}

	@FXML
	void processAdminsMenuItem(MouseEvent event) {
		loadPage("Admins", adminsMenuItem, lblAdmins, menuItemIconAdmins);
	}

	/* Dynamically load page based on left menu item on click */
	private void loadPage(String page, Pane menuItem, Label menuLabel, Pane menuIcon) {
		Parent root = null;

		try {
			root = FXMLLoader.load(getClass().getResource("/resources/pages/" + page + ".fxml"));
		} catch (IOException e) {
			Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, e);
		}

		changeMenuItemsApperance(menuItem, menuLabel, menuIcon);
		bp.setCenter(root);
	}

	/* Tweak left menu buttons active style */
	private void changeMenuItemsApperance(Pane menuItem, Label menuLabel, Pane menuIcon) {
		
		menuItems.forEach(item -> {
			if (item == menuItem) {
				item.setStyle("-fx-background-color: #fff");
			} else {
				item.setStyle("-fx-background-color: #1A97EB");
			}
		});
		
		menuLabels.forEach(item -> {
			if (item == menuLabel) {
				item.setStyle("-fx-text-fill: #1A97EB");
			}
			else {
				item.setStyle("-fx-text-fill: #fff");
			}
		});
		
		menuIcons.forEach(item -> {
			if (item == menuIcon) {
				item.setStyle("-fx-background-color: #1A97EB");
			}
			else {
				item.setStyle("-fx-background-color: #fff");
			}
		});
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		apNewStudentPage.setTranslateY(-700);

	    List<Pane> sideMenuItems = Arrays.asList(studentsMenuItem, transactionsMenuItem, donatorsMenuItem, reportsMenuItem, adminsMenuItem);
	    menuItems = sideMenuItems;
	    
	    List<Label> sideMenuLabels = Arrays.asList(lblStudents, lblTransactions, lblDonators, lblReports, lblAdmins);
	    menuLabels = sideMenuLabels;
	    
	    List<Pane> sideMenuIcons = Arrays.asList(menuItemIconStudents, menuItemIconTransactions, menuItemIconDonators, menuItemIconReports, menuItemIconAdmins);
	    menuIcons = sideMenuIcons;
	    
	    /* Student Tab is firstly loaded */
	    changeMenuItemsApperance(studentsMenuItem, lblStudents, menuItemIconStudents);
	    
	    showStudentTable();
	    
	    /*for university combox*/
	    UniversityModel1 universityModel = new UniversityModel1();
	    try {
			cobUniversity.setItems(universityModel.getUniList("select * from universities;"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	 // listen chosen university
	 		cobUniversity.valueProperty().addListener(new ChangeListener<String>() {


	 			@Override
	 			public void changed(ObservableValue<? extends String> observeUni, String oldUni, String newUni) {
	 				AttendanceYearModel1 attendanceYearModel = new AttendanceYearModel1();
	 				try {
	 					uniId = attendanceYearModel.getUniId(newUni);
	 				} catch (SQLException e) {
	 					// TODO Auto-generated catch block
	 					e.printStackTrace();
	 				}
	 				// set academic year according to chosen university name

	 				try {
	 		
	 					cobAttYear.setItems(
	 							attendanceYearModel.getYearList("select * from attendance_years where university_id='" + uniId + "';"));

	 				} catch (SQLException e) {

	 					e.printStackTrace();
	 				}
	 				// choose academic year(listen chosen year)
	 				
	 				
	 				

	 			}
	 		});
	}

}
