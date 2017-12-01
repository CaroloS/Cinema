package cinema.control;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import sun.awt.CGraphicsDevice;

public class WhatsOnCustController implements Initializable {

	// DECALRES THE FXML ELEMENT TO SET THE NEW FILMS TO
	@FXML
	public VBox centreAnchor;
	@FXML
	public DatePicker filterDates;
	public ComboBox filterGenre;
	public Button allFilms;

	// DECALRES THE ROOT ELEMENT TO BE SET BY PARSING film.XML
	Element root;
	List list;

	public static String pageTitle;

	// DECALRES FILM VARIABLES TO STORE VALUES FROM XML PARSING
	// Attribute filmID;
	String filmID, filmTitle, filmGenre, filmDescription, filmStart, filmLength, filmDates, filmRating, filmImage;

	ArrayList<String> filmIDs = new ArrayList<String>();

	// USES THE INITIALIZE METHOD TO PARSE XML AND LAYOUT THE FILMS CURRENTLY IN
	// THE XML FILE WHEN PAGE IS LOADED
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// SETS UP THE GENRE COMBOBOX
		List<String> Genres = new ArrayList<String>();
		Genres.addAll(Arrays.asList("Horror", "Comedy", "Children's", "Action", "Love"));
		ObservableList<String> obList1 = FXCollections.observableList(Genres);
		filterGenre.getItems().clear();
		filterGenre.setItems(obList1);

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

			// SPLITS THE FILMDATES FROM XML AND CREATES AN ARRAYLIST TO PASS TO
			// COMBOBOX
			filmDates = node.getChildText("date");
			List<String> allDates = new ArrayList<String>();

			for (String element : filmDates.split(" ")) {
				allDates.add(element);
			}

			// COMPARES THE DATES OF THIS FILM TO THE CURRENT DATE, REMOVING
			// DATES FROM ALLDATES IF PAST
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
			Date today = new Date();

			Iterator<String> it = allDates.iterator();
			while (it.hasNext()) {
				String element = it.next();

				Date date = null;
				try {
					date = sdf.parse(element);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (today.compareTo(date) > 0)
					it.remove();
			}

			// ONLY PROCEEDS TO PARSE THE XML FOR THIS FILM NODE IF SOME OF THE
			// DATES ARE TODAY OR FUTURE
			// i.e. DOESN'T DISPLAY FILMS WITH WHERE ALL DATES HAVE PASSED
			// if (count != allDates.size()) {
			if (allDates.size() > 0) {

				filmID = node.getAttributeValue("id");
				filmIDs.add(filmID);

				filmTitle = node.getChildText("title");
				filmGenre = node.getChildText("genre");
				filmDescription = node.getChildText("description");
				filmStart = node.getChildText("start");
				// filmRating = node.getChildText("rating");
				filmImage = node.getChildText("image");

				// CREATES A NEW GRIDPANE AND POPULATES IT WITH THE FILM
				// INFORMATION
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

				ObservableList<String> obList = FXCollections.observableList(allDates);

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

	}

	public void filtersDates() {

		String selectedDate = filterDates.getValue().format(DateTimeFormatter.ofPattern("dd-MM-YY"));

		for (int i = 0; i < centreAnchor.getChildren().size(); i++) {

			GridPane grid = (GridPane) centreAnchor.getChildren().get(i);
			ComboBox filmDates = (ComboBox) grid.getChildren().get(6);
			ObservableList<String> obList3 = FXCollections.observableList(filmDates.getItems());

			int a = 0;
			for (String element : obList3) {
				if (selectedDate.equalsIgnoreCase(element)) {
					a = 1;
				}
			}
			if (a == 0) {
				grid.setVisible(false);
			}
		}

	}

	public void filtersGenre() {
		
		String selectedGenre = filterGenre.getSelectionModel().getSelectedItem().toString();

		for (int i = 0; i < centreAnchor.getChildren().size(); i++) {

			GridPane grid = (GridPane) centreAnchor.getChildren().get(i);
			Label filmGenre = (Label) grid.getChildren().get(2);
			
			String labels = filmGenre.getText();
			System.out.println(labels);
			System.out.println(selectedGenre);

			if  (!filmGenre.getText().equalsIgnoreCase(selectedGenre)) 
				grid.setVisible(false);
		}
		
	}

	public void backtoAllFilms() {

	}

	// EVENTHANDLER FOR THE BOOKNG BUTTON - WILL TAKE YOU TO THE BOOKING PAGE
	final static public EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(final ActionEvent event) {
			Button btn = (Button) event.getSource();
			GridPane grid = (GridPane) btn.getParent();
			List childList = grid.getChildren();

			Label selectedName = (Label) childList.get(1);
			Label timeAndLength = (Label) childList.get(3);
			String[] selectedTime = timeAndLength.getText().split(" ");
			ComboBox<String> c = (ComboBox) childList.get(6);
			String selectedDate = c.getSelectionModel().getSelectedItem();

			if (selectedDate == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("Date not selected!");
				alert.setContentText("Please select a date for this film");
				alert.showAndWait();
			} else {
				pageTitle = selectedName.getText() + " " + selectedDate + " " + selectedTime[0];
				CinemaMain main = new CinemaMain();
				main.goToNextPage("view/BookingPage.fxml", pageTitle);
			}

		}
	};

	/////////// NAVIGATION FUNCTIONS ////////////

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

}
