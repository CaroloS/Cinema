package Users;

import java.net.URL;
import java.util.ResourceBundle;

//import cinema.CinemaMain;
//import cinema.XML.CreateUserXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UserAddController implements Initializable {

	// DECLARES THE FXML TEXTFIELD VARIABLES TO COLLECT THE INPUT FROM
	@FXML
	private TextField firstName, lastName, emailAddress, phoneNumber, password, confirmPassword;

	@FXML
	private Button addUserButton;

	/**
	 * Adds a user to the 'user.XML' file uses the user input to text-fields to set
	 * the user variables. Calls 'createsUser' method from 'CreateUserXML' class
	 * 
	 * @param event
	 *            : event created from button click
	 */

	@FXML
	public void initialize() {
		addUserButton.setDisable(true);
	}

	@FXML
	private void addUser(ActionEvent event) {

		if (event.getSource().equals(addUserButton)) {

			// System.out.println("Button" + addUserButton);
			// }

			// CREATES AN INSTANCE OF 'CreateXML'
			CreateUsersXML usersXML = new CreateUsersXML("users.xml", "users");

			// GETS THE USER INPUT FROM TEXTFIELDS AND SETS INSTANCE VARIABLES OF
			// 'filmXML' WITH IT
			usersXML.setFirstName(firstName.getText());
			usersXML.setLastName(lastName.getText());
			usersXML.setEmailAddress(emailAddress.getText());
			usersXML.setPhoneNumber(phoneNumber.getText());
			usersXML.setPassword(password.getText());
			// usersXML.setDate(filmDate.getText());

			// CALLS THE 'getsRoot' AND 'createsFilm' METHODS TO WRITE THE NEW FILM
			// INFORMATION TO 'film.XML' FILE
			usersXML.getsRoot();
			usersXML.createUser();
		}
	}

	// @FXML
	// private void goToWhatsOn(ActionEvent event) {
	//
	// // TAKES USER TO 'WhatsOnEmployee' PAGE WHEN 'WHATS ON' MENU ITEM
	// // CLICKED
	// CinemaMain main = new CinemaMain();
	// main.goToNextPage("view/WhatsOnEmployee.fxml", "What's On");
	// }
	//
	// @FXML
	// private void logsOut(ActionEvent event) {
	//
	// // TAKES USER BACK TO 'Cinema Login' PAGE WHEN 'LOG OUT' MENU ITEM
	// // CLICKED
	// CinemaMain main = new CinemaMain();
	// main.goToNextPage("view/LoginScreen.fxml", "Cinema Login");
	//
	// }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		addUserButton.setDisable(true);
	}

}
