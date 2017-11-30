package cinema.control;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jdom2.Element;

import cinema.CinemaMain;
import cinema.XML.ReadXMLFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class EmpBookingController extends WhatsOnEmpController implements Initializable {

	@FXML
	private AnchorPane seatAnchor;
	@FXML
	Label filmSelection, bookingInfo1, bookingInfo2, bookingInfo3;
	String strPageTitle = WhatsOnEmpController.pageTitle;

	// DECALRES THE ROOT ELEMENT TO BE SET BY PARSING film.XML
	Element root;
	List list;


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		filmSelection.setText(strPageTitle);
		Image emptyChair = new Image("images/emptychair.png", 20, 20, true, true);

		// ON PAGE LOADING ITERATES THROUGH ALL THE BUTTON CHILDREN OF THE
		// CENTRAL ANCHORPANE
		// AND ADDS AN EMPTY CHAIR IMAGE TO THE BUTTON
		for (int i = 4; i < (seatAnchor.getChildren().size() ); i++) {
			
			Button btn = (Button) seatAnchor.getChildren().get(i);
			ImageView chair = new ImageView(emptyChair);
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

		String bookedSeats = null;
		String totalBooked = null;
		String totalUnBooked = null;
		
		for (int i = 0; i < list.size(); i++) {

			Element node = (Element) list.get(i);
			String xmlAttr = node.getAttributeValue("name");

			if (xmlAttr.equalsIgnoreCase(strPageTitle)) {
				bookedSeats = node.getChildText("bookedSeats");
				totalBooked = node.getChildText("booked");
				totalUnBooked = node.getChildText("unbooked");
			}
		}
		
		System.out.println(bookedSeats);
		System.out.println(totalBooked);
		System.out.println(totalUnBooked);
		
		
		if (bookedSeats != null) {
			String[] arrBookedSeats = bookedSeats.split(" ");

			for (int a = 0; a < arrBookedSeats.length; a++) {
				for (int i = 4; i < seatAnchor.getChildren().size(); i++) {
					
					
					Button btn = (Button) seatAnchor.getChildren().get(i);

					if (arrBookedSeats[a].equalsIgnoreCase(btn.getId())) {
						Image orangeChair = new Image("images/orangechair.png", 20, 20, true, true);
						ImageView bookedChair = new ImageView(orangeChair);
						btn.setGraphic(bookedChair);
					}
				}
			}
		}

		if (bookedSeats.length() > 0)
			bookingInfo1.setText("Seats Booked: " + bookedSeats);
		else
			bookingInfo1.setText("Seats Booked: none yet!");
		bookingInfo2.setText("Total seats booked: " + totalBooked);
		bookingInfo3.setText("Total seats unbooked: " + totalUnBooked);
		
	}
	

	public void seatSelected(ActionEvent event) {

	}

	public void goesBack(ActionEvent event) {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("view/WhatsOnEmployee.fxml", "What's On");
	}

}
