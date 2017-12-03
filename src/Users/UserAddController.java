package Users;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jdom2.Element;

import java.io.File;

import cinema.CinemaMain;
import cinema.XML.ReadXMLFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class UserAddController implements Initializable {

	// DECLARES THE FXML TEXTFIELD VARIABLES TO COLLECT THE INPUT FROM
	@FXML
	private TextField firstName, lastName, emailAddress, phoneNumber, userName, password, confirmPassword;

	@FXML
	private Button addUserButton, cancel;

	@FXML
	private RadioButton customerProfile;

	@FXML
	private RadioButton employeeProfile;

	Alert alert = new Alert(AlertType.WARNING);

	// ITERATES THROUGH ALL THE USERNAMEs AND CHECKS FOR DUPLICATES

	File xmlFile = new File("users.xml");

	// DECALRES THE ROOT ELEMENT TO BE SET BY PARSING 'users.xml'
	Element root;
	List list;

	/**
	 * Adds a user to the 'user.XML' file uses the user input to text-fields to
	 * set the user variables. Calls 'createsUser' method from 'CreateUserXML'
	 * class
	 * 
	 * @param event
	 *            : event created from button click
	 */

	@FXML
	public void initialize() {
		// addUserButton.setDisable(true);
	}

	@FXML
	private void addUser(ActionEvent event) {

		if (event.getSource().equals(addUserButton)) {

			// CREATES AN INSTANCE OF 'CreateXML'
			CreateUsersXML usersXML = new CreateUsersXML("users.xml", "users");

			// GETS THE USER INPUT FROM TEXTFIELDS AND SETS INSTANCE VARIABLES
			// OF
			// 'CreateUsersXML' WITH IT

			int x = 0;

			if (!firstName.getText().trim().isEmpty()) {
				usersXML.setFirstName(firstName.getText());
			} else {
				x = 1;
			}

			if (!lastName.getText().trim().isEmpty()) {
				usersXML.setLastName(lastName.getText());
			} else {
				x = 1;
			}

			if (!emailAddress.getText().trim().isEmpty()) {
				if (emailAddress.getText().contains("@")) {
					usersXML.setEmailAddress(emailAddress.getText());
				} else {
					x = 2;
					alert.setTitle("E-Mail Address");
					alert.setHeaderText("Invalid E-Mail Address");
					alert.setContentText("Ensure to enter a valid e-mail address!");
					alert.showAndWait();
				}
			} else {
				x = 1;
			}

			if (!phoneNumber.getText().trim().isEmpty()) {
				if (phoneNumber.getText().matches("[0-9]+") && (phoneNumber.getText().length() > 5)) {
					usersXML.setPhoneNumber(phoneNumber.getText());
				} else {
					x = 2;
					alert.setTitle("Phone Number");
					alert.setHeaderText("Invalid Phone Number");
					alert.setContentText(
							"Ensure to enter a phone number that contains only numbers an is larger than 5 digits!");
					alert.showAndWait();
				}
			} else {
				x = 1;
			}

			if (xmlFile.exists()) {
				try {
					ReadXMLFile read = new ReadXMLFile("users.xml");
					root = read.readsXML();
				} catch (Exception e) {
					CinemaMain.LOGGER.warning("Couldn't parse users.XML");
				}
				list = root.getChildren("User");
				// System.out.println(list.size());
			}

			if (!userName.getText().trim().isEmpty()) {
				if (userName.getText().length() > 4) {
					if (list != null) {
						for (int i = 0; i < list.size(); i++) {
							Element node = (Element) list.get(i);
							String xmlusername = node.getChildText("UserName");
							// System.out.println(xmlusername);

							if (xmlusername.equals(userName.getText())) {
								x = 2;
								alert.setTitle("Username");
								alert.setHeaderText("Invalid Username");
								alert.setContentText("Username already exists!");
								alert.showAndWait();
							}
						}
					}
				} else {
					x = 2;
					alert.setTitle("Username");
					alert.setHeaderText("Invalid Username");
					alert.setContentText("Ensure to enter a username that is larger than 5 digits!");
					alert.showAndWait();
				}
				
				usersXML.setUserName(userName.getText());
			} else {
				x = 1;
			}


			if (customerProfile.isSelected()) {
				usersXML.setUserProfile("customer");
			} else if (employeeProfile.isSelected()) {
				usersXML.setUserProfile("employee");
			}

			if (!password.getText().trim().isEmpty() && !confirmPassword.getText().trim().isEmpty()) {
				if (password.getText().equals(confirmPassword.getText())) {
					if (password.getText().length() > 5) {
						usersXML.setPassword(password.getText());
					} else {
						x = 2;
						alert.setTitle("User Password");
						alert.setHeaderText("Passwords is too short");
						alert.setContentText("Ensure that the passwords entered is at least 6 characters long!");
						alert.showAndWait();
					}
				} else {
					x = 2;
					alert.setTitle("User Password");
					alert.setHeaderText("Passwords do not match");
					alert.setContentText("Ensure that the passwords entered match before proceeding!");
					alert.showAndWait();
				}

			} else {
				x = 1;
			}

			switch (x) {
			case (1):
				alert.setTitle("Warning");
				alert.setHeaderText("Empty Text Field");
				alert.setContentText("Please ensure to complete all fields");
				alert.showAndWait();
				break;
			case (0):
				// CALLS THE 'getsRoot' AND 'createsUser' METHODS TO WRITE THE
				// NEW USER INFORMATION TO 'users.XML' FILE. 
				usersXML.getsRoot();
				usersXML.createUser();
				backToLogin();
			}

		}

	}
	
	@FXML
	private void backToLogin() {
		
		// TAKES YOU BACK TO LOGIN PAGE WHEN SIGN UP COMPLETED
		CinemaMain main = new CinemaMain();
		main.goToNextPage("view/LoginScreen.fxml", "Cinema Login");
	}
	
	@FXML
	private void cancelsSignUp() {
		
		// TAKES YOU BACK TO LOGIN PAGE WHEN CANCEL PRESSED
		CinemaMain main = new CinemaMain();
		main.goToNextPage("view/LoginScreen.fxml", "Cinema Login");
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	

	

}
