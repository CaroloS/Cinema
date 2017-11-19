package uk.ac.ucl.coursework.control;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

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

public class WhatsOnEmpController implements Initializable {

	@FXML
	public VBox centreAnchor;
	
	Attribute filmID;
	String filmTitle, filmGenre, filmDescription, filmStart, filmEnd, filmDate;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("film.xml");

		try {

			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List list = rootNode.getChildren("film");

			for (int i = 0; i < list.size(); i++) {

				Element node = (Element) list.get(i);

				filmID = node.getAttribute("id");
				filmTitle = node.getChildText("title");
				filmGenre = node.getChildText("genre");
				filmDescription = node.getChildText("description");
				filmStart = node.getChildText("start");
				filmEnd = node.getChildText("end");
				filmDate = node.getChildText("date");

				// System.out.println(filmID + " " + filmTitle + " " + genre + "
				// " + description + " " + startTime + " " + endTime + " " +
				// date);
				
				GridPane gridPane = new GridPane();
				
				Image dummyPic = new Image("images/greencamera.png");
				ImageView viewPic = new ImageView(dummyPic);
				

				Label title = new Label(filmTitle);
				Label genre = new Label(filmGenre);
				Label description = new Label(filmDescription);
				String dateTimeInfo = filmDate + ", " + filmStart + " - " + filmEnd;
				Label dateTime = new Label(dateTimeInfo);
				Label moreInfo = new Label("more info..");
				
				Button book = new Button("Book");
				book.setOnAction(buttonHandler);
				
				ComboBox dateList = new ComboBox();
				
				gridPane.add(viewPic, 0, 1, 1, 4);
				gridPane.add(title, 1, 1, 2, 1);
				gridPane.add(genre, 1, 2, 2, 1);
				gridPane.add(dateTime, 1, 3, 2, 1);
				gridPane.add(description, 1, 4, 3, 1);
				gridPane.add(moreInfo, 1, 5, 1, 1);
				gridPane.add(book, 4, 3, 1, 1);
				gridPane.add(dateList, 4, 1, 1, 1);
				
				centreAnchor.getChildren().add(gridPane);
			}
		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}

	}
	
	
	
	final static public EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>(){

        @Override
        public void handle(final ActionEvent event) {
            
        }
    };
	
	

}
