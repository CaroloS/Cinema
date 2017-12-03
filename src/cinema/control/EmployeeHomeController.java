package cinema.control;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.jdom2.Element;

import cinema.CinemaMain;
import cinema.XML.CreateBookingsXML;
import cinema.XML.CreateFilmXML;
import cinema.XML.ReadXMLFile;
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

public class EmployeeHomeController implements Initializable {

	// DECLARES THE FXML VARIABLES TO COLLECT THE INPUT FROM
	@FXML
	private TextField filmTitle, filmStart, filmGenre, filmDescription;
	@FXML
	private Button imageButton;
	@FXML
	private Label pictureLabel, date1;
	@FXML
	private DatePicker datePicker;
	@FXML
	private ComboBox filmRating, timePicker, genrePicker;

	String fileName;
	// ArrayList<String> dateTimes = new ArrayList<String>();
	StringBuffer dateTimes = new StringBuffer();

	Element root = null;
	List list = null;

	Alert alert = new Alert(AlertType.WARNING);

	// FUNCTION FOR ADDING DATES FROM DATEPICKER TO A LABEL TO WRITE TO XML
	@FXML
	private void addsDate(ActionEvent event) {

		String time = (String) timePicker.getSelectionModel().getSelectedItem();

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
		String date = datePicker.getValue().format(DateTimeFormatter.ofPattern("dd-MM-YY"));
		Date selectedDate = null;
		Date today = new Date();
		try {
			selectedDate = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String dateTime = date + " " + time;

		int x = 0;
		if (today.compareTo(selectedDate) >= 0)
			x = 1;

		StringBuffer sb = new StringBuffer("");

		int a = 0;
		
		if (list != null) {

			for (int i = 0; i < list.size(); i++) {
				Element node = (Element) list.get(i);
				String[] dT = node.getChildText("dateTimes").split(",");

				for (String element : dT) {
					if (element.equalsIgnoreCase(dateTime))
						x = 2;
					sb.append(element + "\n");
				}
			}
		}

		switch (x) {
		case (1):
			alert.setTitle("Warning");
			alert.setHeaderText("Date is in the past!");
			alert.setContentText("Please select a date in the future");
			alert.showAndWait();
			break;
		case (2):
			alert.setTitle("Warning");
			alert.setHeaderText("Another film is being shown at the same time!");
			alert.setContentText("Please select another date or time. \n\nThe dates/times with films "
					+ "already booked at this cinema are: \n\n" + sb.toString());
			alert.showAndWait();
			break;
		case (0):
			date1.setWrapText(true);
			date1.setText(date1.getText() + " " + dateTime + ",");
			dateTimes.append(dateTime + ",");

		}
	}

	// FUNCTION FOR OPENING FILECHOOSER TO PICK AN IMAGE, WRITES RELATIVE PATH
	// TO A LABEL TO WRITE TO XML
	@FXML
	private void selectImage(ActionEvent event) {

		final FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(CinemaMain.thestage);

		fileName = file.getName();
		pictureLabel.setText("images/" + fileName);
	}

	/**
	 * Adds a film to the 'film.XML' file Uses the employee input to text-fields
	 * to set the film variables. Calls 'createFilm' method from
	 * 'CreateEditFilmXML' class
	 * 
	 * @param event
	 *            : event created from button click
	 */
	@FXML
	private void addFilm(ActionEvent event) {

		System.out.println(filmTitle.getText().length());
		System.out.println(genrePicker.getSelectionModel().getSelectedItem());
		System.out.println(date1.getText().length());
		System.out.println(filmDescription.getText().length());
		System.out.println(filmRating.getSelectionModel().getSelectedItem());
		System.out.println(pictureLabel.getText().length());

		int b = 0;

		if (list != null) {

			for (int i = 0; i < list.size(); i++) {
				Element node = (Element) list.get(i);
				if (node.getChildText("title").equalsIgnoreCase(filmTitle.getText())) {
					b = 1;
				}
			}

			if (filmTitle.getText().length() == 0 || genrePicker.getSelectionModel().getSelectedItem() == null
					|| date1.getText().length() == 0 || filmDescription.getText().length() == 0
					|| pictureLabel.getText().length() == 0
					|| filmRating.getSelectionModel().getSelectedItem() == null) {
				b = 2;
			}

		}

		switch (b) {
		case (1):
			alert.setTitle("Warning");
			alert.setHeaderText("We're already showing that film!");
			alert.setContentText("We're very exclusive and don't repeat shows here! \n Add a different movie!");
			alert.showAndWait();
			break;
		case (2):
			alert.setTitle("Warning");
			alert.setHeaderText("Some fields are unfilled!");
			alert.setContentText("Please fill in all the film information fields");
			alert.showAndWait();
			break;
		default:
			// CREATES AN INSTANCE OF 'CreateXML'
			CreateFilmXML filmXML = new CreateFilmXML("film.xml", "films");

			// GETS THE USER INPUT AND SETS INSTANCE VARIABLES OF 'filmXML' WITH
			// IT
			filmXML.setTitle(filmTitle.getText());
			filmXML.setDescription(filmDescription.getText());
			filmXML.setGenre(genrePicker.getSelectionModel().getSelectedItem().toString());
			filmXML.setDateTimes(dateTimes.toString());
			filmXML.setImage(pictureLabel.getText());

			String rating = (String) filmRating.getSelectionModel().getSelectedItem();
			filmXML.setRating(rating);

			// CALLS THE 'getsRoot' AND 'createsFilm' METHODS TO WRITE THE NEW
			// FILM
			// INFORMATION TO 'film.XML' FILE
			filmXML.getsRoot();
			filmXML.createsFilm();

			CreateBookingsXML bookingsXML = new CreateBookingsXML("filmBookings.xml", "bookings");

			String[] arrDateTimes = dateTimes.toString().split(",");
			for (int i = 0; i < arrDateTimes.length; i++) {
				String attribute = filmTitle.getText() + " " + arrDateTimes[i];
				bookingsXML.setBookingAttribute(attribute);
				bookingsXML.setBookedSeats("");
				bookingsXML.setBookedNumber("0");
				bookingsXML.setUnBookedNumber("36");

				bookingsXML.getsRoot();
				bookingsXML.createsBookings();
			}
		}

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
	
	@FXML
	private void goToAllBookings(ActionEvent event) {

		// TAKES USER TO ALL BOOKINGS PAGE FROM MENU ITEM
		CinemaMain main = new CinemaMain();
		main.goToNextPage("view/AllBookings.fxml", "All Bookings");

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// POPULATES THE RATINGS COMBOBOX WITH VALUES ON PAGE INITIALISATION
		filmRating.getItems().addAll("U", "PG", "12", "15", "18");

		timePicker.getItems().addAll("1200", "1300", "1400", "1500", "1600", "1700", "1800", "1900", "2000");

		genrePicker.getItems().addAll("Horror", "Comedy", "Children's", "Action", "Love");

		File xmlFile = new File("film.xml");

		if (xmlFile.exists()) {
			try {
				ReadXMLFile read = new ReadXMLFile("film.xml");
				root = read.readsXML();
			} catch (Exception e) {
				CinemaMain.LOGGER.warning("Couldn't parse film.XML");
			}
			list = root.getChildren("film");
		}

	}

	
}