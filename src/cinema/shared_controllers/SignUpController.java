package cinema.shared_controllers;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jdom2.Element;

import cinema.CinemaMain;
import cinema.XML.CreateUsersXML;
import cinema.XML.EditUserXML;
import cinema.XML.ReadXMLFile;
import cinema.employee_controllers.EmployeeHomeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 * This class has methods and fields to create a new user from the sign up page or 
 * to edit a user's information from inside the cinema application. 
 * It implements initialise to parse the 'user.xml' file on loading and to populate
 * the input fields with the current user information if editing their profile. 
 * @author carolinesmith, daianabassi.
 *
 */
public class SignUpController implements Initializable {

	//DECLARES ALL THE FXML ELEMENTS USED BY THIS CONTROLLER
	@FXML
	private TextField firstName, lastName, emailAddress, phoneNumber, userName, password, confirmPassword;
	@FXML
	private Button addUserButton, cancel;
	@FXML
	private RadioButton customerProfile;
	@FXML
	private RadioButton employeeProfile;
	@FXML
	private Label picLabel;

	public static String filePicName, editFirstName, editLastName, editEmailAddress, editPhoneNumber, editUserName,
			editPassword, editConfirmPassword;

	Alert alert = new Alert(AlertType.WARNING);

	Element root, root2;	//XML ROOT ELEMENTS TO SET WHEN PARSING XML DOCUMENTS
	List list, list2;		//LISTS TO PASS XML NODES TO FROM XML PARSING

