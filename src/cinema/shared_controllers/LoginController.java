package cinema.shared_controllers;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jdom2.Element;

import cinema.CinemaMain;
import cinema.XML.ReadXMLFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class LoginController implements Initializable {

	@FXML
	private TextField username, password;
	@FXML
	private Button signUp;

	public static String loggedInUser = null;
	public static String userID = null;
	public static String usersName = null;

	File xmlFile = new File("users.xml");
	Element root = null;
	List list = null;
	Alert alert = new Alert(AlertType.WARNING);

	@FXML
	private void loginPressed(ActionEvent event) {

		// IF USER TYPES 'customer' LOADS CUSTOMER HOME PAGE, ELSE IF USER TYPES
		// 'employee' LOADS EMPLOYEE HOME PAGE

		// READS AND PARSES USER.XML FILE
		if (xmlFile.exists()) {
			try {
				ReadXMLFile read = new ReadXMLFile("users.xml");
				root = read.readsXML();
			} catch (Exception e) {
				CinemaMain.LOGGER.warning("Couldn't parse users.XML");
			}
			list = root.getChildren("User");
		}

		int x = 0;

		if (username.getText().trim().isEmpty() || password.getText().trim().isEmpty()) {
			x = 1;
		}

		// ITERATES THROUGH USER.XML TO SEE IF USER IS SIGNED UP AND IF USER IS
		// EMPLOYEE OR CUSTOMER
		// AND PASSES USERNAME AND ID TO STATIC VARIABLES
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Element node = (Element) list.get(i);
				String userName = node.getChildText("UserName");
				String userPassword = node.getChildText("Password");

				if (userName.equalsIgnoreCase(username.getText())
						&& userPassword.equalsIgnoreCase(password.getText())) {
					x = 2;
					userID = node.getAttributeValue("id");
					usersName = node.getChildText("FirstName");
					loggedInUser = node.getChildText("UserProfile");
				}

			}
		}

		switch (x) {
		case (0):
			alert.setTitle("Login Denied");
			alert.setHeaderText("Username or Password not recognised");
			alert.setContentText("Check your login details are correct or got to 'Sign up' if you don't have an account");
			alert.showAndWait();
			break;
		case (1):
			alert.setTitle("Empty Fields");
			alert.setHeaderText("Some fields are empty");
			alert.setContentText("Please enter a username and password to login. Or go to 'Sign up' to create an account");
			alert.showAndWait();
			break;
		case (2):
			if (loggedInUser.equals("customer")) {

				CinemaMain main = new CinemaMain();
				main.goToNextPage("customer_view/CustomerHome.fxml", "Customer Home");

			} else if (loggedInUser.equals("employee")) {

				CinemaMain main = new CinemaMain();
				main.goToNextPage("employee_view/EmployeeHome.fxml", "Employee Home");
			}
		}

	}

	@FXML
	public void goToSignUp(ActionEvent e) {
		if (e.getSource().equals(signUp)) {
			CinemaMain main = new CinemaMain();
			main.goToNextPage("shared_view/SignUpPage.fxml", "Sign Up Page");
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

}
