package cinema.control;

import java.io.File;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import cinema.CinemaMain;
import cinema.XML.CreateFilmXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
	private Label pictureLabel, date1, date2, date3;
	@FXML
	private DatePicker datePicker;
	@FXML
	private ComboBox filmRating;
	
	String fileName;
	
	//FUNCTION FOR ADDING DATES FROM DATEPICKER TO A LABEL TO WRITE TO XML
	@FXML
	private void addsDate(ActionEvent event) {
		
		String date = datePicker.getValue().format(DateTimeFormatter.ofPattern("dd-MM-YY"));
		date1.setWrapText(true);
		date1.setText(date1.getText() + " " + date);
	}
	
	//FUNCTION FOR OPENING FILECHOOSER TO PICK AN IMAGE, WRITES RELATIVE PATH TO A LABEL TO WRITE TO XML
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
		
		// CREATES AN INSTANCE OF 'CreateXML'
		CreateFilmXML filmXML = new CreateFilmXML("film.xml", "films");

		// GETS THE USER INPUT AND SETS INSTANCE VARIABLES OF 'filmXML' WITH IT
		filmXML.setTitle(filmTitle.getText());
		filmXML.setDescription(filmDescription.getText());
		filmXML.setGenre(filmGenre.getText());
		filmXML.setStart(filmStart.getText());
		filmXML.setDate(date1.getText());
		filmXML.setImage(pictureLabel.getText());
		
		String rating = (String) filmRating.getSelectionModel().getSelectedItem();
		filmXML.setRating(rating);

		// CALLS THE 'getsRoot' AND 'createsFilm' METHODS TO WRITE THE NEW FILM
		// INFORMATION TO 'film.XML' FILE
		filmXML.getsRoot();
		filmXML.createsFilm();
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//POPULATES THE RATINGS COMBOBOX WITH VALUES ON PAGE INITIALISATION
	    filmRating.getItems().addAll("U", "PG", "12", "15", "18");

	}

}
