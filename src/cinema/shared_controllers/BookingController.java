package cinema.shared_controllers;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.jdom2.Element;

import cinema.CinemaMain;
import cinema.XML.CreateUserBookingsXML;
import cinema.XML.EditBookingsXML;
import cinema.XML.ReadXMLFile;
import cinema.customer_controllers.WhatsOnCustController;
import cinema.employee_controllers.WhatsOnEmpController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Controller class for the booking page. Lays out the cinema with empty or orange chair
 * icons based on information from parsing the 'filmBookings.xml' file. Defines a function that
 * allows only a customer to make a seat booking if the seat is available. Updates xml files 
 * with booking information. 
 * @author carolinesmith, daianabassi
 *
 */
public class BookingController extends WhatsOnEmpController implements Initializable {

	//DECLARES ALL THE FXML ELEMENTS USED BY THIS CONTROLLER//
	@FXML
	protected AnchorPane seatAnchor;
	@FXML
	protected Label filmSelection, bookingPrompt, confirmLabel1, confirmLabel2, bookingInfo1, bookingInfo2,
			bookingInfo3, bookingInfo4;

	// THE PAGE TITLE IS UNIQUE FOR EACH FILM/DATE/TIME. IT WAS SET IN THE BUTTON HANDLER
	// METHOD IN THE WHATS ON PAGE WHEN A USER SELECTS A DATE AND FILM.
	protected String strPageTitle = WhatsOnCustController.pageTitle;

	// DECLARES STATIC VARIABLES TO BE SET WHEN THE 'filmBookings.xml' FILE IS READ
	// THESE ARE ALTERED WHEN A USER BOOKS A SEAT AND  
	// 'EditBookingsXML.editsBookingXML' IS CALLED TO EDIT THE XML WITH NEW VALUES
	public static String bookedSeats = null;
	public static String totalBooked = null;
	public static String totalUnBooked = null;

	// DECALRES THE ROOT ELEMENT TO BE SET BY PARSING 'filmBookings.xml'
	Element root;
	//A LIST TO PASS THE XML NODES TO FROM XML PARSING
	List list;


