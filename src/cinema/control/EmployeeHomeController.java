package cinema.control;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import cinema.CinemaMain;
import cinema.XML.CreateFilmXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
	private void selectImage(ActionEvent event) {
		final FileChooser fileChooser = new FileChooser();
		Desktop desktop = Desktop.getDesktop();
		File file = fileChooser.showOpenDialog(CinemaMain.thestage);

	/*	
		try {
			desktop.open(file);
		} catch (IOException ex) {
			System.out.println("can't open file");
			;
		}
	*/
		String path = file.getPath();
		String relPath = null;
		
		try {
			relPath = file.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String fullPath = file.getAbsolutePath(); 
		
		pictureLabel.setText(fullPath);
		
		System.out.println(fullPath);
		System.out.println(relPath);
		System.out.println(path);
		
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
