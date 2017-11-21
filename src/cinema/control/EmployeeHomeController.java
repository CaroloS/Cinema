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

	public Scene scene5;

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

	/**
	 * Takes the employee to the 'What's On' page. Sets a new scene (5) to
	 * 'thestage'.
	 * 
	 * @param event
	 *            : event created by clicking menu-bar item
	 */
	@FXML
	private void goToWhatsOn(ActionEvent event) {

		// TAKES USER TO 'WhatsOnEmployee' PAGE WHEN 'WHATS ON' MENU ITEM
		// CLICKED
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/WhatsOnEmployee.fxml"));
			scene5 = new Scene(root, 690, 850);
			CinemaMain.thestage.setTitle("What's On");
			CinemaMain.thestage.setScene(scene5);
			CinemaMain.thestage.show();
		} catch (Exception e) {
			CinemaMain.LOGGER.warning("Couldn't load What's On");
			e.printStackTrace();
		}

	}

	/**
	 * Takes the employee back to the 'Cinema Login' page.
	 * 
	 * @param event
	 *            : event created by clicking menu-bar item
	 */
	@FXML
	private void logsOut(ActionEvent event) {

		// TAKES USER BACK TO 'Cinema Login' PAGE WHEN 'LOG OUT' MENU ITEM
		// CLICKED
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/LoginScreen.fxml"));
			scene5 = new Scene(root, 480, 300);
			CinemaMain.thestage.setTitle("Cinema Login");
			CinemaMain.thestage.setScene(scene5);
			CinemaMain.thestage.show();
		} catch (Exception e) {
			CinemaMain.LOGGER.warning("Problem logging out");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
