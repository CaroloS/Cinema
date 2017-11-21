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

	@FXML
	private TextField username;

	@FXML
	private void loginPressed(ActionEvent event) {

		// IF USER TYPES 'customer' LOADS CUSTOMER HOME PAGE, ELSE IF USER TYPES
		// 'employee' LOADS EMPLOYEE HOME PAGE
		if (username.getText().equals("customer")) {

			CinemaMain main = new CinemaMain();
			main.goToNextPage("view/CustomerHome.fxml", "Customer Home");

		} else if (username.getText().equals("employee")) {
			
			CinemaMain main = new CinemaMain();
			main.goToNextPage("view/EmployeeHome.fxml", "Employee Home");
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

}