	/**
	 * Called to initialize a controller after its root element has been completely processed.
	 * Parses 'filmBookings.xml' and sets the seats on the page to empty icon if available and
	 * orange icon if booked. Displays information about number of booked/available seats to the 
	 * employee. 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		filmSelection.setText(strPageTitle);
		Image emptyChair = new Image("images/emptychair.png", 50, 50, true, true);

		// ITERATES THROUGH ALL THE BUTTONs AND ADDS AN EMPTY CHAIR ICON
		for (int i = 4; i < (seatAnchor.getChildren().size()); i++) {

			Button btn = (Button) seatAnchor.getChildren().get(i);
			ImageView chair = new ImageView(emptyChair);
			chair.setId("empty");
			btn.setStyle("-fx-background-color: rgba(0,0,0,0.05);");
			btn.setGraphic(chair);

		}

		// PARSING FILM BOOKING XML FILE
		try {
			ReadXMLFile read = new ReadXMLFile("filmBookings.xml");
			root = read.readsXML();
		} catch (Exception e) {
			CinemaMain.LOGGER.warning("Couldn't parse film.XML");
		}
		list = root.getChildren("filmBooking");

		// GETS THE SEAT BOOKING INFORMATION FROM THE 'filmBookings.xml' FILE
		// WHERE THE FILM ATTRIBUTE EQUALS THE FILM CURRENTLY BEING DISPLAYED ON
		// THE PAGE (i.e. ATTRIBUTE MATCHES UNIQUE PAGE TITLE
		for (int i = 0; i < list.size(); i++) {

			Element node = (Element) list.get(i);
			String xmlAttr = node.getAttributeValue("name");

			if (xmlAttr.equalsIgnoreCase(strPageTitle)) {
				bookedSeats = node.getChildText("bookedSeats");
				totalBooked = node.getChildText("bookedNumber");
				totalUnBooked = node.getChildText("unBookedNumber");

			}
		}

		// ITERATES THROUGH A LIST OF SEATS BOOKED AND THE SEATS IN THE CINEMA LAYOUT
		// WHERE THE SEAT BOOKED MATCHES THE SEAT ID - CHANGES THE SEAT IMAGE TO
		// ORANGE CHAIR
		if (bookedSeats.length() > 0) {
			String[] arrBookedSeats = bookedSeats.split(" ");

			for (int a = 0; a < arrBookedSeats.length; a++) {
				for (int i = 4; i < seatAnchor.getChildren().size(); i++) {

					Button btn = (Button) seatAnchor.getChildren().get(i);

					if (arrBookedSeats[a].equalsIgnoreCase(btn.getId())) {
						Image orangeChair = new Image("images/orangechair.png", 50, 50, true, true);
						ImageView bookedChair = new ImageView(orangeChair);
						bookedChair.setId("orange");
						btn.setGraphic(bookedChair);
					}
				}
			}
		}

		// IF THE LOGGED IN USER IS AN EMPLOYEE DISPLAYS INFORMATION ABOUT THE BOOKINGS
		// PRINTS THE SEAT BOOKING INFORMATION TO THE LABELS IN THE VBOX BELOW THE CINEMA LAYOUT
		if (LoginController.loggedInUser.equalsIgnoreCase("employee")) {
			if (bookedSeats.length() > 0)
				bookingInfo1.setText("Seats Booked: " + bookedSeats);
			else
				bookingInfo1.setText("Seats Booked: None yet!");
			bookingInfo2.setText("Total seats booked: " + totalBooked);
			bookingInfo3.setText("Total seats unbooked: " + totalUnBooked);
			bookingInfo4.setText("Booking Information for this film:");
		}

		// IF USER IS A CUSTOMER DISPLAYS A PROMPT TO PICK A SEAT
		if (LoginController.loggedInUser.equalsIgnoreCase("customer")) {
			bookingPrompt.setText("Don't miss the chance to see this film! Click on a seat to book it!");
		}

	}

	/**
	 * Allows a customer to book a seat. Alerts customer if seat is already taken - can't book. 
	 * Otherwise alerts customer to confirm or cancel their booking after reviewing the booking information.
	 * Adds a new node to 'userBookings.xml' to reflect this booking. And edits 'filmBookings.xml; with
	 * new values about booked and available seats. 
	 * @param event
	 */
	public void seatSelected(ActionEvent event) {

		//CHECKS IF THE USER IS A CUSTOMER - EMPPLOYEE CAN'T BOOK A SEAT VIA EMPLOYEE SIDE
		if (LoginController.loggedInUser.equalsIgnoreCase("customer")) {

			// GETS INFORMATION ABOUT THE SEAT SELECTED
			Button btn2 = (Button) event.getSource();
			String seatNumber = btn2.getId();
			ImageView selectedChair = (ImageView) btn2.getGraphic();
			String chairType = selectedChair.getId();

			// IF SEAT IS BOOKED (ORANGE) ALERT TO SAY PICK ANOTHER SEAT
			if (chairType.equalsIgnoreCase("orange")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("Sorry that seat is taken!");
				alert.setContentText("No sitting on each other laps at this cinema! Best pick another seat!");
				alert.showAndWait();
			} else {

				// PARSES THE FILM INFORMATION TO USE IN THE CONFIRMATION ALERT
				// USING THE UNIQUE PAGE TITLE
				String[] confirmInfo = strPageTitle.split(" ");
				StringBuffer confirmName = new StringBuffer("");
				String confirmTime = confirmInfo[confirmInfo.length - 1];
				String confirmDate = confirmInfo[confirmInfo.length - 2];

				for (int i = 0; i < confirmInfo.length - 2; i++)
					confirmName.append(confirmInfo[i] + " ");

				// PRESENTS AN ALERT TO ASK THEM TO CONFIRM THEIR BOOKING OR CANCEL
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText("Great choice, that seat is free - go ahead and book!");
				alert.setContentText("By cicking OK you will be confirmed for " + confirmName + " on " + confirmDate
						+ " at " + confirmTime + " in seat " + seatNumber
						+ ". Or click cancel and pick another seat. \n\n " + "ARE YOU SURE YOU WANT TO BOOK?");

				
				// IF THEY SELECT OK - ADDS THE USER'S BOOKING TO THE 'userBookings.xml' FILE
				// AND EDITS THE 'filmBookings.xml' FILE WITH THE NEW VALUES AFTER BOOKING A SEAT
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {

					// CREATES A NEW ENTRY IN 'userBookings.xml' FOR THE LOGGED IN USER
					// SETS THE CHILD ELEMENTS TO THE INFORMATION FOR THE FILM THEY BOOKED
					CreateUserBookingsXML newBooking = new CreateUserBookingsXML("userBookings.xml", "userBookings");
					newBooking.setFilmName(confirmName.toString());
					newBooking.setFilmDate(confirmDate);
					newBooking.setFilmTime(confirmTime);
					newBooking.setSeatBooked(seatNumber);

					newBooking.getsRoot();
					newBooking.CreateUserBooking();

					// UPDATES 'filmBookingXML' WITH NEW TOTALS FOR BOOKED AND UNBOOKED
					// AND APPENDS THE SEAT NUMBER TO THE SEATS BOOKED ELEMENT
					int booked = Integer.parseInt(totalBooked);
					totalBooked = Integer.toString(++booked);    	//INCREMENTS BOOKED SEAT NUMBER BY ONE

					int unbooked = Integer.parseInt(totalUnBooked);
					totalUnBooked = Integer.toString(--unbooked);  //DECREMENTS AVAILABLE SEAT NUMBER BY ONE

					StringBuffer sb = new StringBuffer("");
					sb.append(bookedSeats);							//ADDS THE PREVIOUSLY BOOKED SEATS TO A STRING BUFFER
					sb.append(" " + seatNumber);  					//APPENDS THE NEW BOOKED SEAT TO THE STRING BUFFER
					bookedSeats = sb.toString();					//RESETS 'bookedSeats' STATIC FIELD WITH PREVIOUS SEATS AND NEW SEAT BOOKED
					
					EditBookingsXML alter = new EditBookingsXML("filmBookings.xml", "bookings");  
					alter.editsBookingXML();						//ALTERS THE XML WITH NEW BOOKING VALUES

					// CHANGES THE CHAIR TO ORANGE ON CONFIRMATION OF BOOKING
					Image orangeChair = new Image("images/orangechair.png", 50, 50, true, true);
					ImageView bookedChair = new ImageView(orangeChair);
					bookedChair.setId("orange");
					btn2.setGraphic(bookedChair);

					// SETS SOME TEXT CONFIRMING BOOKINGS DETAILS UNDER THE CINEMA LAYOUT
					confirmLabel1.setText("Thanks " + LoginController.usersName + " you're all booked!");
					confirmLabel2.setText("Your ticket will be sent to you by email");
					bookingInfo4.setText("Your film details:");
					bookingInfo1.setText(confirmName + " on " + confirmDate + " at " + confirmTime);
					bookingInfo2.setText("Seat: " + seatNumber);

				} else {
					// ... user chose CANCEL or closed the dialog
				}
			}

		}
	}

	// TAKES EMPLOYEE BACK TO THE WHATS ON PAGE
	/**
	 * Loads the customer or employee what's on page when back to what's on button pressed. 
	 * Calls <code>goToNextPage</code> function from <code>cinema.CinemaMain</code>
	 * @param event the button/menu item click event
	 */
	public void goesBack(ActionEvent event) {

		if (LoginController.loggedInUser.equalsIgnoreCase("employee")) {
			CinemaMain main = new CinemaMain();
			main.goToNextPage("employee_view/WhatsOnEmployee.fxml", "What's On");
		}

		else if (LoginController.loggedInUser.equalsIgnoreCase("customer")) {
			CinemaMain main = new CinemaMain();
			main.goToNextPage("customer_view/WhatsOnCustomer.fxml", "What's On");
		}
	}

}
