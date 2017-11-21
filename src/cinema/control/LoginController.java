package cinema.control;

import java.net.URL;
import java.util.ResourceBundle;

import cinema.CinemaMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;;

public class LoginController implements Initializable {

	public Scene scene2, scene3;
	
	@FXML
	private TextField username;


	@FXML
	private void loginPressed(ActionEvent event) {
		
		//IF USER TYPES 'customer' LOADS CUSTOMER HOME PAGE, ELSE IF USER TYPES 'employee' LOADS EMPLOYEE HOME PAGE
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

				scene3 = new Scene(root, 690, 850);
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
