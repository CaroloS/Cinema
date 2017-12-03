package cinema.control;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class WhatsOnCustController implements Initializable {

	// DECALRES THE FXML ELEMENT TO SET THE NEW FILMS TO
	@FXML
	public VBox centreAnchor;
	@FXML
	public VBox outerAnchor;
	@FXML
	public ScrollPane scrollPane;
	@FXML
	public DatePicker filterDates;
	public ComboBox filterGenre;
	public Button allFilms;

	// DECALRES THE ROOT ELEMENT TO BE SET BY PARSING film.XML
	Element root;
	List list;

	// DECALRES FILM VARIABLES TO STORE VALUES FROM XML PARSING
	// Attribute filmID;
	public static String pageTitle;
	String filmID, filmTitle, filmGenre, filmDescription, filmStart, filmLength, filmDateTimes, filmRating, filmImage;

	ArrayList<String> filmIDs = new ArrayList<String>();
	
	List<GridPane> gridList ;
	List<GridPane> genreList ;
	List<GridPane> dateList ;

	
	// USES THE INITIALIZE METHOD TO PARSE XML AND LAYOUT THE FILMS CURRENTLY IN
	// THE XML FILE WHEN PAGE IS LOADED
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		gridList = new ArrayList<>();

		// SETS UP THE GENRE COMBOBOX
		filterGenre.getItems().addAll("", "Horror", "Comedy", "Children's", "Action", "Love");

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
			filmDateTimes = node.getChildText("dateTimes");

			ArrayList<String> datesForCombo = new ArrayList<String>();
			List<String> allDates = new ArrayList<String>();

			for (String element : filmDateTimes.split(",")) {
				datesForCombo.add(element);
				String date = element.substring(0, 8);
				allDates.add(date);
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
				Label runningTime = new Label("Running Time: 1 hour");
				runningTime.setWrapText(true);

				Label description = new Label(filmDescription);
				description.setWrapText(true);

				Label moreInfo = new Label("more info..");
				Label blank = new Label(" ");

				ObservableList<String> obList = FXCollections.observableList(datesForCombo);
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
				gridPane.add(runningTime, 1, 3, 2, 1);
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

				gridList.add(gridPane);
			}
		}
		
		//System.out.println(gridList.toString());
		centreAnchor.getChildren().setAll(gridList);

	}

	public void filtersDates() {

		String selectedDate = filterDates.getValue().format(DateTimeFormatter.ofPattern("dd-MM-YY"));
		dateList = new ArrayList<>();
		
		for (int i = 0; i < gridList.size(); i++) {
			GridPane grid = gridList.get(i);
			ComboBox filmDateTimes = (ComboBox) grid.getChildren().get(6);
			
			ObservableList<String> obList3 = FXCollections.observableList(filmDateTimes.getItems());
			ArrayList<String> arrJustDates = new ArrayList<String>();
			
			for (String element : obList3) {
				String justDate = element.substring(0, 8);
				arrJustDates.add(justDate);
				if (selectedDate.equalsIgnoreCase(justDate))
					dateList.add(grid);
			}
		}
		
		centreAnchor.getChildren().clear();
		centreAnchor.getChildren().setAll(dateList);
		
	}
	

	public void filtersGenre() {

		String selectedGenre = filterGenre.getSelectionModel().getSelectedItem().toString();
		genreList = new ArrayList<>();
		
		for (int i = 0; i < gridList.size(); i++) {
			GridPane grid = gridList.get(i);
			Label filmGenre = (Label) grid.getChildren().get(2);
			
			if (filmGenre.getText().equalsIgnoreCase(selectedGenre)) {
				genreList.add(grid);
			}
		}
		centreAnchor.getChildren().clear();
		centreAnchor.getChildren().setAll(genreList);
		

	}

	
	public void backtoAllFilms() {
		
		centreAnchor.getChildren().clear();
		centreAnchor.getChildren().setAll(gridList);
		
	}

	// EVENTHANDLER FOR THE BOOKNG BUTTON - WILL TAKE YOU TO THE BOOKING PAGE
	final static public EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(final ActionEvent event) {
			Button btn = (Button) event.getSource();
			GridPane grid = (GridPane) btn.getParent();
			List childList = grid.getChildren();

			Label selectedName = (Label) childList.get(1);
			ComboBox<String> c = (ComboBox) childList.get(6);
			String selectedDateTime = c.getSelectionModel().getSelectedItem();

			if (selectedDateTime == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("Date not selected!");
				alert.setContentText("Please select a date/time for this film");
				alert.showAndWait();
			} else {
				pageTitle = selectedName.getText() + " " + selectedDateTime;
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
	
	@FXML 
	private void goToMyAccount() {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("view/CustomerAccount.fxml", "My Account");
	}

}
