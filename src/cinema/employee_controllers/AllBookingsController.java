package cinema.employee_controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.jdom2.Element;

import cinema.CinemaMain;
import cinema.XML.ReadXMLFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * Controller class for the Employee 'All Bookings' page. On initialization, parses 'filmBookings.xml'
 * and extracts information about seat bookings in the future and the past. Lays out the information in 2 pie charts.
 * Defines functions for buttons that can export the booking information to text file as comma separated list. 
 * @author carolinesmith
 *
 */
public class AllBookingsController implements Initializable {

	//DECLARES ALL THE FXML ELEMENTS USED BY THIS CONTROLLER//
	@FXML
	private Label futureBookingLabel, pastBookingLabel;
	@FXML
	private Pane futurePieAnchor, pastPieAnchor;
	
	File xmlFile = new File("filmBookings.xml");
	
	Element root = null; //AN XML ROOT ELEMENT TO SET WHEN PARSING XML DOCUMENTS
	List list = null;    //A LIST TO PASS THE XML NODES TO FROM XML PARSING
 
	//STRING FIELDS TO HOLD THE BOOKING INFORMATION FROM 'filmBookings.xml'
	String seatsBooked = null;
	String booked = null;
	String available = null;

	//ARRAYLISTS TO HOLD BOOKGING INFORMATION FOR FUTURE AND PAST FILMS
	ArrayList<String> futureBookings = new ArrayList<String>(1000);
	ArrayList<String> pastBookings = new ArrayList<String>(1000);

	//ARRAYLIST FOR PAST SEAT BOOKING NUMBERS
	ArrayList<String> pastUnbooked = new ArrayList<String>(1000);
	ArrayList<String> pastBooked = new ArrayList<String>(1000);

	//ARRAYLIST FOR FUTURE SEAT BOOKING NUMBERS
	ArrayList<String> futureUnbooked = new ArrayList<String>(1000);
	ArrayList<String> futureBooked = new ArrayList<String>(1000);

	Date today = new Date();

	/**
	 * Called to initialize a controller after its root element has been completely processed. Parses 'filmBookings.xml'
	 * to extract booking information about film - keeps separate record of past and future bookings. Lays out the 
	 * information about past and future bookings vs available seats in pie charts. Passes past and future  booking information 
	 * to ArrayLists for export to file when employee requests. 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//IF 'filmBookings.xml' EXSISTS PARSES IT BY CALLING 'readsXML' FROM 'cinema.XML.ReadXMLFile'
		if (xmlFile.exists()) {
			try {
				ReadXMLFile read = new ReadXMLFile("filmBookings.xml");
				root = read.readsXML();			//RETURNS THE ROOT NODE
			} catch (Exception e) {
				CinemaMain.LOGGER.warning("Couldn't parse users.XML");
			}
			list = root.getChildren("filmBooking");
		}

		//ITERATES THROUGH THE LIST OF XML NODES TO EXRTACT FILM BOOKING INFORMATION
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {

				Element node = (Element) list.get(i);

				String titleDateTime = node.getAttributeValue("name");   //GETS THE ATTRIBUTE WHICH IS THE FILM NAME + DATE + TIME
				Date filmDate = null;
				String[] arr = titleDateTime.split(" ");
				String strFilmDate = arr[arr.length - 2];    			 //GETS JUST THE FILM DATE FROM THE ATTRIBUTE

				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy"); //CREATES INSTANCE OF 'SimpleDateFormat' TO PARSE STRING TO DATES 
				try {
					filmDate = sdf.parse(strFilmDate);					 //PARSES THE STRING FILM DATE TO A DATE
				} catch (ParseException e) {
					CinemaMain.LOGGER.warning("Couldn't parse strFilmDate to Date");
					e.printStackTrace();
				}

				if (today.compareTo(filmDate) < 0) {					//CHECKS IF DATE IS IN THE FUTURE

					seatsBooked = node.getChildText("bookedSeats");		//GETS BOOKING INFORMATION FOR FUTURE FILMS
					booked = node.getChildText("bookedNumber");
					available = node.getChildText("unBookedNumber");

					String filmInfo = "\n\n" + titleDateTime + ": Seats Booked: " + seatsBooked + " Number Booked: "
							+ booked + " Number Available: " + available;

					//ADDS THE FILM INFORMATION TO THE FUTURE ARRAYLISTS
					futureBookings.add(filmInfo);						
					futureBooked.add(booked);
					futureUnbooked.add(available);
				} else if (today.compareTo(filmDate) > 0) {				//DOES THE SAME FOR PAST FILMS

					seatsBooked = node.getChildText("bookedSeats");		//GETS PAST FILM INFORMATION
					booked = node.getChildText("bookedNumber");
					available = node.getChildText("unBookedNumber");

					String filmInfo = "\n\n" + titleDateTime + ": Seats Booked: " + seatsBooked + " Number Booked: " + booked
							+ " Number Available: " + available;

					//ADDS INFORMATION TO THE PAST ARRAYLISTS
					pastBookings.add(filmInfo);
					pastBooked.add(booked);
					pastUnbooked.add(available);
				}
			}
		}
		
		//CHECKS IF THERE ARE AN Y FUTURE FILMS
		if (futureBookings.size() > 0) {

			int futureBookingCount = 0;
			for (String element : futureBooked) {
				futureBookingCount += Integer.valueOf(element);     	//COUNTS THE NUMBER OF FUTURE BOOKINGS
			}

			int futureAvailableCount = 0;
			for (String element : futureUnbooked) {
				futureAvailableCount += Integer.valueOf(element);		//COUNTS THE NUMBER OF FUTURE AVAILABLE SEATS
			}

			//CREATES A PICHART TO DISPLAY FUTURE BOOKED VS AVAILABLE SEAT NUMBERS
			ObservableList<PieChart.Data> futurePieData = FXCollections.observableArrayList(  
					new PieChart.Data("Total Available", futureAvailableCount),
					new PieChart.Data("Total Booked", futureBookingCount));

			PieChart futurePie = new PieChart(futurePieData);
			futurePie.setTitle("Future Booking Data");
			futurePieAnchor.getChildren().add(futurePie);
			futurePie.prefWidthProperty().bind(futurePieAnchor.widthProperty());
			futurePie.prefHeightProperty().bind(futurePieAnchor.heightProperty());
			futurePie.setLabelsVisible(false);
			
		} else {
			Label label = new Label();
			label.setText("No future booking data to display yet!");
			futurePieAnchor.getChildren().add(label);
			
		}
		
		//CHECKS IF THERE ARE PAST FILM BOOKINGS
		if (pastBookings.size() > 0) {

			int pastBookingCount = 0;
			for (String element : pastBooked) {
				pastBookingCount += Integer.valueOf(element);    		 //COUNTS THE NUMBER OF PAST BOOKINGS
			}

			int pastAvailableCount = 0;
			for (String element : pastUnbooked) {
				pastAvailableCount += Integer.valueOf(element);			//COUNTS THE NUMBER OF PAST AVAILABLE SEATS
			}

			//CREATES A PICHART TO DISPLAY PAST BOOKED VS AVAILABLE SEAT NUMBERS
			ObservableList<PieChart.Data> pastPieData = FXCollections.observableArrayList(
					new PieChart.Data("Total Available", pastAvailableCount),
					new PieChart.Data("Total Booked", pastBookingCount));

			PieChart pastPie = new PieChart(pastPieData);
			pastPie.setTitle("Past Booking Data");
			pastPieAnchor.getChildren().add(pastPie);
			pastPie.prefWidthProperty().bind(pastPieAnchor.widthProperty());
			pastPie.prefHeightProperty().bind(pastPieAnchor.heightProperty());
			pastPie.setLabelsVisible(false);
		} else {
			Label label2 = new Label();
			label2.setText("No past booking data to display!");
			pastPieAnchor.getChildren().add(label2);
		}
		
		

	}
	
	/**
	 * Checks if there are future film bookings. Calls <code>writesToFile</code> with the information from the
	 * future bookings ArrayList.
	 */
	public void exportsFutureBookings() {

		if (futureBookings.size() > 0) {
			writesToFile("FutureBookings.txt", futureBookings.toString());        
			futureBookingLabel.setWrapText(true);
			futureBookingLabel.setText("Done! Check your computer for FutureBookings.txt");

		} else {
			futureBookingLabel.setText("No past bookings to export!");
		}

	}

