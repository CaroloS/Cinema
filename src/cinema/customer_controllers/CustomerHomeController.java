package cinema.customer_controllers;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

import org.jdom2.Element;

import cinema.CinemaMain;
import cinema.XML.ReadXMLFile;
import cinema.shared_controllers.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Controller class for the Customer Home page. Sets up an image carousel and embedded YouTube videos at initialization.
 * Defines functions to scroll backwards and forwards through image carousel and to navigate to different customer pages/log out.
 * 
 * @author carolinesmith, daianabassi
 *
 */
public class CustomerHomeController implements Initializable {

//DECLARES ALL THE FXML ELEMENTS USED BY THIS CONTROLLER//
	@FXML
	private ScrollPane homeAnchor;
	@FXML
	private Pane carouselAnchor;
	@FXML
	private BorderPane borderPane;
	@FXML
	private Button forwardButton, backButton, freeTicket;
	@FXML
	private VBox menuAnchor, vBox1, vBox2;
	

	Element root;  //AN XML ROOT ELEMENT TO SET WHEN PARSING XML DOCUMENTS
	List list;     //A LIST TO PASS THE XML NODES TO FROM XML PARSING

	ArrayList<String> imageList = new ArrayList<String>();  //AN ARRAYLIST TO PASS THE RELATIVE PATHS OF IMAGES TO FOR CAROUSEL

	ImageView viewPic; //IMAGEVIEW TO SET THE CAROUSEL IMAGES TO

	
	/**
	 * Called to initialize a controller after its root element has been completely processed.
	 * Parses 'film.xml' file for film date information for setting up the carousel.
	 * Loads embedded youtube videos to the home page.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//URLS FOR EMBEDDED YOUTUBE VIDEOS 
		String content_Url1 = "<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/19jGdktyGgk\" frameborder=\"0\" gesture=\"media\" allow=\"encrypted-media\" allowfullscreen></iframe>";
		String content_Url2 = "<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/8O6eQBjDWDM\" frameborder=\"0\" gesture=\"media\" allow=\"encrypted-media\" allowfullscreen></iframe>";
		//LOADS VIDEO CONTENT FROM YOUTUBE AND SETS TO VBOXs AT BOTTOM OF HOMEPAGE
		loadVideo(content_Url1, vBox1);
		loadVideo(content_Url2, vBox2);
		
		//PASSES 'film.xml' TO FILE
		File xmlFile = new File("film.xml");

		//IF 'film.xml' EXSISTS PARSES IT BY CALLING 'readsXML' FROM 'cinema.XML.ReadXMLFile'
		if (xmlFile.exists()) {
			try {
				ReadXMLFile read = new ReadXMLFile("film.xml");
				root = read.readsXML();     //RETURNS THE ROOT NODE
			} catch (Exception e) {
				CinemaMain.LOGGER.warning("Couldn't parse film.XML");
			}

			// GETS LIST OF CHILD NODES OF THE ROOT ELEMENT IN 'film.xml'
			list = root.getChildren("film");

			//ITERATES THROUGH THE LIST OF XML NODES TO EXRTACT FILM DATE/TIME INFORMATION 
			//THIS WILL BE USED TO ENSURE IMAGES ARE NOT SET TO THE CAROUSEL FOR FILMS IN THE PAST
			for (int i = 0; i < list.size(); i++) {

				Element node = (Element) list.get(i);
				String filmDateTimes = node.getChildText("dateTimes");

				List<String> allDates = new ArrayList<String>();  //ARRAYLIST TO PASS JUST THE FILM DATES TO 

				for (String element : filmDateTimes.split(",")) {  
					String date = element.substring(0, 8);    //EXTRACTS JUST THE DATE (NOT THE TIME)
					allDates.add(date);
				}
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");  //CREATES INSTANCE OF 'SimpleDateFormat' TO PARSE STRING TO DATES
				Date today = new Date();
				Iterator<String> it = allDates.iterator();    //SETS UP AN ITERATOR TO ITERATE THROUGH 'allDates'
				
				while (it.hasNext()) {   
					String element = it.next();

					Date date = null;
					try {
						date = sdf.parse(element);     //PARSES EACH STRING IN allDates TO A DATE
					} catch (ParseException e) {       //CATCHES PARSE EXCEPTION
						CinemaMain.LOGGER.warning("Couldn't parse element to Date");
						e.printStackTrace();
					}
					if (today.compareTo(date) > 0)     //IF THE DATE IS BEFORE TODAY'S DATE REMOVES THE DATE FROM THE LIST
						it.remove();
				}

				if (allDates.size() > 0)        	   //IF THE LIST OF DATE FOR THIS NODE (THIS FILM) IS >0 ADDS IT TO THE LIST OF IMAGES FOR CAROUSEL
					imageList.add(node.getChildText("image"));      //i.e. IF THE FILM HAS AT LEAST ONE DATE IN THE FUTURE - DISPLAY AN IMAGE FOR IT
			}
		}

		if (imageList.size() > 0) {                     // IF THERE ARE IMAGES TO DISPLAY (FILMS IN THE FUTURE) 
			Image filmPic = new Image(imageList.get(0), 270, 400, false, false);   //ADD THE FIRST IMAGE TO THE CAROUSEL
			viewPic = new ImageView(filmPic);
			viewPic.setId("0");

			carouselAnchor.getChildren().add(viewPic);	//SETS THE IMAGEVIEW TO A PANE
			viewPic.fitWidthProperty().bind(carouselAnchor.widthProperty());    //BINDS THE IMAGEVIEW SIZE TO THE PANE
			viewPic.fitHeightProperty().bind(carouselAnchor.heightProperty());
			viewPic.setPreserveRatio(false);
		}

	}

	/**
	 * Iterates forwards through the list of images for films with showings in the future. Gets the next image in the list
	 * and replaces the current carousel image with it. 
	 */
	@FXML
	public void scrollsForward() {

		if (imageList.size() > 0) {

			int a = Integer.parseInt(viewPic.getId()); //GETS THE ID OF THE CURRENT IMAGE DISPLAYED AS INTEGER

			if (a != imageList.size() - 1) {      	                   // IF ID DOESN'T EQUAL THE SIZE OF THE IMAGE LIST
																	   
				ListIterator<String> it = imageList.listIterator(a);   //ITERATES FORWARDS THROUGH IMAGE LIST

				if (it.hasNext()) {
					Image filmPic = new Image(imageList.get(a + 1), 200, 200, false, false);   //GETS THE NEXT IMAGE IN THE LIST
					viewPic = new ImageView(filmPic);											//SETS IT TO AN IMAGEVIEW
					viewPic.setId(Integer.toString(a + 1));										//SETS IT'S ID TO ONE LARGER THAN CURRENT IMAGE

					carouselAnchor.getChildren().add(viewPic);									//DISPLAYS IT IN THE CAROUSEL (REPLACES CURRENT IMAGE)
					viewPic.fitWidthProperty().bind(carouselAnchor.widthProperty());
					viewPic.fitHeightProperty().bind(carouselAnchor.heightProperty());
					viewPic.setPreserveRatio(false);
				}
			}
		}

	}
	
