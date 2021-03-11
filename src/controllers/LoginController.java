package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import models.Auth;

public class LoginController implements Initializable {

	@FXML
    private AnchorPane loginPane;

    @FXML
    private TextField tfLoginUsername;

    @FXML
    private PasswordField tfLoginPassword;

    @FXML
    private Label msg;

    @FXML
    private Pane btnCloseWindow;

    @FXML
    private Pane btnMinimizeWindow;


	double xOffset, yOffset = 0;

	@FXML
	void processLogin(ActionEvent event) {
		String username = tfLoginUsername.getText().trim();
		String password = tfLoginPassword.getText();

		if ("".equals(username) || "".equals(password)) {
			msg.setText("Both fields are required.");
			msg.setStyle("-fx-text-fill: #fff; -fx-font-size: 14px;");
			msg.setVisible(true);
			return;
		}

		Auth auth = new Auth();
		boolean isLoggedIn = auth.checkLogin(username, password);

		if (isLoggedIn) {
			// loginPane.setVisible(false);

			Stage stage = (Stage) loginPane.getScene().getWindow();
			stage.close();

			Stage primaryStage = new Stage();
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("/resources/pages/Students.fxml"));
				root.setStyle("-fx-background-color: transparent; ");

				/* Make window to be draggable */
				root.setOnMousePressed(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						xOffset = primaryStage.getX() - event.getScreenX();
						yOffset = primaryStage.getY() - event.getScreenY();
					}
				});
				root.setOnMouseDragged(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						primaryStage.setX(event.getScreenX() + xOffset);
						primaryStage.setY(event.getScreenY() + yOffset);
					}
				});

				Scene scene = new Scene(root);

				JMetro jMetro = new JMetro(Style.LIGHT);
				jMetro.setScene(scene);

				scene.setFill(Color.TRANSPARENT);
				scene.getStylesheets().add(getClass().getResource("/resources/css/application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				primaryStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			msg.setText("Username or password is wrong.");
			msg.setStyle("-fx-text-fill: #fff; -fx-font-size: 14px;");
			msg.setVisible(true);
		}
	}
	
	@FXML
    void processCloseWindow(MouseEvent event) {
		Stage stage = (Stage) loginPane.getScene().getWindow();
		stage.close();
    }
	
    @FXML
    void processMinimizeWindow(MouseEvent event) {
    	Stage stage = (Stage) loginPane.getScene().getWindow();
		stage.setIconified(true);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		msg.setVisible(false);
	}

}
