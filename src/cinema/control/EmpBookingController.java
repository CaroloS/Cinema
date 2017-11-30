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
	private AnchorPane cinemaLayout;
	@FXML
	Label filmSelection;
	
	// DECALRES THE ROOT ELEMENT TO BE SET BY PARSING film.XML
	Element root;
	List list;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		filmSelection.setText(WhatsOnEmpController.pageTitle);
		
		//ON PAGE LOADING ITERATES THROUGH ALL THE BUTTON CHILDREN OF THE CENTRAL ANCHORPANE
		//AND ADDS AN EMPTY CHAIR IMAGE TO THE BUTTON
		Image emptyChair = new Image("images/emptychair.png", 20, 20, true, true);
		
		for (int i = 5; i< cinemaLayout.getChildren().size(); i++) {
			Button btn = (Button) cinemaLayout.getChildren().get(i);
			ImageView chair = new ImageView(emptyChair);
			btn.setStyle("-fx-background-color: rgba(0,0,0,0.05);");
			btn.setGraphic(chair);
		}
		
		//PARSING FILM BOOKING XML FILE
		try {
			ReadXMLFile read = new ReadXMLFile("filmBookings.xml");
			root = read.readsXML();
		} catch (Exception e) {
			CinemaMain.LOGGER.warning("Couldn't parse film.XML");
		}
		list = root.getChildren("filmBooking");
		
		String bookedSeats  = null;
		for (int i = 0; i < list.size(); i++) {

			Element node = (Element) list.get(i);
			String xmlAttr = node.getAttributeValue("name");
			String strTitle = WhatsOnEmpController.pageTitle;

			if (xmlAttr.equalsIgnoreCase(strTitle)) {
				bookedSeats = node.getChildText("bookedSeats");
			}
		}
		System.out.println(bookedSeats);
		
		if (bookedSeats != null) {
			
			
		}
		
		
	}
	
	public void seatSelected(ActionEvent event){
		
	}
	
	public void goesBack(ActionEvent event) {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("view/WhatsOnEmployee.fxml", "What's On");
	}

}