	/**
	 * Iterates backwards through the list of images for films with showings in the future. Gets the next image in the list
	 * and replaces the current carousel image with it. 
	 */
	@FXML
	public void scrollsBack() {

		if (imageList.size() > 0) {

			int b = Integer.parseInt(viewPic.getId());

			if (b != 0) {													//STARTS ITERATING THROUGH LIST IF THE CURRENT IMAGE ISN'T THE FIRST
																			//IN THE LIST (i.e. THE FIRST IMAGE DISPLAYED ON INITIALISATION)
				ListIterator<String> itBack = imageList.listIterator(b);

				if (itBack.hasPrevious()) {									//ITERATES BACKWARDS THROUGH IMAGE LIST
					Image filmPic = new Image(imageList.get(b - 1), 200, 200, false, false);   //GETS THE PREVIOUS IMAGE
					viewPic = new ImageView(filmPic);
					viewPic.setId(Integer.toString(b - 1));

					carouselAnchor.getChildren().add(viewPic);				//SETS IT TO THE CAROUSEL 
					viewPic.fitWidthProperty().bind(carouselAnchor.widthProperty());
					viewPic.fitHeightProperty().bind(carouselAnchor.heightProperty());
					viewPic.setPreserveRatio(false);
				}
			}
		}

	}

/**
 * Loads the embedded YouTube video to a WebView and adds this WebView a VBox.
 * @param content String URL of the embedded YouTube video.
 * @param vbox VBox to set the video to.
 */
	public void loadVideo(String content, VBox vbox) {

		WebView webView = new WebView();
		WebEngine webEngine = webView.getEngine();
		webEngine.loadContent(content);
		vbox.getChildren().add(webView);
		vbox.setPrefSize(560, 350);
	}
	

//////// NAVIGATION FUNCTIONS///////

	/**
	 * Loads the customer what's on page when 'book a film' button clicked or 'what's on' menu item selected.
	 * Calls <code>goToNextPage</code> function from <code>cinema.CinemaMain</code>
	 * @param event the button/menu item click event
	 */
	@FXML
	private void loadfilms(ActionEvent event) {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("customer_view/WhatsOnCustomer.fxml", "What's On");
	}
	
	/**
	 * Takes user back to login page when log out menu item selected.
	 * Calls <code>goToLoginPage</code> function from <code>cinema.CinemaMain</code>
	 * @param event the menu item click event
	 */
	@FXML
	private void logsOut(ActionEvent event) {
		CinemaMain main = new CinemaMain();
		main.goToLoginPage("shared_view/LoginScreen.fxml", "Cinema Login");
	}

	/**
	 * Loads the customer account page when account menu item selected.
	 * Calls <code>goToNextPage</code> function from <code>cinema.CinemaMain</code>
	 * @param event the menu item click event
	 */
	@FXML
	private void goToMyAccount() {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("customer_view/CustomerAccount.fxml", "My Account");
	}
	
	/**
	 * Loads the customer questionnaire page when 'win a free ticket' button clicked.
	 * Calls <code>goToNextPage</code> function from <code>cinema.CinemaMain</code>
	 * @param event the button click event
	 */
	@FXML 
	public void goesToQuestionnaire() {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("customer_view/Questionnaire.fxml", "Questionnaire");
	}

}
