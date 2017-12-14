package cinema.customer_controllers;

import java.io.File;
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
import cinema.shared_controllers.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller class for the customer what's on page. On initialization, it parses 'film.xml' and lays out
 * all the film information in a list of GridPanes which are displayed in a central VBox. 
 * Defines functions to filter the page content based on date and genre. Defines a button handler event
 * for the booking button. Defines navigation functions to other pages. 
 * 
 * @author carolinesmith, daianabassi
 *
 */
public class WhatsOnCustController implements Initializable {

    //DECLARES ALL THE FXML ELEMENTS USED BY THIS CONTROLLER
	@FXML
	public VBox centreAnchor;
	@FXML
	public DatePicker filterDates;
	public ComboBox filterGenre;
	public Button allFilms;
	@FXML
	private Label helloUser;

	Element root; //AN XML ROOT ELEMENT TO SET WHEN PARSING XML DOCUMENTS
	List list;    //A LIST TO PASS THE XML NODES TO FROM XML PARSING
	 
	//Loads the style sheet
    //	String style = getClass().getResource("style.css").toExternalForm();

	// DECALRES FILM VARIABLES TO STORE VALUES FROM XML PARSING
	public static String pageTitle;
	String filmID, filmTitle, filmGenre, filmDescription, filmStart, filmLength, filmDateTimes, filmRating, filmImage;

	ArrayList<String> filmIDs = new ArrayList<String>();     //AN ARRAYLIST FOR THE FILM IDs

	//DECLARES LISTS TO ADD FILM GRIDPANES TO: THE CENTRAL VBOX IS SET WITH DIFFERENT LISTS OF GRIDPANES (DIFFERENT FILMS)
	//DEPENDING ON WHAT FILTERS ARE SELECTED BY THE USER
	protected List<GridPane> gridList;      //THIS LIST IS FOR ALL FILMS
	protected List<GridPane> genreList;     //THIS IS FOR FILMS THAT MATCH THE USER'S SELECTED GENRE
	protected List<GridPane> dateList;      //THIS IS FOR FILMS THAT MATCH THE USER'S SELECTED DATE

