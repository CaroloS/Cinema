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
import uk.ac.ucl.coursework.CinemaMain;;

public class LoginController implements Initializable {

	public Scene scene2, scene3;
	@FXML
	private TextField username;


	@FXML
	private void loginPressed(ActionEvent event) {

		if (username.getText().equals("customer")) {

			try {
				Parent root = FXMLLoader.load(getClass().getResource("../view/CustomerHome.fxml"));

				scene2 = new Scene(root, 690, 850);
				CinemaMain.thestage.setTitle("Customer Home");
				CinemaMain.thestage.setScene(scene2);
				CinemaMain.thestage.show();
			} catch (Exception e) {
				CinemaMain.LOGGER.warning("Couldn't load Customer Home");
				e.printStackTrace();
			}
		} else if (username.getText().equals("employee")) {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("../view/EmployeeHome.fxml"));

				scene3 = new Scene(root, 850, 800);
				CinemaMain.thestage.setTitle("Employee Home");
				CinemaMain.thestage.setScene(scene3);
				CinemaMain.thestage.show();
			} catch (Exception e) {
				CinemaMain.LOGGER.warning("Couldn't load Employee Home");
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

}
