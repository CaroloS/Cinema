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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import Users.UserAddController;

public class LoginController implements Initializable {

	@FXML
	private TextField username;

	@FXML
	private Button signUp;

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

	@FXML
	public void onButtonClicked(ActionEvent e) {
		if (e.getSource().equals(signUp)) {
			CinemaMain main = new CinemaMain();
			main.goToNextPage("view/SignUpPage.fxml", "Sign Up Page");
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

}
