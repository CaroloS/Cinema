package cinema.customer_controllers;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.jdom2.Element;

import cinema.CinemaMain;
import cinema.XML.EditBookingsXML;
import cinema.XML.EditUserBookingsXML;
import cinema.XML.ReadXMLFile;
import cinema.shared_controllers.BookingController;
import cinema.shared_controllers.LoginController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

/**
 * Controller class for the customer account page. On initialization, lays out
 * the customer profile information at the top of the page and the corresponding
 * list of film bookings for this customer at the bottom of the page. Defines
 * navigation functions to the rest of the customer pages and back to the sign
 * up page to allow customer to edit profile information. Defines a function to
 * allow a user to delete a booking if it's in the future.
 * 
 * @author carolinesmith, daianabassi
 *
 */
public class CustAccountController implements Initializable {

	// DECLARES ALL THE FXML ELEMENTS USED BY THIS CONTROLLER
	@FXML
	private VBox topAnchor;
	@FXML
	private Label firstNameLabel, lastNameLabel, emailLabel, numberLabel, usernameLabel, bookingHistory;
	@FXML
	private Pane picBox;
	@FXML
	private VBox bottomAnchor;
	@FXML
	private Menu allBookings;

	// LISTS TO PASS THE XML NODES TO FROM XML PARSING
	// 3 LISTS AND ROOT ELEMENTS ARE NEEDED AS 3 XML FILES ARE PARSED IN THIS
	// CLASS
	List list = null;
	List list2 = null;
	List list3 = null;
	// XML ROOT ELEMENTS TO SET WHEN PARSING XML DOCUMENTS
	Element root, root2, root3;

	// ARRAYLIST TO PASS THE USER FILM BOOKINGS TO
	protected List<GridPane> bookingList;

	public static String ID;

