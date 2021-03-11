package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import models.ReportsModel;

public class ChartController implements Initializable{


    @FXML
    private AnchorPane chartPane;
    
    @FXML
    private AnchorPane showPieChartPane;

    @FXML
    private PieChart allPie;
    
    @FXML
    private AnchorPane thisYearDonationPane;

    @FXML
    private PieChart thisYearDonationPie;
    
    @FXML
    private Label lblChartValue;

    @FXML
    private Text txtChart;
    
    private ReportsModel reportModel = new ReportsModel();
    
    final Label caption = new Label("");

    ObservableList<PieChart.Data> allDonationPieData;
    
    ResultSet rs;
    
    String sql;
    @FXML
    void processAllDonation(MouseEvent event) throws SQLException, IOException {

    	showAllDonationPie();

    }

    @FXML
    void processChart(ActionEvent event) {

    }

    @FXML
    void processDonation(ActionEvent event) throws IOException {

    	AnchorPane pane = FXMLLoader.load(getClass().getResource("/resources/pages/Reports.fxml"));
        chartPane.getChildren().setAll(pane);
    }

    @FXML
    void processMoneyInfo(MouseEvent event) {

    }

    @FXML
    void processScholarship(ActionEvent event) throws IOException {

    	AnchorPane pane = FXMLLoader.load(getClass().getResource("/resources/pages/Scholarship.fxml"));
        chartPane.getChildren().setAll(pane);
    }

    @FXML
    void processThisYearDonation(MouseEvent event) throws IOException {

    	showThisYearDonationPie();
	
    }

    @FXML
    void processUniveristy(MouseEvent event) throws SQLException, IOException {

    	showUniversityPie();
    }
    
    // All Donation PieChart
    public void showAllDonationPie() throws SQLException {
	
    	sql = "select sum(amount), year(donated_at)\r\n"
    			+ "from donations\r\n"
    			+ "group by year(donated_at);";
    	
    	// call method with sql and tableColumn Names
    	ObservableList<PieChart.Data> pieData = reportModel.getChartData(sql,"year(donated_at)", "sum(amount)", false);
    	
    	allPie.setData(pieData);
    	
    	allPie.setTitle("Donations according to years");
    	
    	txtChart.setText("This is all the donations started from the 2015 to 2021");
    
    	// When pie is clicked, value is shown on label
    	for(final Data data: allPie.getData()) {
    		data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					lblChartValue.setText(String.valueOf(data.getPieValue()));
					
				}
    			
    		}
    				);
    	}
    }
    
    // This Year Donation PieChart
    public void showThisYearDonationPie() {
    	
    	Integer year = Calendar.getInstance().get(Calendar.YEAR);
    	sql = "select sum(amount), month(donated_at)\r\n"
    			+ "from donations\r\n"
    			+ "where year(donated_at) = '"+year+"'\r\n"
    			+ "group by month(donated_at);";
    	
    	// call method with sql and tableColumn Names
    	ObservableList<PieChart.Data> pieData2 = reportModel.getChartData(sql,"month(donated_at)", "sum(amount)", true);
    	
    	allPie.setData(pieData2);
    	
    	allPie.setTitle("This year's donations");
    	
    	txtChart.setText("This is current year's donations according to month. 1(Jan), 2(Feb), etc.");
    
    	// When pie is clicked, value is shown on label
    	for(final Data data: allPie.getData()) {
    		data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					lblChartValue.setText(String.valueOf(data.getPieValue()));
					
				}
    			
    		}
    				);
    	}
    }
    
    // University PieChart
    public void showUniversityPie() {
    	
    	//Integer year = Calendar.getInstance().get(Calendar.YEAR);
    	sql = "select count(t.transaction_id),u.short_name\r\n"
    			+ "from transactions t, students s, universities u, enrollments e, majors m\r\n"
    			+ "where t.student_id = s.student_id and s.student_id = e.student_id and "
    			+ "e.major_id = m.major_id and m.university_id = u.university_id and "
    			+ "year(t.taken_out_at)='"+2015+"'\r\n"
    			+ "group by u.name;";
    	
    	// call method with sql and tableColumn Names
    	ObservableList<PieChart.Data> pieData3 = reportModel.getChartData(sql, "u.short_name", "count(t.transaction_id)", false);
    	
    	allPie.setData(pieData3);
    	
    	allPie.setTitle("All universities in 2015");
    	
    	txtChart.setText("These are the universities that are participated in 2015 according to transaction table");
    	
    	// When pie is clicked, value is shown on label
    	for(final Data data: allPie.getData()) {
    		data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					lblChartValue.setText(String.valueOf(data.getPieValue()));
					
				}
    			
    		}
    				);
    	}
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Initialize All donation PieChart
		try {
			showAllDonationPie();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
