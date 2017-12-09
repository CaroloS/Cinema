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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class CustAccountController implements Initializable {

	@FXML
	private AnchorPane topAnchor;
	@FXML
	private Label firstNameLabel, lastNameLabel, emailLabel, numberLabel, usernameLabel;
	@FXML
	private VBox picBox;
	@FXML
	private VBox bottomAnchor;

	List list = null;
	List list2 = null;
	List list3 = null;
	Element root, root2, root3;

	protected List<GridPane> bookingList;

	public static String ID;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		bookingList = new ArrayList<>();

		System.out.println(LoginController.userID);

		File userFile = new File("users.xml");

		if (userFile.exists()) {
			try {
				ReadXMLFile read = new ReadXMLFile("users.xml");
				root = read.readsXML();
			} catch (Exception e) {
				CinemaMain.LOGGER.warning("Couldn't parse user.XML");
			}

			// PARSES XML: ITERATES THROUGH THE 'FILM' LIST, GETS
			// ELEMENT/ATTRIBUTES
			// AND PASSES IT TO FILM VARIABLES
			list = root.getChildren("User");

			for (int i = 0; i < list.size(); i++) {

				Element node = (Element) list.get(i);

				if (node.getAttributeValue("id").equalsIgnoreCase(LoginController.userID)) {

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

		File userBookingFile = new File("userBookings.xml");

		if (userBookingFile.exists()) {
			try {
				ReadXMLFile read = new ReadXMLFile("userBookings.xml");
				root2 = read.readsXML();
			} catch (Exception e) {
				CinemaMain.LOGGER.warning("Couldn't parse user.XML");
			}

			// PARSES XML: ITERATES THROUGH THE 'FILM' LIST, GETS
			// ELEMENT/ATTRIBUTES
			// AND PASSES IT TO FILM VARIABLES
			list2 = root2.getChildren("booking");

			for (int i = 0; i < list2.size(); i++) {

				Element node = (Element) list2.get(i);

				if (node.getAttributeValue("userID").equalsIgnoreCase(LoginController.userID)) {

					GridPane gridBooking = new GridPane();
					gridBooking.setPrefSize(760, 100);
					gridBooking.setId(node.getAttributeValue("bookingID"));

					Label filmName = new Label(node.getChildText("filmName"));
					Label filmDate = new Label(node.getChildText("filmDate"));
					Label filmTime = new Label(node.getChildText("filmTime"));
					Label seatBooked = new Label("Seat Booked: " + node.getChildText("seatBooked"));

					gridBooking.add(filmName, 0, 0);
					gridBooking.add(filmDate, 1, 0);
					gridBooking.add(filmTime, 2, 0);
					gridBooking.add(seatBooked, 0, 1);

					gridBooking.getColumnConstraints().add(new ColumnConstraints(160)); // column
																						// 0
																						// is
																						// 100
																						// wide
					gridBooking.getColumnConstraints().add(new ColumnConstraints(100));
					gridBooking.getColumnConstraints().add(new ColumnConstraints(160));
					gridBooking.getRowConstraints().add(new RowConstraints(40));

					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
					Date today = new Date();

					Date date = null;
					try {
						date = sdf.parse(node.getChildText("filmDate"));
					} catch (ParseException e) {
						e.printStackTrace();
					}

					if (today.compareTo(date) < 0) {
						Button delete = new Button("cancel booking");
						delete.setPrefSize(150, 20);
						delete.setId(node.getAttributeValue("bookingID"));
						delete.setOnAction(buttonHandler);
						gridBooking.add(delete, 1, 1, 2, 1);

					}
					bookingList.add(gridBooking);
				}

			}
		}

		if (bookingList.size() > 0) {
			bottomAnchor.getChildren().setAll(bookingList);
		}

		bottomAnchor.setPadding(new Insets(0, 0, 0, 170));

	}

	final public EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(final ActionEvent event) {

			Button btn = (Button) event.getSource();
			ID = btn.getId();
			GridPane selectedGrid = (GridPane) btn.getParent();

			Label selectedName = (Label) selectedGrid.getChildren().get(0);
			Label selectedDate = (Label) selectedGrid.getChildren().get(1);
			Label selectedTime = (Label) selectedGrid.getChildren().get(2);
			Label selectedSeat = (Label) selectedGrid.getChildren().get(3);
			String[] getSeat = selectedSeat.getText().split(" ");
			String seat = getSeat[2];
			System.out.println(seat);

			String selectedFilm = selectedName.getText() + " " + selectedDate.getText() + " " + selectedTime.getText();
			WhatsOnCustController.pageTitle = selectedFilm;
		//	System.out.println(WhatsOnCustController.pageTitle);

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Cancel Booking");
			alert.setHeaderText("Are you sure you want to cancel this booking?");
			alert.setContentText("By clicking OK you're booking will be cancelled");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {

				// UPDATES USER BOOKING XML
				EditUserBookingsXML deleteBooking = new EditUserBookingsXML("userBookings.xml", "userBookings");
				deleteBooking.editsUserBooking();

				// UPDATES THE FILM BOOKING XML

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

						Element node = (Element) list3.get(i);
						if (node.getAttributeValue("name").equalsIgnoreCase(WhatsOnCustController.pageTitle)) {

							StringBuffer sb = new StringBuffer();
							String[] oldSeats = node.getChildText("bookedSeats").split(" ");
							
							if (oldSeats.length > 0) {
								for (int b = 0; b < oldSeats.length; b ++){
									if (!oldSeats[b].equalsIgnoreCase(seat)) {
										sb.append(oldSeats[b] + " ");
									}
										
								}
							}
							
							BookingController.bookedSeats = sb.toString().trim();

							String oldBooked = node.getChildText("bookedNumber");
							String oldUnBooked = node.getChildText("unBookedNumber");

							int booked = Integer.parseInt(oldBooked);
							BookingController.totalBooked = Integer.toString(--booked);

							int unbooked = Integer.parseInt(oldUnBooked);
							BookingController.totalUnBooked = Integer.toString(++unbooked);

						}
					}
					
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

	public void allowsEdit() {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("shared_view/SignUpPage.fxml", "Edit Information for User: " + LoginController.userID);

	}

	/////////// NAVIGATION FUNCTIONS////////
	@FXML
	private void goToWhatsOn(ActionEvent event) {
		// TAKES YOU TO 'WhatsOn' PAGE WHEN 'ALL FILM' BUTTON OR 'WHATS ON' MENU
		// ITEM PRESSED
		CinemaMain main = new CinemaMain();
		main.goToNextPage("customer_view/WhatsOnCustomer.fxml", "What's On");
	}

	@FXML
	private void logsOut(ActionEvent event) {
		// TAKES YOU TO 'WhatsOn' PAGE WHEN 'ALL FILM' BUTTON OR 'WHATS ON' MENU
		// ITEM PRESSED
		CinemaMain main = new CinemaMain();
		main.goToLoginPage("shared_view/LoginScreen.fxml", "Cinema Login");
	}

	@FXML
	private void goToHome() {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("customer_view/CustomerHome.fxml", "Customer Home");
	}

}