	/**
	 * Called to initialize a controller after its root element has been
	 * completely processed. Parses the 'user.xml' file to lay out the
	 * information for the logged in customer at the top of the account page.
	 * Parses the 'userBookings.xml' file to extract the film booking
	 * information for the logged in customer and display it in a list at the
	 * bottom of the page.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		bookingList = new ArrayList<>();

		if (LoginController.loggedInUser.equalsIgnoreCase("customer")) {
			allBookings.setVisible(false);
		}

		if (LoginController.loggedInUser.equalsIgnoreCase("employee")) {
			bookingHistory.setVisible(false);
		}

		
		File userFile = new File("users.xml");

		// IF 'user.xml' EXSISTS PARSES IT BY CALLING 'readsXML' FROM
		// 'cinema.XML.ReadXMLFile'
		if (userFile.exists()) {
			try {
				ReadXMLFile read = new ReadXMLFile("users.xml");
				root = read.readsXML(); // RETURNS THE ROOT NODE
			} catch (Exception e) {
				CinemaMain.LOGGER.warning("Couldn't parse user.XML");
			}

			// GETS LIST OF CHILD NODES OF THE ROOT ELEMENT IN 'user.xml'
			list = root.getChildren("User");

			// ITERATES THROUGH THE LIST OF XML NODES TO EXTRACT ALL THE
			// INFORMATION ABOUT THE USERS
			// TO DISPLAY THEIR PROFILE INFORMATION AT THE TOP OF THE PAGE
			for (int i = 0; i < list.size(); i++) {

				Element node = (Element) list.get(i);

				if (node.getAttributeValue("id").equalsIgnoreCase(LoginController.userID)) { // ONLY EXRTACTS INFORMATION FOR THE 
																							// LOGGED IN USER - STATIC FIELD WAS SET ON LOGIN
					String picImage = node.getChildText("profilePic");
					Image profilePic = new Image(picImage, 200, 200, false, false);
					ImageView viewProfilePic = new ImageView(profilePic);
					picBox.getChildren().add(viewProfilePic);

					firstNameLabel.setText(node.getChildText("FirstName"));
					lastNameLabel.setText(node.getChildText("LastName"));
					emailLabel.setText(node.getChildText("EmailAddress"));
					numberLabel.setText(node.getChildText("PhoneNumber"));
					usernameLabel.setText(node.getChildText("UserName"));
				}
			}
		}

		if (LoginController.loggedInUser.equalsIgnoreCase("customer")) {
			File userBookingFile = new File("userBookings.xml");

			// IF 'userBookings.xml' EXSISTS PARSES IT BY CALLING 'readsXML'
			// FROM 'cinema.XML.ReadXMLFile'
			if (userBookingFile.exists()) {
				try {
					ReadXMLFile read = new ReadXMLFile("userBookings.xml");
					root2 = read.readsXML(); // RETURNS THE ROOT NODE
				} catch (Exception e) {
					CinemaMain.LOGGER.warning("Couldn't parse user.XML");
				}

				// GETS LIST OF CHILD NODES OF THE ROOT ELEMENT IN
				// 'userBookings.xml'
				list2 = root2.getChildren("booking");

				// ITERATES THROUGH THE LIST OF XML NODES TO EXTRACT ALL THE
				// INFORMATION ABOUT THE LOGGED IN USER'S BOOKINGS
				for (int i = 0; i < list2.size(); i++) {

					Element node = (Element) list2.get(i);

					if (node.getAttributeValue("userID").equalsIgnoreCase(LoginController.userID)) {

						GridPane gridBooking = new GridPane(); // INITIALISES A GRIDPANE TO LAYOUT BOOKING INFORMATION
															
						gridBooking.setPrefSize(760, 100);
						gridBooking.setId(node.getAttributeValue("bookingID"));

						Label filmName = new Label(node.getChildText("filmName")); // LAYS OUT THE BOOKING INFORMATION IN THE GRIDPANE
						Label filmDate = new Label(node.getChildText("filmDate"));
						Label filmTime = new Label(node.getChildText("filmTime"));
						Label seatBooked = new Label("Seat Booked: " + node.getChildText("seatBooked"));
						Label empty = new Label("");
						Label empty2 = new Label("");

						gridBooking.add(filmName, 0, 0); 							// ADDS THE INFORMATION TO THE GRIDPANE
						gridBooking.add(filmDate, 1, 0);
						gridBooking.add(filmTime, 2, 0);
						gridBooking.add(seatBooked, 0, 1);
						gridBooking.add(empty,0, 3);
						gridBooking.add(empty2, 0, 4);

						gridBooking.getColumnConstraints().add(new ColumnConstraints(160)); // SETS SOME COLUMN CONSTRANTS 
						gridBooking.getColumnConstraints().add(new ColumnConstraints(100));
						gridBooking.getColumnConstraints().add(new ColumnConstraints(160));
						gridBooking.getRowConstraints().add(new RowConstraints(40));

						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy"); // CREATES INSTANCE OF 'SimpleDateFormat' TO PARSE STRING TO DATES
																				
						Date today = new Date();

						Date date = null;
						try {
							date = sdf.parse(node.getChildText("filmDate")); // PARSES THE STRING DATE FOR THIS BOOKING TO A DATE
																		
						} catch (ParseException e) {
							CinemaMain.LOGGER.warning("Couldn't parse filmDate");
							e.printStackTrace();
						}

						if (today.compareTo(date) < 0) { 						// IF THE BOOKING DATE IS IN THE FUTURE
							Button delete = new Button("cancel booking"); 		// ADDS A DELETE BOOKING BUTTON 
							delete.setPrefSize(150, 20);
							delete.setId(node.getAttributeValue("bookingID"));  // SETS THE DELETE BOOKING BUTTON A UNIQUE ID
							delete.setOnAction(buttonHandler); 					// THAT IS THE SAME AS THE BOOKING ID 
							gridBooking.add(delete, 1, 1, 2, 1); 				// ADDS A BUTTON HANDLER EVENT

						}
						bookingList.add(gridBooking); 						// ADDS THIS FILM BOOKING TO THE LIST OF BOOKINGS
					}

				}
			}

			if (bookingList.size() > 0) {
				bottomAnchor.getChildren().setAll(bookingList); 			// SETS THE LIST OF ALL BOOKINGS FOR THIS USER TO THE BOTTOM VBOX
														
			} 

			bottomAnchor.setPadding(new Insets(0, 0, 0, 170));
		}

	}

	/**
	 * Button handler event for the 'delete booking' button. Gets the
	 * information for the film booking selected for deletion. Parses
	 * 'useBookings.xml' to match the booking for deletion to the one selected -
	 * deletes the booking by calling
	 * <code>cinema.XML.EditUserBookingsXML.editsUserBooking()</code>. Parses
	 * 'filmBookings.xml' and updates the information about number of seats
	 * booked/available and deletes the seat booking - by callig
	 * <code>cinema.XML.EditBookingsXML.editsBookingXML()</code>
	 */
	final public EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(final ActionEvent event) {

			Button btn = (Button) event.getSource();
			ID = btn.getId(); // GETS THE UNIQUE BUTTON ID WHICH IS THE BOOKING
								// ID
			GridPane selectedGrid = (GridPane) btn.getParent(); // GETS THE PARENT GRIDPANE FOR THIS BOOKING

			// GETS THE INFORMATION ABOUT THIS BOOKING FROM THE GRIDPANE
			Label selectedName = (Label) selectedGrid.getChildren().get(0);
			Label selectedDate = (Label) selectedGrid.getChildren().get(1);
			Label selectedTime = (Label) selectedGrid.getChildren().get(2);
			Label selectedSeat = (Label) selectedGrid.getChildren().get(3);
			String[] getSeat = selectedSeat.getText().split(" ");
			String seat = getSeat[2];
			System.out.println(seat);

			// SETS THE UNIQUE PAGE TITLE STATIC VARIABLE FROM
			// 'WhatsOnCustController' WITH THE FILM BOOKING INFO
			String selectedFilm = selectedName.getText() + " " + selectedDate.getText() + " " + selectedTime.getText();
			WhatsOnCustController.pageTitle = selectedFilm;

			// PRESENT AN ALERT TO CONFIRM THEY WANT TO CANCEL BOOKING
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Cancel Booking");
			alert.setHeaderText("Are you sure you want to cancel this booking?");
			alert.setContentText("By clicking OK you're booking will be cancelled");

			// IF THEY PRESS OK - PROCEED TO CANCEL THE BOOKING
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {

				// DELETES THE BOOKING FROM 'userBookings.xml' BY CREATING AN INSTANCE OF 'EditUserBookingsXML' and CALLING
				// THE INSTANCE METHOD TO EDIT THE FILE
				EditUserBookingsXML deleteBooking = new EditUserBookingsXML("userBookings.xml", "userBookings");
				deleteBooking.editsUserBooking();

				// THEN OPENS AND PARSES THE 'filmBookings.xml' FILE TO EDIT THE
				// BOOKING INFORMATION FOR THAT FILM
				File xmlFile = new File("filmBookings.xml");

				if (xmlFile.exists()) {
					try {
						ReadXMLFile read = new ReadXMLFile("filmBookings.xml");
						root3 = read.readsXML();
					} catch (Exception e) {
						CinemaMain.LOGGER.warning("Couldn't parse film.XML");
					}

					list3 = root3.getChildren("filmBooking");

					for (int i = 0; i < list3.size(); i++) {

						// GETS EACH NODE IN 'filmBookings' AND SELECTS THE NODE THAT MATCHES THE UNIQUE PAGE TITLE
						// WHICH WAS SET WITH THE INFORMATION OF THE FILM SELECTED FOR DELETION
						Element node = (Element) list3.get(i);
						if (node.getAttributeValue("name").equalsIgnoreCase(WhatsOnCustController.pageTitle)) {

							// GETS THE SEAT BOOKINGS FOR THIS FILM, AND APPENDS EACH SEAT BOOKING TO A STRINGBUFFER
							// EXCEPT FOR THE SEAT IN THE BOOKINGS SELECTED FOR DELETION (SO DELETES THE SEAT FROM THE BOOKINGS)
							StringBuffer sb = new StringBuffer();
							String[] oldSeats = node.getChildText("bookedSeats").split(" ");

							if (oldSeats.length > 0) {
								for (int b = 0; b < oldSeats.length; b++) {
									if (!oldSeats[b].equalsIgnoreCase(seat)) {
										sb.append(oldSeats[b] + " ");
									}

								}
							}

							BookingController.bookedSeats = sb.toString().trim();

							String oldBooked = node.getChildText("bookedNumber");
							String oldUnBooked = node.getChildText("unBookedNumber");

							// GETS THE NUMBER OF BOOKED SEATS FOR THIS FILM ANd DECREMENTS BY ONE
							int booked = Integer.parseInt(oldBooked);
							BookingController.totalBooked = Integer.toString(--booked);

							// GETS THE NUMBER OF UNBOOKED SEATS FOR THIS FILM AND INCREMENTS BY ONE
							int unbooked = Integer.parseInt(oldUnBooked);
							BookingController.totalUnBooked = Integer.toString(++unbooked);

						}
					}

					// UPDATES THE BOOKING INFORMATION FOR THIS FILM IN 'filmBookings.xml'
					// BY CREATING AN INSTANCE OF 'EditBookingsXML' and CALLING THE INSTANCE METHOD TO EDIT THE FILE
					EditBookingsXML editBooking = new EditBookingsXML("filmBookings.xml", "bookings");
					editBooking.editsBookingXML();

					// REMOVES BOOKING FROM CUSTOMER ACCOUNT PAGE
					bookingList.remove(selectedGrid);
					bottomAnchor.getChildren().clear();
					bottomAnchor.getChildren().setAll(bookingList);

				}

			} else {
				// ... user chose CANCEL or closed the dialog
			}

		}
	};

	/////////// NAVIGATION FUNCTIONS////////

	/**
	 * Allows customer to edit their profile information. Loads the Sign Up page
	 * whenedit profile button clicked. Calls <code>goToNextPage</code> function
	 * from <code>cinema.CinemaMain</code>
	 * 
	 * @param event
	 *            the button/menu item click event
	 */
	public void allowsEdit(ActionEvent event) {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("shared_view/SignUpPage.fxml", "Edit Information for User: " + LoginController.userID);

	}

	/**
	 * Loads the customer what's on page when 'what's on' menu item selected.
	 * Calls <code>goToNextPage</code> function from
	 * <code>cinema.CinemaMain</code>
	 * 
	 * @param event
	 *            the button/menu item click event
	 */
	@FXML
	private void goToWhatsOn(ActionEvent event) {
		// TAKES YOU TO 'WhatsOn' PAGE WHEN 'ALL FILM' BUTTON OR 'WHATS ON' MENU
		// ITEM PRESSED
		if (LoginController.loggedInUser.equalsIgnoreCase("customer")) {
			CinemaMain main = new CinemaMain();
			main.goToNextPage("customer_view/WhatsOnCustomer.fxml", "What's On");
		} else if (LoginController.loggedInUser.equalsIgnoreCase("employee")) {
			CinemaMain main = new CinemaMain();
			main.goToNextPage("employee_view/WhatsOnEmployee.fxml", "What's On");
		}
	}

	/**
	 * Takes use back to login page when log out menu item selected. Calls
	 * <code>goToLoginPage</code> function from <code>cinema.CinemaMain</code>
	 * 
	 * @param event
	 *            the menu item click event
	 */
	@FXML
	private void logsOut(ActionEvent event) {
		// TAKES YOU TO 'WhatsOn' PAGE WHEN 'ALL FILM' BUTTON OR 'WHATS ON' MENU
		// ITEM PRESSED
		CinemaMain main = new CinemaMain();
		main.goToLoginPage("shared_view/LoginScreen.fxml", "Cinema Login");
	}

	/**
	 * Loads the customer home page when home menu item selected. Calls
	 * <code>goToNextPage</code> function from <code>cinema.CinemaMain</code>
	 * 
	 * @param event
	 *            the menu item click event
	 */
	@FXML
	private void goToHome() {
		if (LoginController.loggedInUser.equalsIgnoreCase("customer")) {
			CinemaMain main = new CinemaMain();
			main.goToNextPage("customer_view/CustomerHome.fxml", "Customer Home");
		} else if (LoginController.loggedInUser.equalsIgnoreCase("employee")) {
			CinemaMain main = new CinemaMain();
			main.goToNextPage("employee_view/EmployeeHome.fxml", "Employee Home");
		}
	}

	/**
	 * Loads the employee 'all bookings' page when menu item selected. Calls
	 * <code>goToNextPage</code> function from <code>cinema.CinemaMain</code>
	 * 
	 * @param event
	 *            the menu item click event
	 */
	@FXML
	private void goToAllBookings(ActionEvent event) {

		CinemaMain main = new CinemaMain();
		main.goToNextPage("employee_view/AllBookings.fxml", "All Bookings");

	}

}
