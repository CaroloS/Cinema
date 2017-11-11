package uk.ac.ucl.coursework.viewEmployee;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import uk.ac.ucl.coursework.CinemaMain;
import uk.ac.ucl.coursework.model.Films;

public class EmployeeHomeController implements Initializable {
	
	@FXML
	private TextField filmTitleText, filmIDText, filmDateText, filmStartText, filmEndText, filmGenreText, pictureText;
	
	public Scene scene5;
	
	@FXML
	private void addFilm(ActionEvent event) {
		
		
		
	}
	
	@FXML
	private void goToWhatsOn(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("WhatsOn2.fxml"));
			scene5 = new Scene(root, 690, 850);
			CinemaMain.thestage.setTitle("What's On");
			CinemaMain.thestage.setScene(scene5);
			CinemaMain.thestage.show();
		} catch (Exception e) {
			CinemaMain.LOGGER.warning("Couldn't load What's On");
		}
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
