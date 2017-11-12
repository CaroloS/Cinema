package uk.ac.ucl.coursework;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class CinemaMain extends Application {
	
	public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public static Stage thestage;
	public Scene scene1;
	
	@Override
	public void start(Stage primaryStage) {
		
		thestage = primaryStage;

		try {
			Parent root = FXMLLoader.load(getClass().getResource("view/LoginScreen.fxml"));

			scene1 = new Scene(root, 480, 300);
			primaryStage.setTitle("Cinema Login");
			primaryStage.setScene(scene1);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		try {
	         CinemaLogger.setup();
	     } catch (IOException e) {
	         e.printStackTrace();
	         throw new RuntimeException("Problems with creating the log files");
	     }
		
		launch(args);
	}
}
