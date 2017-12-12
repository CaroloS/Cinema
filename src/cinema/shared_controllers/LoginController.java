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

/**
 * Contoller class for the Login page. Defines functions to allow user to log in if they are a known user
 * or to navigate to sign up page if they are not known. Defines static variables about the logged in user
 * that can be accessed throughout the application to differentiate what they see / where they can access
 * depending on who is logged in.
 * @author carolinesmith, daianabassi
 *
 */
public class LoginController implements Initializable {

	//DECLARES ALL THE FXML ELEMENTS USED BY THIS CONTROLLER//
	@FXML
	private TextField username, password;
	@FXML
	private Button signUp;

	//DECLARES STATIC STRING TO SET WITH THE INFORMATION ABOUT THE LOGGED IN USER
	//USED THROUGHOUT THE PROGRAM TO DISPLAY / NAVIGATE DIFFERENT INFORMATION
	//BASED ON WHO IS LOGGED IN
	public static String loggedInUser = null;
	public static String userID = null;
	public static String usersName = null;

	File xmlFile = new File("users.xml");
	Element root = null;        //AN XML ROOT ELEMENT TO SET WHEN PARSING XML DOCUMENTS
	List list = null;			//A LIST TO PASS THE XML NODES TO FROM XML PARSING
	
	Alert alert = new Alert(AlertType.WARNING);

	/**
	 * Parses the 'user.xml' file and checks the username / password entered against the list of known users. 
	 * If the user is known - they will be navigated to the correct side of the application depending on 
	 * if they are a customer of employee. Invites to sign up if user is not known.
	 * @param event the button click event.
	 */
	@FXML
	private void loginPressed(ActionEvent event) {

		// READS AND PARSES 'user.xml' FILE
		if (xmlFile.exists()) {
			try {
				ReadXMLFile read = new ReadXMLFile("users.xml");
				root = read.readsXML();			//RETURNS THE ROOT
			} catch (Exception e) {
				CinemaMain.LOGGER.warning("Couldn't parse users.XML");
			}
			list = root.getChildren("User");
		}

		int x = 0;

		//CHECKS IF BOTH USERNAME AND PASSWORD FIELDS ARE FILLED IN 
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

				if (userName.equalsIgnoreCase(username.getText())				//CHECKS IF USERNAME / PASSWORD PAIR ARE PRESENT IN 'user.xml' file
						&& userPassword.equalsIgnoreCase(password.getText())) {
					x = 2;														//WILL ALLOW THE USER INTO THE APPLICATION IF THE PAIR IS PRESENT
					userID = node.getAttributeValue("id");
					usersName = node.getChildText("FirstName");
					loggedInUser = node.getChildText("UserProfile");
				}

			}
		}

		//ALERTS IF USERNAME/ PASSWORD PAIR IS NOT PRESENT OR IF FIELDS ARE EMPTY
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
			//IF LOGGED IN UESR IS CUSTOMER (AS SPECIFIED IN 'user.xml' NAVIGATES TO CUSTOMER SIDE OF APPLICATOIN
			if (loggedInUser.equals("customer")) {

				CinemaMain main = new CinemaMain();
				main.goToNextPage("customer_view/CustomerHome.fxml", "Customer Home");

			//IF LOGGED IN UESR IS EMPLOYEE NAVIGATES TO EMPLOYEE SIDE OF APPLICATOIN
			} else if (loggedInUser.equals("employee")) {

				CinemaMain main = new CinemaMain();
				main.goToNextPage("employee_view/EmployeeHome.fxml", "Employee Home");
			}
		}

	}

	/**
	 * Navigates the user to the Sign Up page when sign up button clicked.
	 * @param e the button click event.
	 */
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
