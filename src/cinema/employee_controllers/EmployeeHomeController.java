package cinema.employee_controllers;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.jdom2.Element;

import cinema.CinemaMain;
import cinema.XML.CreateFilmBookingsXML;
import cinema.XML.CreateFilmXML;
import cinema.XML.ReadXMLFile;
import cinema.shared_controllers.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 * Controller class for Employee Home page. Defines functions and fields that
 * allow an employee to add a new film to the cinema. Checks the new film
 * information against 'film.xml' file to ensure showings in the cinema don't
 * overlap, and that films are only added for future dates.
 * 
 * @author carolinesmith, daianabassi
 *
 */
public class EmployeeHomeController implements Initializable {

	// DECLARES ALL THE FXML ELEMENTS USED BY THIS CONTROLLER
	@FXML
	private TextField filmTitle, filmStart, filmGenre, filmDescription;
	@FXML
	private Button imageButton;
	@FXML
	private Label pictureLabel, date1;
	@FXML
	private DatePicker datePicker;
	@FXML
	private ComboBox timePicker, genrePicker;

	// DECALRES A STATIC VARIABLE THAT IS SET TO TRUE WHEN EMPLOYEE SELECTS TO EDIT
	// THEIR PROFILE INFORMATION. IT IS CHECKED BY THE SIGN UP CONTROLLER TO DIFFERENTIATE
	// AN EMPLOYEE EDITING THEIR INFORMATION FROM A CUSTOMER.
	public static Boolean editEmployee = false;

	String fileName;
	StringBuffer dateTimes = new StringBuffer(); // A STRINGBUFFER TO COLLECT DATE/TIME INFORMATION FOR A NEW FILM

	Element root = null; // AN XML ROOT ELEMENT TO SET WHEN PARSING XML DOCUMENTS
	List list = null; // A LIST TO PASS THE XML NODES TO FROM XML PARSING

	Alert alert = new Alert(AlertType.WARNING);

	/**
	 * Called to initialize a controller after its root element has been
	 * completely processed. Populates the ComboBoxes with values and parses the
	 * 'film.xml' file.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// POPULATES THE COMBOBOXES WITH VALUES ON PAGE INITIALISATION
		timePicker.getItems().addAll("1200", "1300", "1400", "1500", "1600", "1700", "1800", "1900", "2000");
		genrePicker.getItems().addAll("Horror", "Comedy", "Children's", "Action", "Love");

		File xmlFile = new File("film.xml");

		//IF 'film.xml' EXSISTS PARSES IT BY CALLING 'readsXML' FROM 'cinema.XML.ReadXMLFile'
		if (xmlFile.exists()) {
			try {
				ReadXMLFile read = new ReadXMLFile("film.xml");
				root = read.readsXML();    							   //RETURNS THE ROOT NODE
			} catch (Exception e) {
				CinemaMain.LOGGER.warning("Couldn't parse film.XML");
			}
			list = root.getChildren("film"); 							//RETURNS A LLIST OF CHILD NODES OF THE ROOT
		}
	}

	/**
	 * Adds a list of selected dates and times for a new film to a StringBuffer
	 * - won't add date/time if date is in the past or if another film is
	 * already being shown on that date/time.
	 * 
	 * @param event the button click event to add the date + time selected
	 */
	@FXML
	private void addsDate(ActionEvent event) {

		int x = 0;
		String dateTime = null;
		StringBuffer sb = null;

		String time = (String) timePicker.getSelectionModel().getSelectedItem(); // GETS THE TIME SELECTED

		if (time == null)														//CHECKS IF TIME HAS BEEN SELECTED
			x = 3;

		String date = null;
		if (datePicker.getValue() == null)										//CHECKS IF A DATE HAS BEEN SELECTED
			x = 3;
		else {
			date = datePicker.getValue().format(DateTimeFormatter.ofPattern("dd-MM-YY")); // GETS THE DATE SELECTED

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy"); // CREATES INTANCE OF 'SimpleDateFormat' TO PARSE STRING TO DATE
			Date selectedDate = null;
			Date today = new Date();
			try {
				selectedDate = sdf.parse(date); 								// PARSES THE STRING DATE SELECTED TO A DATE
			} catch (ParseException e) { 										// CATCHES PARSE EXCEPTION
				CinemaMain.LOGGER.warning("Couldn't parse String date to Date");
				e.printStackTrace();
			}

			dateTime = date + " " + time; 										// CONCATENATES DATE AND TIME SELECTED

			if (today.compareTo(selectedDate) >= 0)								 // CHECKS IF DATE SELECTED IS IN THE PAST
				x = 1;

			sb = new StringBuffer(""); 	// STRING BUFFER TO COLLECT THE DATE/TIME INFORMATOIN FOR FILMS ALREADY BEING SHOWN
										
			int a = 0;
			if (list != null) {

				for (int i = 0; i < list.size(); i++) { 	// ITERATES THROUGH THE LIST OF XML NODES IN 'film.xml' THAT WAS PARSED ON INITIALISATION
					Element node = (Element) list.get(i);
														
					String[] dT = node.getChildText("dateTimes").split(","); // GETS THE DATE/TIME INFORMATOIN FOR EACH FILM IN 'film.xml'
					for (String element : dT) {
						if (element.equalsIgnoreCase(dateTime)) 		// CHECKS IF THE DATE/TIME SELECTED FOR THE NEW FILM IS THE SAME 
							x = 2; 										// AS A FILM ALREADY BEING SHOWN
						sb.append(element + "\n"); 						// ADDS THE DATE/TIME INFORMATION TO THE STRING BUFFER
					} 													// FOR USE IN THE ALERT IF OVERLAPPING TIMES
				}
			}
		}

		switch (x) {
		case (1):
			alert.setTitle("Warning"); 				// ALERT IF DATE IS IN THE PAST - CAN'T ADD THIS DATE
			alert.setHeaderText("Date is in the past!");
			alert.setContentText("Please select a date in the future");
			alert.showAndWait();
			break;
		case (2):
			alert.setTitle("Warning"); 				// ALERT IF ANOTHER FILM ALREADY BEING SHOWN AT THAT TIME - CAN'T ADD THIS DATE
			alert.setHeaderText("Another film is being shown at the same time!");
			alert.setContentText("Please select another date or time. \n\nThe dates/times with films "
					+ "already booked at this cinema are: \n\n" + sb.toString());
			alert.showAndWait();
			break;
		case (3):
			alert.setTitle("Warning"); 				// ALERT IF NO TIME SELECTED
			alert.setHeaderText("Empty Fields!");
			alert.setContentText("Please select a date and a time!");
			alert.showAndWait();
			break;
		case (0): 									// OTHERWISE - SET THE LABEL WITH THE SELECTED DATE/TIME
			date1.setWrapText(true);
			date1.setText(date1.getText() + " " + dateTime + ",");
			dateTimes.append(dateTime + ","); 		// AND APPEND THIS DATE/TIME TO THE STRINGBUFFER OF DATE/TIMES FOR NEW FILM

		}
	}

