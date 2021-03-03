package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	double xOffset, yOffset = 0;

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.initStyle(StageStyle.UNDECORATED);

			Parent root = FXMLLoader.load(getClass().getResource("/resources/pages/Home.fxml"));

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

			scene.getStylesheets().add(getClass().getResource("/resources/css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
