package cinema.control;

import java.io.File;
import java.net.URL;
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

	// DECLARES THE FXML TEXTFIELD VARIABLES TO COLLECT THE INPUT FROM
	@FXML
	private TextField filmTitle, filmDate, filmStart, filmEnd, filmGenre, filmDescription;
	@FXML
	private Button imageButton;
	@FXML
	private Label pictureLabel;
	@FXML
	private DatePicker datePicker;
	@FXML
	private ComboBox filmRating;
	
	String fileName;
	String imageRelPath;
	
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
		
		//NEED TO ADD CODE TO CHECK IF ALL THE BOXES ARE FILLED IN ... 

		// CREATES AN INSTANCE OF 'CreateXML'
		CreateFilmXML filmXML = new CreateFilmXML("film.xml", "films");

		// GETS THE USER INPUT FROM TEXTFIELDS AND SETS INSTANCE VARIABLES OF
		// 'filmXML' WITH IT
		filmXML.setTitle(filmTitle.getText());
		filmXML.setDescription(filmDescription.getText());
		filmXML.setGenre(filmGenre.getText());
		filmXML.setStart(filmStart.getText());
		filmXML.setEnd(filmEnd.getText());
		filmXML.setDate(filmDate.getText());
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
	    filmRating.getItems().addAll("U", "PG", "12", "15", "18");

	}

}