	// FUNCTION FOR OPENING FILECHOOSER TO PICK AN IMAGE, WRITES RELATIVE PATH TO A LABEL TO WRITE TO XML
	/**
	 * Opens a FileChooser for employee to pick a film image. Writes the
	 * relative path of the image to a lavel to write to XML.
	 * 
	 * @param event the click event to open FileChooser.
	 */
	@FXML
	private void selectImage(ActionEvent event) {

		final FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(CinemaMain.thestage); // OPENS A FILECHOOSER ON THE MAIN STAGE

		fileName = file.getName(); 									// GETS NAME OF FILE SELECTED
		pictureLabel.setText("images/" + fileName); 				// APPENDS 'images/' TO MAKE THE CORRECT RELATIVE PATH
	}

	/**
	 * Adds a film to the 'film.XML' file Uses the employee input to text-fields
	 * to set the film variables. Calls
	 * <code>cinema.XML.CreateFilmXML.createFilm()</code>.
	 * 
	 * @param event the button click event to add a new film
	 */
	@FXML
	private void addFilm(ActionEvent event) {

		int b = 0;

		if (list != null) {

			for (int i = 0; i < list.size(); i++) { 			// ITERATES THROUGH NODES IN 'film.xml' AND CHECKS IF A FILM WITH THE SAME NAME
				Element node = (Element) list.get(i); 			// IS ALREADY BEING SHOWN
				if (node.getChildText("title").equalsIgnoreCase(filmTitle.getText())) {
					b = 1;
				}
			}

			// CHECKS IF ALL REQUIRED INPUT FIELDS ARE FILLED IN
			if (filmTitle.getText().length() == 0 || genrePicker.getSelectionModel().getSelectedItem() == null
					|| date1.getText().length() == 0 || filmDescription.getText().length() == 0
					|| pictureLabel.getText().length() == 0) {
				b = 2;
			}

		}

		switch (b) {
		case (1):
			alert.setTitle("Warning");							 // ALERT IF ALREADY SHOWING THAT FILM - CAN'T ADD IT AGAIN
			alert.setHeaderText("We're already showing that film!");
			alert.setContentText("We're very exclusive and don't repeat shows here! \n Add a different movie!");
			alert.showAndWait();
			break;
		case (2):
			alert.setTitle("Warning"); 							// ALERT IF SOME FIELDS ARE UNFILLED - NEED TO COMPLETE THEM
			alert.setHeaderText("Some fields are unfilled!");
			alert.setContentText("Please fill in all the film information fields");
			alert.showAndWait();
			break;
		default:
			// CREATES AN INSTANCE OF 'CreateFilmXML'
			CreateFilmXML filmXML = new CreateFilmXML("film.xml", "films");

			// GETS THE USER INPUT AND SETS INSTANCE VARIABLES OF 'filmXML' 
			filmXML.setTitle(filmTitle.getText());
			filmXML.setDescription(filmDescription.getText());
			filmXML.setGenre(genrePicker.getSelectionModel().getSelectedItem().toString());
			filmXML.setDateTimes(dateTimes.toString());
			filmXML.setImage(pictureLabel.getText());


			// CALLS THE 'getsRoot' AND 'createsFilm' METHODS TO WRITE THE NEW FILM
			// INFORMATION TO 'film.XML' FILE
			filmXML.getsRoot();
			filmXML.createsFilm();

			// CREATES AN INSTANCE OF 'CreateFilmBookingsXML'
			CreateFilmBookingsXML bookingsXML = new CreateFilmBookingsXML("filmBookings.xml", "bookings");

			String[] arrDateTimes = dateTimes.toString().split(","); 	// GETS A LIST OF ALL DATE/TIMES A NEW FILM IS BEING SHOWN
																		
			for (int i = 0; i < arrDateTimes.length; i++) { 			// ITERATES THROUGH THIS LIST TO CREATE A BOOKING XML NODE FOR EACH SHOWING
				String attribute = filmTitle.getText() + " " + arrDateTimes[i];
				bookingsXML.setBookingAttribute(attribute); 			// ATTRIBUTE IS A RANDOM INTEGER (PARSED TO STRING) SET IN 'CreateFilmBookingsXML'
				bookingsXML.setBookedSeats(""); 						// BOOKED SEATS IS INITIALLY EMPTY
				bookingsXML.setBookedNumber("0"); 						// NUMNBER BOOKED IS INITIALLY 0
				bookingsXML.setUnBookedNumber("36"); 					// NUMBER AVAILABLE IS INITIALLY 36

				bookingsXML.getsRoot();
				bookingsXML.createsBookings();
			}
			Alert alert2 = new Alert(AlertType.CONFIRMATION); 			// CONFIRMATION ALERT WHEN FILM SUCCESSFULLY ADDED
			alert2.setTitle("Film Added!");
			alert2.setHeaderText("Your film has been added");
			alert2.setContentText("Check 'what's on' to see the listing");
			alert2.showAndWait();

		}

	}

