package cinema.control;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import org.jdom2.Attribute;
import org.jdom2.Element;

import cinema.CinemaMain;
import cinema.XML.ReadXMLFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class WhatsOnCustController implements Initializable {

	// DECALRES THE FXML ELEMENT TO SET THE NEW FILMS TO
	@FXML
	public VBox centreAnchor;

	// DECALRES THE ROOT ELEMENT TO BE SET BY PARSING film.XML
	Element root;
	List list;
	
	public static String pageTitle;

	// DECALRES FILM VARIABLES TO STORE VALUES FROM XML PARSING
	Attribute filmID;
	String filmTitle, filmGenre, filmDescription, filmStart, filmLength, filmDates, filmRating, filmImage;
	
	ArrayList<String> filmIDs = new ArrayList<String>();
	//ArrayList<Attribute> filmIDs = new ArrayList<Attribute>();

	// TAKES USER BACK TO 'Cinema Login' PAGE WHEN 'LOG OUT' MENU ITEM CLICKED
	@FXML
	private void logsOut(ActionEvent event) {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("view/LoginScreen.fxml", "Cinema Login");
	}

	// TAKES USER BACK TO 'Customer Home' PAGE WHEN 'HOME' MENU ITEM CLICKED
	@FXML
	private void goBackHome(ActionEvent event) {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("view/CustomerHome.fxml", "Customer Home");
	}

	// USES THE INITIALIZE METHOD TO PARSE XML AND LAYOUT THE FILMS CURRENTLY IN
	// THE XML FILE WHEN PAGE IS LOADED
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// CREATES INSTANCE OF 'ReadXMLFile', PARSES 'film.XML' AND RETURNS THE
		// ROOT NODE
		try {
			ReadXMLFile read = new ReadXMLFile("film.xml");
			root = read.readsXML();
		} catch (Exception e) {
			CinemaMain.LOGGER.warning("Couldn't parse film.XML");
		}

		// PARSES XML: ITERATES THROUGH THE 'FILM' LIST, GETS ELEMENT/ATTRIBUTES
		// AND PASSES IT TO FILM VARIABLES
		list = root.getChildren("film");
		
		for (int i = 0; i < list.size(); i++) {

			Element node = (Element) list.get(i);

			filmID = node.getAttribute("id");
			String ID = filmID.toString();
			String IDs[] = ID.split("\"");
			filmIDs.add(IDs[1]);
			
			filmTitle = node.getChildText("title");
			filmGenre = node.getChildText("genre");
			filmDescription = node.getChildText("description");
			filmStart = node.getChildText("start");
			// filmRating = node.getChildText("rating");
			filmImage = node.getChildText("image");

			// CREATES A NEW GRIDPANE AND POPULATES IT WITH THE FILM INFORMATION
			// FROM THE XML PARSING
			GridPane gridPane = new GridPane();
			gridPane.setPrefSize(680, 800);

			Image filmPic = new Image(filmImage, 200, 200, false, false);
			ImageView viewPic = new ImageView(filmPic);

			Label title = new Label(filmTitle);
			Label genre = new Label(filmGenre);
			Label startTime = new Label(filmStart + "  length: 1 hour");
			startTime.setWrapText(true);

			Label description = new Label(filmDescription);
			description.setWrapText(true);

			Label moreInfo = new Label("more info..");
			Label blank = new Label(" ");

			// SPLITS THE FILMDATES FROM XML AND FILLS A COMBOBOX WITH LIST OF
			// DATES
			filmDates = node.getChildText("date");
			String[] splitDates = filmDates.split(" ");

			List<String> dateList2 = new ArrayList<String>();
			for (int a = 0; a < splitDates.length; a++) {
				dateList2.add(splitDates[a]);
			}
			ObservableList<String> obList = FXCollections.observableList(dateList2);

			ComboBox dateList = new ComboBox();
			dateList.getItems().clear();
			dateList.setItems(obList);
			dateList.setPromptText("Pick a Date");

			// BOOKING BUTTON WITH A 'buttonHandler' EVENTHANDLER
			Button book = new Button("Book");
			book.setOnAction(buttonHandler);
			book.setPrefSize(120, 20);

			// ADDS ALL NODES (LABELS/BUTTON/COMBOBOX) TO THE GRIDPANE
			gridPane.add(viewPic, 0, 1, 1, 4);
			gridPane.add(title, 1, 1, 2, 1);
			gridPane.add(genre, 1, 2, 2, 1);
			gridPane.add(startTime, 1, 3, 2, 1);
			gridPane.add(description, 1, 4, 2, 1);

			gridPane.add(moreInfo, 1, 4, 2, 1);
			gridPane.setValignment(moreInfo, VPos.BOTTOM);
			gridPane.setHalignment(moreInfo, HPos.RIGHT);

			gridPane.add(dateList, 4, 3, 1, 1);
			gridPane.add(book, 4, 4, 1, 1);
			gridPane.add(blank, 0, 5, 1, 1);

			// SETS SOME COLUMN WIDTH CONSTRAINTS FOR PROPER LAYOUT
			ColumnConstraints col1 = new ColumnConstraints();
			ColumnConstraints col2 = new ColumnConstraints();
			ColumnConstraints col3 = new ColumnConstraints();
			col1.setPercentWidth(2);
			col2.setPercentWidth(2);
			col3.setPercentWidth(2);
			gridPane.getColumnConstraints().addAll(col1, col2, col3);

			// ADDS THE GRIDPANE TO THE CENTRAL VBOX 'centreAnchor'
			centreAnchor.getChildren().add(gridPane);
		}

	}

	// EVENTHANDLER FOR THE BOOKNG BUTTON - WILL TAKE YOU TO THE BOOKING PAGE
	final static public EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(final ActionEvent event) {
			Button btn = (Button) event.getSource();
			String btnID = btn.getId();
			GridPane grid = (GridPane) btn.getParent();
			List childList = grid.getChildren();
			
			Label a = (Label) childList.get(1);
			Label b = (Label) childList.get(3);
			String[] times = b.getText().split(" ");
			ComboBox<String> c = (ComboBox) childList.get(6);
			
			pageTitle = a.getText() + " " + c.getSelectionModel().getSelectedItem() + " " +  times[0];
			
			CinemaMain main = new CinemaMain();
			main.goToNextPage("view/BookingPage.fxml", pageTitle);
		}
	};

}
