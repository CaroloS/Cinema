package uk.ac.ucl.coursework.control;

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

public class CustomerHomeController implements Initializable {

	public Scene scene4;
	@FXML

	private void loadfilms(ActionEvent event) {
		
		//TAKES YOU TO 'WhatsOn' PAGE WHEN 'ALL FILM' BUTTON OR 'WHATS ON' MENU ITEM PRESSED
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/WhatsOnCustomer.fxml"));

			scene4 = new Scene(root, 690, 850);
			CinemaMain.thestage.setTitle("What's On");
			CinemaMain.thestage.setScene(scene4);
			CinemaMain.thestage.show();
		} catch (Exception e) {
			CinemaMain.LOGGER.warning("Couldn't load film list window");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