	// USES THE INITIALIZE METHOD TO PARSE XML AND LAYOUT THE FILMS CURRENTLY IN
	// THE XML FILE WHEN PAGE IS LOADED
	/**
	 * Called to initialize a controller after its root element has been completely processed.
	 * Parses 'film.xml' and lays out the information for each film in a gridpane. Sets the central
	 * VBox with a vertical list of gridpanes (films).
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		gridList = new ArrayList<>();   //INITIALISES THE LIST OF GRIDPANES FOR 'ALL FILMS'

		filterGenre.getItems().addAll("Horror", "Comedy", "Children's", "Action", "Love");   // SETS UP THE GENRE COMBOBOX

		//PASSES 'film.xml' TO FILE
		File xmlFile = new File("film.xml");

		//IF 'film.xml' EXSISTS PARSES IT BY CALLING 'readsXML' FROM 'cinema.XML.ReadXMLFile'
		if (xmlFile.exists()) {
			try {
				ReadXMLFile read = new ReadXMLFile("film.xml");
				root = read.readsXML();       //RETURNS THE ROOT NODE
			} catch (Exception e) {
				CinemaMain.LOGGER.warning("Couldn't parse film.XML");
			}

			// GETS LIST OF CHILD NODES OF THE ROOT ELEMENT IN 'film.xml'
			list = root.getChildren("film");

			//ITERATES THROUGH THE LIST OF XML NODES TO EXTRACT ALL THE INFORMATION ABOUT THE FILM 
			for (int i = 0; i < list.size(); i++) {

				Element node = (Element) list.get(i);
				
				filmDateTimes = node.getChildText("dateTimes"); 

				ArrayList<String> datesForCombo = new ArrayList<String>();   //AN ARRAYLIST OF DATES FOR THIS FILM TO PASS TO COMBOBOX

				for (String element : filmDateTimes.split(",")) {    
					String filmDate = element.substring(0, 8);               //EXTRACTS JUST THE DATE (NOT THE TIME)

					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy"); //CREATES INSTANCE OF 'SimpleDateFormat' TO PARSE STRING TO DATES
					Date today = new Date();

					Date date = null;
					try {
						date = sdf.parse(filmDate);							//PARSES EACH STRING IN allDates TO A DATE
					} catch (ParseException e) {							//CATCHES PARSE EXCEPTION
						CinemaMain.LOGGER.warning("Couldn't parse filmDate to Date");
						e.printStackTrace();
					}
					if (today.compareTo(date) < 0) {						//IF THE DATE IS AFTER TODAY'S DATE ADDS IT TO THE LIST FOR THE COMBOBOX
						datesForCombo.add(element);							//THEREFORE REMOVES VIEWINGS THAT HAVE PAST
					}

				}

				// ONLY PROCEEDS TO PARSE THE XML FOR THIS FILM NODE IF SOME OF THE DATES ARE TODAY OR FUTURE
				// i.e. DOESN'T DISPLAY ON WHATS ON PAGE WHERE ALL DATES HAVE PASSED
				if (datesForCombo.size() > 0) {

					filmID = node.getAttributeValue("id");
					filmIDs.add(filmID);                                 

					filmTitle = node.getChildText("title");
					filmGenre = node.getChildText("genre");
					filmDescription = node.getChildText("description");
					// filmRating = node.getChildText("rating");
					filmImage = node.getChildText("image");

					// CREATES A NEW GRIDPANE AND POPULATES IT WITH THE FILM INFORMATION
					// FROM THE XML PARSING
					GridPane gridPane = new GridPane();
					gridPane.setPrefSize(680, 800);

					Image filmPic = new Image(filmImage, 189, 280, false, false);
					ImageView viewPic = new ImageView(filmPic);

					Label title = new Label(filmTitle);
					Label genre = new Label(filmGenre);
					Label runningTime = new Label("Running Time: 1 hour");
					runningTime.setWrapText(true);

					Label description = new Label(filmDescription);
					description.setWrapText(true);

					Label blank = new Label(" ");

					ObservableList<String> obList = FXCollections.observableList(datesForCombo);
					ComboBox dateList = new ComboBox();
					dateList.getItems().clear();
					dateList.setItems(obList);
					dateList.setPromptText("Pick a Date");
					dateList.setPrefSize(220, 35);

					// BOOKING BUTTON WITH A 'buttonHandler' EVENTHANDLER
					Button book = new Button("Book");
					book.setId("book");
					book.setOnAction(buttonHandler);
					book.setPrefSize(120, 20);

					// ADDS ALL NODES (LABELS/BUTTON/COMBOBOX) TO THE GRIDPANE
					gridPane.add(viewPic, 0, 1, 1, 4);
					gridPane.add(title, 1, 1, 2, 1);
					gridPane.add(genre, 1, 2, 2, 1);
					gridPane.add(runningTime, 1, 3, 2, 1);
					gridPane.add(description, 1, 4, 2, 2);
					gridPane.add(dateList, 4, 3, 2, 1);
					gridPane.add(book, 4, 4, 2, 1);
					gridPane.add(blank, 0, 5, 1, 1);
					
					

					// SETS SOME COLUMN WIDTH CONSTRAINTS FOR PROPER LAYOUT
					ColumnConstraints col1 = new ColumnConstraints();
					ColumnConstraints col2 = new ColumnConstraints();
					ColumnConstraints col3 = new ColumnConstraints();
					col1.setPercentWidth(3);
					col2.setPercentWidth(5);
					col3.setPercentWidth(3);
					gridPane.getColumnConstraints().addAll(col1, col2, col3);
					gridPane.setHgap(10);

					//ADDS THIS FILM'S GRIDPANE TO THE LIST OF GRIDPANES FOR 'ALL FILMS'
					gridList.add(gridPane);
				}
			}

			//DISPLAYS ALL THE FILMS BY ADDING THEM THE THE CENTRAL VBOX
			centreAnchor.getChildren().setAll(gridList);

		} else {
			//IF NO FILMS IN THE FUTURE CURRENTLY, DISPLAYS A LABEL TO EXPLAIN THIS
			Label labelNoFilms = new Label();            
			labelNoFilms.setText("No films scheduled yet!");
			centreAnchor.getChildren().add(labelNoFilms);
		}

	}

/**
 * Filters the films displayed on the page by date. Gets the user date selection from the DatePicker. Iterates through the
 * showing dates for each film and adds it to a new ArrayList of GridPanes only if the film has a showing on a date that matches
 * the selected date. Resets the page using these GridPanes.
 * 
 */
	public void filtersDates() {

		filterGenre.getSelectionModel().clearSelection();     //CLEARS THE GENRE SELECTION AS THE PAGE DOES NOT FILTER BY BOTH GENRE AND DATE

		if (filterDates.getValue() == null) {
			backtoAllFilms();								 //LOADS ALL FILMS TO THE PAGE IF NO DATE IS SELECTED
		} else if (gridList.size() > 0) {
			String selectedDate = filterDates.getValue().format(DateTimeFormatter.ofPattern("dd-MM-YY"));    //FORMATS SELECTED DATE
			
			dateList = new ArrayList<>();					 //INITIALISES THE LIST OF GRIDPANES TO SET WITH FILMS THAT MATCH SELECTED DATE

			for (int i = 0; i < gridList.size(); i++) {      //ITERATES THROUGH THE LIST OF GRIDPANES FOR 'ALL FILMS' 
				GridPane grid = gridList.get(i);
				ComboBox filmDateTimes = (ComboBox) grid.getChildren().get(5);    //GETS THE 5TH CHILD ELEMENT - THE DATE COMBOBOX

				ObservableList<String> obList3 = FXCollections.observableList(filmDateTimes.getItems());   //GETS THE ITEMS FROM THE COMBOBOX
			
				ArrayList<String> arrJustDates = new ArrayList<String>();         //A LIST TO ADD JUST THE DATES (NOT TIMES) TO

				int y = 0;
				for (String element : obList3) {               	   //ITERATES THROUGH FILM DATES
					String justDate = element.substring(0, 8);     //GETS THE DATE (IGNORES THE TIME) 
					arrJustDates.add(justDate);
					if (selectedDate.equalsIgnoreCase(justDate))   //IF THE DATE MATCHES THE SELECTED DATE SET Y EQUAL TO 1
						y = 1;									    
				}

				if (y == 1)                                        //IF Y==1 ADDS THE FILM GRIDPANE TO THE 'DATELIST' LIST OF GRIDPANES
					dateList.add(grid);

			}

			centreAnchor.getChildren().clear(); 				   //CLEARS THE FILMS DISPLAYED ON THE PAGE
			centreAnchor.getChildren().setAll(dateList);           //RESETS THE VBOX WITH THE 'DATELIST' LIST OF GRIDPANES
		}

	}

