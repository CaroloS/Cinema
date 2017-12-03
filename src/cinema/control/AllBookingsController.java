package cinema.control;

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

public class AllBookingsController implements Initializable {

	@FXML
	private Label futureBookingLabel, pastBookingLabel;
	@FXML
	private AnchorPane futurePieAnchor, pastPieAnchor;

	File xmlFile = new File("filmBookings.xml");
	Element root = null;
	List list = null;

	String seatsBooked = null;
	String booked = null;
	String available = null;

	ArrayList<String> futureBookings = new ArrayList<String>(1000);
	ArrayList<String> pastBookings = new ArrayList<String>(1000);

	ArrayList<String> pastUnbooked = new ArrayList<String>(1000);
	ArrayList<String> pastBooked = new ArrayList<String>(1000);

	ArrayList<String> futureUnbooked = new ArrayList<String>(1000);
	ArrayList<String> futureBooked = new ArrayList<String>(1000);

	Date today = new Date();

	public void exportsFutureBookings() {

		if (futureBookings.size() > 0) {
			writesToFile("FutureBookings.txt", futureBookings.toString());
			futureBookingLabel.setWrapText(true);
			futureBookingLabel.setText("Done! Check your computer for FutureBookings.txt");

		} else {
			futureBookingLabel.setText("No past bookings to export!");
		}

	}

	public void exportsPastBookings() {

		if (pastBookings.size() > 0) {
			writesToFile("PastBookings.txt", pastBookings.toString());
			pastBookingLabel.setWrapText(true);
			pastBookingLabel.setText("Done! Check your computer for PastBookings.txt");

		} else {
			pastBookingLabel.setText("No past bookings to export!");
		}

	}

	public void writesToFile(String filename, String content) {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {

			bw.write(content);
			System.out.println("Done");

		} catch (IOException e) {
			CinemaMain.LOGGER.warning("Couldn't write to file");
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		if (xmlFile.exists()) {
			try {
				ReadXMLFile read = new ReadXMLFile("filmBookings.xml");
				root = read.readsXML();
			} catch (Exception e) {
				CinemaMain.LOGGER.warning("Couldn't parse users.XML");
			}
			list = root.getChildren("filmBooking");
		}

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {

				Element node = (Element) list.get(i);

				String titleDateTime = node.getAttributeValue("name");
				Date filmDate = null;
				String[] arr = titleDateTime.split(" ");
				String strFilmDate = arr[arr.length - 2];

				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
				try {
					filmDate = sdf.parse(strFilmDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				if (today.compareTo(filmDate) < 0) {

					seatsBooked = node.getChildText("bookedSeats");
					booked = node.getChildText("bookedNumber");
					available = node.getChildText("unBookedNumber");

					String filmInfo = "\n\n" + titleDateTime + ": Seats Booked: " + seatsBooked + " Number Booked: "
							+ booked + " Number Available: " + available;

					futureBookings.add(filmInfo);
					futureBooked.add(booked);
					futureUnbooked.add(available);
				} else if (today.compareTo(filmDate) > 0) {

					seatsBooked = node.getChildText("bookedSeats");
					booked = node.getChildText("bookedNumber");
					available = node.getChildText("unBookedNumber");

					String filmInfo = titleDateTime + ": Seats Booked: " + seatsBooked + " Number Booked: " + booked
							+ " Number Available: " + available + ", ";

					pastBookings.add(filmInfo);
					pastBooked.add(booked);
					pastUnbooked.add(available);
				}

			}

		}

		if (futureBookings.size() > 0) {

			int futureBookingCount = 0;
			for (String element : futureBooked) {
				futureBookingCount += Integer.valueOf(element);
			}
			System.out.println(futureBookingCount);

			int futureAvailableCount = 0;
			for (String element : futureUnbooked) {
				futureAvailableCount += Integer.valueOf(element);
			}
			System.out.println(futureAvailableCount);

			ObservableList<PieChart.Data> futurePieData = FXCollections.observableArrayList(
					new PieChart.Data("Total Available", futureAvailableCount),
					new PieChart.Data("Total Booked", futureBookingCount));

			PieChart futurePie = new PieChart(futurePieData);
			futurePie.setTitle("Future Booking Data");
			futurePieAnchor.getChildren().add(futurePie);
			futurePie.prefWidthProperty().bind(futurePieAnchor.widthProperty());
			futurePie.prefHeightProperty().bind(futurePieAnchor.heightProperty());
			
		} else {
			Label label = new Label();
			label.setText("No future booking data to display yet!");
			futurePieAnchor.getChildren().add(label);
			
		}
		
		if (pastBookings.size() > 0) {

			int pastBookingCount = 0;
			for (String element : pastBooked) {
				pastBookingCount += Integer.valueOf(element);
			}
			System.out.println(pastBookingCount);

			int pastAvailableCount = 0;
			for (String element : pastUnbooked) {
				pastAvailableCount += Integer.valueOf(element);
			}
			System.out.println(pastAvailableCount);

			ObservableList<PieChart.Data> pastPieData = FXCollections.observableArrayList(
					new PieChart.Data("Total Available", pastAvailableCount),
					new PieChart.Data("Total Booked", pastBookingCount));

			PieChart pastPie = new PieChart(pastPieData);
			pastPie.setTitle("Past Booking Data");
			pastPieAnchor.getChildren().add(pastPie);
			pastPie.prefWidthProperty().bind(pastPieAnchor.widthProperty());
			pastPie.prefHeightProperty().bind(pastPieAnchor.heightProperty());
			
		} else {
			Label label2 = new Label();
			label2.setText("No past booking data to display!");
			pastPieAnchor.getChildren().add(label2);
		}
		
		

	}

	//////////// NAVIGATION METHODS/////////////

	// TAKES USER BACK TO 'Employee Home' PAGE WHEN 'HOME' MENU ITEM CLICKED
	@FXML
	private void goToHome(ActionEvent event) {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("view/EmployeeHome.fxml", "Employee Home");
	}

	@FXML
	private void goToWhatsOn(ActionEvent event) {

		// TAKES USER TO 'WhatsOnEmployee' PAGE WHEN 'WHATS ON' MENU ITEM
		// CLICKED
		CinemaMain main = new CinemaMain();
		main.goToNextPage("view/WhatsOnEmployee.fxml", "What's On");
	}

	@FXML
	private void logsOut(ActionEvent event) {

		// TAKES USER BACK TO 'Cinema Login' PAGE WHEN 'LOG OUT' MENU ITEM
		// CLICKED
		CinemaMain main = new CinemaMain();
		main.goToNextPage("view/LoginScreen.fxml", "Cinema Login");

	}

}