	/**
	 * Gets the information from the input fields and either creates a new user in user.xml or 
	 * edits a user depending on where the user has navigated to the sign up page from 
	 * (new sign up or edit profile). 
	 * @param event the button click event for sign up form submission.
	 */
	@FXML
	private void addUser(ActionEvent event) {

		if (event.getSource().equals(addUserButton)) {

			// CREATES INSTANCES OF 'CreateXML' AND 'editUserCML'
			CreateUsersXML usersXML = new CreateUsersXML("users.xml", "users");
			EditUserXML editXML = new EditUserXML("users.xml", "users");

			// GETS THE USER INPUT FROM TEXTFIELDS AND SETS INSTANCE VARIABLES OF
			// 'CreateUsersXML' WITH IT
			// CHECKS IF INPUT FIELDS ARE EMPTY AND CREATES ALERTS IF THEY ARE
			// ALSO PASSES THE INPUT TO THE STATIC VARIABLES OF THIS CLASS WHICH
			// ARE USED TO EDIT THE XML IF A USER IS EDITING THEIR PROFILE.

			int x = 0;

			if (!firstName.getText().trim().isEmpty()) {
				usersXML.setFirstName(firstName.getText());
				editFirstName = firstName.getText();
			} else {
				x = 1;
			}

			if (!lastName.getText().trim().isEmpty()) {
				usersXML.setLastName(lastName.getText());
				editLastName = lastName.getText();
			} else {
				x = 1;
			}

			if (!picLabel.getText().trim().isEmpty()) {
				usersXML.setProfilePic(picLabel.getText());
			} else {
				x = 1;    //USES A DUMMY INAGE IF USER DOESN'T SET A PROFILE PICTURE
			}

			if (!emailAddress.getText().trim().isEmpty()) {
				if (emailAddress.getText().contains("@")) {           //CREATES AN ALERT IF EMAIL DOESN'T CONTAIN '@'
					usersXML.setEmailAddress(emailAddress.getText());
					editEmailAddress = emailAddress.getText();
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
				if (phoneNumber.getText().matches("[0-9]+") && (phoneNumber.getText().length() > 5)) {     //PHONE NUMBER MUST BE >5 NUMBERS
					usersXML.setPhoneNumber(phoneNumber.getText());											//AND ONLY CONTAIN 0-9 DIGITS
					editPhoneNumber = phoneNumber.getText();
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

		
			if (!userName.getText().trim().isEmpty()) {
				if (userName.getText().length() > 4) {   		//USERNAME MUST BE OVER 4 CHARACTERS LONG
					if (LoginController.userID == null) {       //IF THE SIGN UP PAGE IS BEING ACCESSED BY A NEW USER
						if (list != null) {						//CHECKS THROUGH THE 'user.xml' TO CHECK USERNAME INPUT IS NOT ALREADY IN USE
							for (int i = 0; i < list.size(); i++) {
								Element node = (Element) list.get(i);
								String xmlusername = node.getChildText("UserName");

								if (xmlusername.equals(userName.getText())) {
									x = 2;
									alert.setTitle("Username");
									alert.setHeaderText("Invalid Username");
									alert.setContentText("Username already exists!");
									alert.showAndWait();
								}
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
				editUserName = userName.getText();
			} else {
				x = 1;
			}

			if (customerProfile.isSelected()) {
				usersXML.setUserProfile("customer");
			} else if (employeeProfile.isSelected()) {
				usersXML.setUserProfile("employee");
			}

			if (!password.getText().trim().isEmpty() && !confirmPassword.getText().trim().isEmpty()) {
				if (password.getText().equals(confirmPassword.getText())) { 		//CHECKS BOTH PASSWORDS FIELDS MATCH
					if (password.getText().length() > 5) {							//AND PASSWORD IS OVER 5 CHARACTERS
						usersXML.setPassword(password.getText());
						editPassword = password.getText();
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
				if (LoginController.userID == null) {
					usersXML.getsRoot();
					usersXML.createUser();
					backToLogin();
				} else {
					//ELSE IF THE USER IS ALREADY KNOWN AND IS EDITNG THEIR PROFILE
					//CALLS 'editsBookingXML()' TO EDIT THEIR INFORMATION
					editXML.editsBookingXML();

					//NAVIGATES BACK TO EMPLOYEE OR CUSTOMER PAGE DEPENDING ON WHO IS LOGGED IN 
					if (EmployeeHomeController.editEmployee == true) {
						CinemaMain main = new CinemaMain();
						main.goToNextPage("employee_view/EmployeeHome.fxml", "Employee Home");
					} else {
						CinemaMain main = new CinemaMain();
						main.goToNextPage("customer_view/CustomerAccount.fxml", "Customer Account");
					}

				}

			}

		}

	}

	/**
	 * Opens a file chooser for a user to pick a profile picture. 
	 * Adds 'images/' to the filename to set the correct relative path to the image.
	 * Writes the image path to a label for use in add user method.
	 */
	public void selectsProfilePic() {
		final FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(CinemaMain.thestage);

		filePicName = "images/" + file.getName();
		picLabel.setText(filePicName);

	}

	/**
	 * Called to initialize a controller after its root element has been completely processed.
	 * Parses the 'user.xml' file and gets the list of child elements of user root node.
	 * If the user is known already (and logged in) - they are editing their profile - so 
	 * sets the input fields on the page with the current user profile informatin ready for editing. 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		File xmlFile = new File("users.xml");

		//IF 'users.xml' EXSISTS PARSES IT BY CALLING 'readsXML' FROM 'cinema.XML.ReadXMLFile'
		if (xmlFile.exists()) {
			try {
				ReadXMLFile read = new ReadXMLFile("users.xml");
				root = read.readsXML();
			} catch (Exception e) {
				CinemaMain.LOGGER.warning("Couldn't parse users.XML");
			}
			list = root.getChildren("User");

			// PARSES XML: ITERATES THROUGH THE 'FILM' LIST, GETS
			// ELEMENT/ATTRIBUTES
			// AND PASSES IT TO FILM VARIABLES
			list = root.getChildren("User");

			if (LoginController.userID != null) {

				for (int i = 0; i < list.size(); i++) {

					Element node = (Element) list.get(i);

					if (node.getAttributeValue("id").equalsIgnoreCase(LoginController.userID)) {

						firstName.setText(node.getChildText("FirstName"));
						lastName.setText(node.getChildText("LastName"));
						emailAddress.setText(node.getChildText("EmailAddress"));
						phoneNumber.setText(node.getChildText("PhoneNumber"));
						userName.setText(node.getChildText("UserName"));
						password.setText(node.getChildText("Password"));
						confirmPassword.setText(node.getChildText("Password"));
						picLabel.setText(node.getChildText("profilePic"));
						filePicName = node.getChildText("profilePic");

						//A CUSTOMER OR EMPLOYEE CANNOT EDIT THEIR PROFILE TO SWITCH ROLES
						if (node.getChildText("UserProfile").equalsIgnoreCase("customer"))
							employeeProfile.setDisable(true);
						else if (node.getChildText("UserProfile").equalsIgnoreCase("employee"))
							customerProfile.setDisable(true);

					}
				}
			}

		}
	}



	/**
	 * Takes user back to login page when sign up completed.
	 * Calls <code>goToLoginPage</code> function from <code>cinema.CinemaMain</code>
	 * @param event the button click event.
	 */
	@FXML
	private void backToLogin() {

		// TAKES YOU BACK TO LOGIN PAGE WHEN SIGN UP COMPLETED
		CinemaMain main = new CinemaMain();
		main.goToLoginPage("shared_view/LoginScreen.fxml", "Cinema Login");
	}

	/**
	 * Takes user back to either log in page or customer account or employee account
	 * depending on who is logged in or if it is a new user.
	 * Calls <code>goToNextPage</code> function from <code>cinema.CinemaMain</code>
	 * @param event the button click event
	 */
	@FXML
	private void cancelsSignUp() {

		if (LoginController.userID == null) {
			// TAKES YOU BACK TO LOGIN PAGE WHEN CANCEL PRESSED
			CinemaMain main = new CinemaMain();
			main.goToLoginPage("shared_view/LoginScreen.fxml", "Cinema Login");
		} else if (EmployeeHomeController.editEmployee == true) {
			CinemaMain main = new CinemaMain();
			main.goToNextPage("employee_view/EmployeeHome.fxml", "Employee Home");
		} else {
			CinemaMain main = new CinemaMain();
			main.goToNextPage("customer_view/CustomerAccount.fxml", "Customer Account");
		}

	}

}
