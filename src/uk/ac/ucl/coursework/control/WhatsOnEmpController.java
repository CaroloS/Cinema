package uk.ac.ucl.coursework.control;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import uk.ac.ucl.coursework.CinemaMain;
import uk.ac.ucl.coursework.xml.ReadXMLFile;

public class WhatsOnEmpController implements Initializable {

	@FXML
	public VBox centreAnchor;

	// DECALRES FILM VARIABLES TO STORE VALUES FROM XML PARSING
	Element root;
	Attribute filmID;
	String filmTitle, filmGenre, filmDescription, filmStart, filmEnd, filmDate;
	

	
	// USES THE INITIALIZE METHOD TO PARSE XML AND LAYOUT THE FILMS CURRENTLY IN
	// THE XML FILE WHEN PAGE IS LOADED
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//CREATES INSTANCE OF 'ReadXMLFile', PRASES 'film.XML' AND RETURNS THE ROOT NODE 
		try {
			ReadXMLFile read = new ReadXMLFile("film.xml");
			root = read.readsXML();
		} catch (Exception e) {
			CinemaMain.LOGGER.warning("Couldn't parse film.XML");
		}

		
		// ITERATES THROUGH THE 'FILM' LIST, GETS ELEMENT/ATTRIBUTES AND
		// PASSES IT TO FILM VARIABLES
		
		List list = root.getChildren("film");
		for (int i = 0; i < list.size(); i++) {

			Element node = (Element) list.get(i);

			filmID = node.getAttribute("id");
			filmTitle = node.getChildText("title");
			filmGenre = node.getChildText("genre");
			filmDescription = node.getChildText("description");
			filmStart = node.getChildText("start");
			filmEnd = node.getChildText("end");
			filmDate = node.getChildText("date");


			// CREATES A NEW GRIDPANE AND LABELS WITH THE FILM INFORMATION
			GridPane gridPane = new GridPane();

			Image dummyPic = new Image("images/greencamera.png");
			ImageView viewPic = new ImageView(dummyPic);
			Label title = new Label(filmTitle);
			Label genre = new Label(filmGenre);
			Label description = new Label(filmDescription);
			String dateTimeInfo = filmDate + ", " + filmStart + " - " + filmEnd;
			Label dateTime = new Label(dateTimeInfo);
			Label moreInfo = new Label("more info..");

			// BOOKING BUTTON WITH A 'buttonHandler' EVENTHANDLER
			Button book = new Button("Book");
			book.setOnAction(buttonHandler);

			// COMBOBOX TO POPULATE WITH LIST OF FILM DATES
			ComboBox dateList = new ComboBox();

			// ADDS ALL NODES (LABELS/BUTTON) TO THE GRIDPANE
			gridPane.add(viewPic, 0, 1, 1, 4);
			gridPane.add(title, 1, 1, 2, 1);
			gridPane.add(genre, 1, 2, 2, 1);
			gridPane.add(dateTime, 1, 3, 2, 1);
			gridPane.add(description, 1, 4, 3, 1);
			gridPane.add(moreInfo, 1, 5, 1, 1);
			gridPane.add(book, 4, 3, 1, 1);
			gridPane.add(dateList, 4, 1, 1, 1);

			// ADDS THE GRIDPANE TO THE CENTRAL VBOX 'centreAnchor'
			centreAnchor.getChildren().add(gridPane);
		}

	}

	// EVENTHANDLER FOR THE BOOKNG BUTTON - WILL TAKE YOU TO THE BOOKING PAGE
	final static public EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(final ActionEvent event) {

		}
	};

}