	/**
	 * Filters the films displayed on the page by genre. Gets the user genre selection from the Genre ComboBox. Iterates through the
	 * genres for each film and adds it to a new ArrayList of GridPanes only if the film is a genre that matches
	 * the selected genre. Resets the page using these GridPanes.
	 * 
	 */
	public void filtersGenre() {

		filterDates.getEditor().clear();                     //CLEARS THE DATE SELECTION AS THE PAGE DOES NOT FILTER BY BOTH GENRE AND DATE

		if (filterGenre.getSelectionModel().isEmpty()) {    //IF NO GENRE SELECTED DISPLAYS ALL FILMS
			backtoAllFilms();
		}

		else if (gridList.size() > 0) {
			String selectedGenre = filterGenre.getSelectionModel().getSelectedItem().toString();   //GETS THE SELECTED GENRE
			
			genreList = new ArrayList<>();                   //INITIALISES THE LIST OF GRIDPANES TO SET WITH FILMS THAT MATCH SELECTED GENRE

			for (int i = 0; i < gridList.size(); i++) {     //ITERATES THROUGH THE LIST OF GRIDPANES FOR 'ALL FILMS' 
				GridPane grid = gridList.get(i);
				Label filmGenre = (Label) grid.getChildren().get(2);   //GETS THE GENRE FOR THE FILM

				if (filmGenre.getText().equalsIgnoreCase(selectedGenre)) {     //IF GENRE EQUALS SELECTED GENRE ADDS THIS FILM TO THE NEW GRIDPANE LISE
					genreList.add(grid);
				}
			}
			centreAnchor.getChildren().clear();              //CLEARS THE FILMS DISPLAYED ON THE PAGE
			centreAnchor.getChildren().setAll(genreList);    //RESETS THE VBOX WITH THE 'GENRELIST' LIST OF GRIDPANES
		}

	}