	///// NAVIGATION FUNCTIONS/////

	/**
	 * Loads the employee account page when menu item selected. Calls
	 * <code>goToNextPage</code> function from <code>cinema.CinemaMain</code>
	 * 
	 * @param event the menu item click event
	 */
	@FXML
	private void goesToEmpAccount(ActionEvent event) {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("customer_view/CustomerAccount.fxml", "Employee Account");
	}

	/**
	 * Loads the Sign Up page when edit profile information button clicked. Sets
	 * <code>edtiEmployee</code> to true to differentiate employee from customer
	 * editing. Calls <code>goToNextPage</code> function from
	 * <code>cinema.CinemaMain</code>
	 * 
	 * @param event the button click event
	 */
	@FXML
	public void editsEmployeeProfile() {
		editEmployee = true;
		CinemaMain main = new CinemaMain();
		main.goToNextPage("shared_view/SignUpPage.fxml", "Edit Information for Employee: " + LoginController.userID);
	}

	/**
	 * Loads the employee what's on page when menu item selected. Calls
	 * <code>goToNextPage</code> function from <code>cinema.CinemaMain</code>
	 * 
	 * @param event the menu item click event
	 */
	@FXML
	private void goToWhatsOn(ActionEvent event) {

		CinemaMain main = new CinemaMain();
		main.goToNextPage("employee_view/WhatsOnEmployee.fxml", "What's On");
	}

	/**
	 * Takes use back to login page when log out menu item selected. Calls
	 * <code>goToLoginPage</code> function from <code>cinema.CinemaMain</code>
	 * 
	 * @param event the menu item click event
	 */
	@FXML
	private void logsOut(ActionEvent event) {

		CinemaMain main = new CinemaMain();
		main.goToLoginPage("shared_view/LoginScreen.fxml", "Cinema Login");

	}

	/**
	 * Loads the employee 'all bookings' page when menu item selected. Calls
	 * <code>goToNextPage</code> function from <code>cinema.CinemaMain</code>
	 * 
	 * @param event the menu item click event
	 */
	@FXML
	private void goToAllBookings(ActionEvent event) {

		CinemaMain main = new CinemaMain();
		main.goToNextPage("employee_view/AllBookings.fxml", "All Bookings");

	}

}