	/**
	 * Checks if there are past film bookings. Calls <code>writesToFile</code> with the information from the
	 * past bookings ArrayList.
	 */
	public void exportsPastBookings() {

		if (pastBookings.size() > 0) {
			writesToFile("PastBookings.txt", pastBookings.toString());       
			pastBookingLabel.setWrapText(true);
			pastBookingLabel.setText("Done! Check your computer for PastBookings.txt");

		} else {
			pastBookingLabel.setText("No past bookings to export!");
		}

	}

	/**
	 * Creates an instance of BufferedWriter with the file name passed as parameter and writes the content passed
	 * to this file
	 * @param filename the file to write to.
	 * @param content the content to write.
	 */
	public void writesToFile(String filename, String content) {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {

			bw.write(content);
			System.out.println("Done");

		} catch (IOException e) {
			CinemaMain.LOGGER.warning("Couldn't write to file");
			e.printStackTrace();
		}

	}

//////////// NAVIGATION METHODS/////////////

	/**
	 * Loads the employee home page when menu item selected.
	 * Calls <code>goToNextPage</code> function from <code>cinema.CinemaMain</code>
	 * @param event the menu item click event
	 */
	@FXML
	private void goToHome(ActionEvent event) {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("employee_view/EmployeeHome.fxml", "Employee Home");
	}

	/**
	 * Loads the employee what's on page when menu item selected.
	 * Calls <code>goToNextPage</code> function from <code>cinema.CinemaMain</code>
	 * @param event the menu item click event
	 */
	@FXML
	private void goToWhatsOn(ActionEvent event) {

		// TAKES USER TO 'WhatsOnEmployee' PAGE WHEN 'WHATS ON' MENU ITEM
		// CLICKED
		CinemaMain main = new CinemaMain();
		main.goToNextPage("employee_view/WhatsOnEmployee.fxml", "What's On");
	}

	/**
	 * Takes user back to login page when log out menu item selected.
	 * Calls <code>goToLoginPage</code> function from <code>cinema.CinemaMain</code>
	 * @param event the menu item click event
	 */
	@FXML
	private void logsOut(ActionEvent event) {

		// TAKES USER BACK TO 'Cinema Login' PAGE WHEN 'LOG OUT' MENU ITEM
		// CLICKED
		CinemaMain main = new CinemaMain();
		main.goToLoginPage("shared_view/LoginScreen.fxml", "Cinema Login");

	}
	
	/**
	 * Loads the employee account page when account menu item selected.
	 * Calls <code>goToNextPage</code> function from <code>cinema.CinemaMain</code>
	 * @param event the menu item click event
	 */
	@FXML
	private void goToMyAccount() {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("customer_view/CustomerAccount.fxml", "My Account");
	}

}
