package cinema.control;

import java.net.URL;
import java.util.ResourceBundle;

import cinema.CinemaMain;
import cinema.XML.CreateFilmXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

public class EmployeeHomeController implements Initializable {

	// DECLARES THE FXML TEXTFIELD VARIABLES TO COLLECT THE INPUT FROM
	@FXML
	private TextField filmTitle, filmDate, filmStart, filmEnd, filmGenre, filmDescription;

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

		// GETS THE USER INPUT FROM TEXTFIELDS AND SETS INSTANCE VARIABLES OF
		// 'filmXML' WITH IT
		filmXML.setTitle(filmTitle.getText());
		filmXML.setDescription(filmDescription.getText());
		filmXML.setGenre(filmGenre.getText());
		filmXML.setStart(filmStart.getText());
		filmXML.setEnd(filmEnd.getText());
		filmXML.setDate(filmDate.getText());

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

	}

}
