package cinema.control;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.jdom2.Element;

import Users.CreateUserBookingsXML;
import cinema.CinemaMain;
import cinema.XML.EditBookingsXML;
import cinema.XML.ReadXMLFile;
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

public class EmpBookingController extends WhatsOnEmpController implements Initializable {

	@FXML
	protected AnchorPane seatAnchor;
	@FXML
	protected Label filmSelection, bookingPrompt, confirmLabel, bookingInfo1, bookingInfo2, bookingInfo3, bookingInfo4;
	
	protected String strPageTitle = WhatsOnCustController.pageTitle;
	
	String bookedSeats = null;
	String totalBooked = null;
	String totalUnBooked = null;
	
	String[] confirmInfo = null;
	String confirmTime = null;
	String confirmDate = null; 
	StringBuffer confirmName = new StringBuffer("");
	String seatNumber = null;
	
	public static String newBookedSeats = null;
	public static String newTotalBooked = null;
	public static String newTotalUnBooked = null;
	
	 
	// DECALRES THE ROOT ELEMENT TO BE SET BY PARSING film.XML
	Element root;
	List list;
	public static Element selectedFilmNode = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		System.out.println(LoginController.loggedInUser);
		filmSelection.setText(strPageTitle);
		Image emptyChair = new Image("images/emptychair.png", 20, 20, true, true);

		// ON PAGE LOADING ITERATES THROUGH ALL THE BUTTON CHILDREN OF THE
		// CENTRAL ANCHORPANE
		// AND ADDS AN EMPTY CHAIR IMAGE TO THE BUTTON
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

		
		//GETS THE SEAT BOOKING INFORMATION FROM THE 'filmBookings.xml' FILE
		//WHERE THE FILM ATTRIBUTE EQUALS THE FILM CURRENTLY BEING DISPLAYED ON THE PAGE
		for (int i = 0; i < list.size(); i++) {

			Element node = (Element) list.get(i);
			String xmlAttr = node.getAttributeValue("name");

			if (xmlAttr.equalsIgnoreCase(strPageTitle)) {
				bookedSeats = node.getChildText("bookedSeats");
				totalBooked = node.getChildText("bookedNumber");
				totalUnBooked = node.getChildText("unBookedNumber");
				
				selectedFilmNode = node;
			}
		}


		//SPLITS THE SEATS BOOKED INTO AN ARRAY TO GET A LIST OF SEATS BOOKED
		//ITERATES THROUGH THE SEATS BOOKED AND THE SEATS ON THE PAGE 
		//WHERE THE SEAT BOOKED MATCHES THE SEAT ID - CHANGES THE SEAT IMAGE TO ORANGE CHAIR
		if (bookedSeats != null) {
			String[] arrBookedSeats = bookedSeats.split(" ");

			for (int a = 0; a < arrBookedSeats.length; a++) {
				for (int i = 4; i < seatAnchor.getChildren().size(); i++) {

					Button btn = (Button) seatAnchor.getChildren().get(i);

					if (arrBookedSeats[a].equalsIgnoreCase(btn.getId())) {
						Image orangeChair = new Image("images/orangechair.png", 20, 20, true, true);
						ImageView bookedChair = new ImageView(orangeChair);
						bookedChair.setId("orange");
						btn.setGraphic(bookedChair);
					}
				}
			}
		}

		
		if (LoginController.loggedInUser.equalsIgnoreCase("employee")) {
			//PRINTS THE SEAT BOOKING INFORMATION TO THE LABELS IN THE VBOX BELOW THE CINEMA LAYOUT
			if (bookedSeats != null)
				bookingInfo1.setText("Seats Booked: " + bookedSeats);
			else
				bookingInfo1.setText("Seats Booked: none yet!");
			bookingInfo2.setText("Total seats booked: " + totalBooked);
			bookingInfo3.setText("Total seats unbooked: " + totalUnBooked);
		}
		
		
		if (LoginController.loggedInUser.equalsIgnoreCase("customer")) {
			bookingPrompt.setText("Come see this film! Click on a seat to book ...");
		}
		
		
	}
	

	
	
	public void seatSelected(ActionEvent event) {
		System.out.println(bookedSeats);
		System.out.println(totalBooked);
		System.out.println(totalUnBooked);
		
		if (LoginController.loggedInUser.equalsIgnoreCase("customer")) {
			
			Button btn2 = (Button) event.getSource();
			seatNumber = btn2.getId();
			ImageView selectedChair = (ImageView) btn2.getGraphic();
			String chairType = selectedChair.getId();
			
			if (chairType.equalsIgnoreCase("orange")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("Sorry that seat is taken!");
				alert.setContentText("There's no sitting on other customer's laps at this cinema! So best pick another seat!");
				alert.showAndWait();
			}
			else {
				
				confirmInfo = strPageTitle.split(" ");
				confirmTime = confirmInfo[confirmInfo.length -1]; 
				confirmDate = confirmInfo[confirmInfo.length -2]; 
				
				for (int i = 0; i < confirmInfo.length -2; i++)
					confirmName.append(confirmInfo[i] + " "); 
				
			
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText("Great choice, that seat is free - go ahead and book!");
				alert.setContentText("By cicking OK you will be confirmed for " + confirmName + " on " + 
						confirmDate + " at " + confirmTime + " in seat " + seatNumber + ". Or click cancel and pick another seat. \n\n "
								+ "ARE YOU SURE YOU WANT TO BOOK?");
				//alert.showAndWait();
				
				
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
				    
					CreateUserBookingsXML newBooking = new CreateUserBookingsXML("userBookings.xml", "userBookings");
					newBooking.setFilmName(confirmName.toString());
					newBooking.setFilmDate(confirmDate);
					newBooking.setFilmTime(confirmTime);
					newBooking.setSeatBooked(seatNumber);
					
					newBooking.getsRoot();
					newBooking.CreateUserBooking();
					
					
					int booked = Integer.parseInt(totalBooked);
					newTotalBooked = Integer.toString(++booked);
					int unbooked = Integer.parseInt(totalUnBooked);
					newTotalUnBooked = Integer.toString(--unbooked);
					
					StringBuffer sb = new StringBuffer("");
					sb.append(bookedSeats);
					sb.append(" " + seatNumber);
					newBookedSeats = sb.toString();
					
					EditBookingsXML alter = new EditBookingsXML("filmBookings.xml", "bookings");
					alter.editsBookingXML();
					
					Image orangeChair = new Image("images/orangechair.png", 20, 20, true, true);
					ImageView bookedChair = new ImageView(orangeChair);
					bookedChair.setId("orange");
					btn2.setGraphic(bookedChair);
					
					
					
				} else {
				    // ... user chose CANCEL or closed the dialog
				}
				
			}
			
			
		}
	}

	//TAKES EMPLOYEE BACK TO THE WHATS ON PAGE
	public void goesBack(ActionEvent event) {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("view/WhatsOnEmployee.fxml", "What's On");
	}

}