	/**
	 * Resets the list of films displayed on the page back to all films - by resetting the central VBox with the list of GridPanes for all films
	 * which was populated at initialization.
	 */
	public void backtoAllFilms() {

		if (gridList.size() > 0) {
			filterGenre.getSelectionModel().clearSelection();   //CLEARS THE GENRE BOX
			filterDates.getEditor().clear();			        //CLEARS THE DATEPICKER
			centreAnchor.getChildren().clear();
			centreAnchor.getChildren().setAll(gridList);
		}

	}

	
/**
 * Event handler for the booking button for each film. Takes customer to booking page and uses the 
 * film name and date/time information to create the unique page title for that booking.
 */
	final static public EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(final ActionEvent event) {

			Button btn = (Button) event.getSource();

			if (btn.getId().equalsIgnoreCase("book")) {

				GridPane grid = (GridPane) btn.getParent();
				List childList = grid.getChildren(); 				//GETS THE CONTENTS OF THE GRIDPANE

				Label selectedName = (Label) childList.get(1);      //GETS THE FILM NAME SELECTED FOR BOOKING
				ComboBox<String> c = (ComboBox) childList.get(5);	
				String selectedDateTime = c.getSelectionModel().getSelectedItem();	 //GETS THE FILM DATE/TIME SELECTED

				if (selectedDateTime == null) { 					//SHOWS AN ALERT IF NO DATE/TIME SELECTED AS MUST SELECT ONE
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("Date not selected!");
					alert.setContentText("Please select a date/time for this film");
					alert.showAndWait();
				} else {
					pageTitle = selectedName.getText() + " " + selectedDateTime;    //CREATES THE UNIQUE PAGE TITLE AND PASSES IT TO STATIC FIELD
					CinemaMain main = new CinemaMain();
					main.goToNextPage("shared_view/BookingPage.fxml", pageTitle);  //OPENS BOOKING PAGE
				}
			}

		}

	};

/////////// NAVIGATION FUNCTIONS ////////////

	/**
	 * Takes use back to login page when log out menu item selected.
	 * Calls <code>goToLoginPage</code> function from <code>cinema.CinemaMain</code>
	 * @param event the menu item click event
	 */
	@FXML
	private void logsOut(ActionEvent event) {
		CinemaMain main = new CinemaMain();
		main.goToLoginPage("shared_view/LoginScreen.fxml", "Cinema Login");
	}

	/**
	 * Loads the customer home page when home menu item selected.
	 * Calls <code>goToNextPage</code> function from <code>cinema.CinemaMain</code>
	 * @param event the menu item click event
	 */
	@FXML
	private void goBackHome(ActionEvent event) {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("customer_view/CustomerHome.fxml", "Customer Home");
	}

	/**
	 * Loads the customer/employee account page when account menu item selected.
	 * Calls <code>goToNextPage</code> function from <code>cinema.CinemaMain</code>
	 * @param event the menu item click event
	 */
	@FXML
	private void goToMyAccount() {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("customer_view/CustomerAccount.fxml", "My Account");
	}

}
