package cinema;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import cinema.XML.CreateFilmBookingsXML;
import cinema.XML.CreateFilmXML;
import cinema.XML.CreateUserBookingsXML;
import cinema.XML.CreateUsersXML;
import cinema.employee_controllers.EmployeeHomeController;
import cinema.shared_controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Contains the <code>main</code> method to launch the application. Defines navigation methods and sets up the logger. 
 * @author carolinesmith, daianabassi
 *@version 1.0
 */
public class CinemaMain extends Application {

	// GETS THE GLOBAL LOGGER
	public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	// DECLARES PUBLIC STAGE SO SCENES IN OTHER CLASSES CAN SET THE STAGE
	public static Stage thestage;

	/**
	 * Navigates to the next page. Loads the FXML passed to it and applies the page title specified. 
	 * @param pathToFXML String relative path to FXML document to load
	 * @param pageTitle String page title for the scene
	 */
	public void goToNextPage(String pathToFXML, String pageTitle) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(pathToFXML));

			Scene scene = new Scene(root, 1350, 850);
			thestage.setTitle(pageTitle);
			thestage.setScene(scene);
			thestage.show();
		} catch (Exception e) {
			LOGGER.warning("Couldn't load that page");
			e.printStackTrace();
		}
	}
	
	/**
	 * Navigates to the log-in page. Loads the FXML passed to it and applies the page title specified. 
	 * @param pathToFXML String relative path to FXML document to load
	 * @param pageTitle String page title for the scene
	 */
	public void goToLoginPage(String pathToFXML, String pageTitle) {
		
		//RESETS THE LOGGED IN USER AND CORRESPONDING ID AND USERNAME TO NULL
		LoginController.userID = null; 
		LoginController.loggedInUser = null;
		LoginController.usersName = null;
		EmployeeHomeController.editEmployee = false;
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource(pathToFXML)); 

			Scene scene = new Scene(root,1350, 850);  //SETS A NEW SCENE WITH THE LOADED FXML AND DEFINES THE WDITH/HEIGHT 
			thestage.setTitle(pageTitle);
			thestage.setScene(scene);   //SETS THE SCENE TO THE STAGE 
			thestage.show();
		} catch (Exception e) {
			LOGGER.warning("Couldn't load that page");
			e.printStackTrace();
		}
	}
	
	

	/**
	 * Method called from <code>cinema.CinemaMain.main</code>. Loads the first scene (LoginScreen.fxml).
	 * @param primaryStage the primary stage of this JavaFX application
	 */
	@Override
	public void start(Stage primaryStage) {

		//SETS THE STATIC VARIABLE 'thestage' TO THE LOCAL 'primaryStage' VARIABLE
		thestage = primaryStage;
		
		
		
		// CALLS METHOD TO LOAD LOGIN SCREEN
		goToLoginPage("shared_view/LoginScreen.fxml", "CinemaLogin");
		
	}

	/**
	 * The starting method for the application. Sets up the Logger from <code>cinema.CinemaLogger</code> and launches <code>cinema.CinemaMain.start</code>
	 * @param args the array of arguments passed on launching the program
	 */
	public static void main(String[] args) {

		// SETS UP THE LOGGER FROM 'CinemaLogger.java'
		try {
			CinemaLogger.setup();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Problems with creating the log files");
		}
		
		//CREATES A NEW FILM XML FILE IF ONE DOESN'T EXIST TO PROVIDE SOME INITIAL
		//FILMS WHEN THE PROGRAM IS FIRST RUN
		File file = new File("film.xml");
		if (!file.exists()) {
			CreateFilmXML filmXML = new CreateFilmXML("film.xml", "films");
			filmXML.getsRoot();
			filmXML.createsInitialFilm("Titanic", "Love", "A seventeen-year-old aristocrat falls in love with a kind but "
					+ "poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", "22-11-17 1900,01-02-18 1700,02-02-18 1900,", "images/titanic.jpeg");
			
			filmXML.createsInitialFilm("The Producers", "Comedy", "After putting together another Broadway flop, down-on-his-luck Producer "
					+ "Max Bialystock teams up with timid accountant Leo Bloom in a get-rich-quick scheme to put on the world's worst show.", 
					"23-11-17 2000,11-02-18 1600,11-02-18 1400,", "images/producers.jpeg");
			
			filmXML.createsInitialFilm("Spirited Away", "Children's", "During her family's move to the suburbs, a sullen 10-year-old girl wanders "
					+ "into a world ruled by gods, witches, and spirits, and where humans are changed into beasts.", 
					"09-02-18 2000,", "images/spiritedaway.jpeg");
			
			filmXML.createsInitialFilm("Jaws", "Horror", "A local sheriff, a marine biologist and an old seafarer team up to hunt down a great "
					+ "white shark wrecking havoc in a beach resort.", 
					"08-02-18 1200,09-02-18 1400,", "images/jaws.jpeg");
			
			filmXML.createsInitialFilm("American Beauty", "Horror", "A sexually frustrated suburban father has a mid-life crisis after becoming "
					+ "infatuated with his daughter's best friend.", 
					"02-11-18 1200,03-02-18 1200,", "images/americanbeauty.jpeg");
			
			filmXML.createsInitialFilm("The Hurt Locker", "Action", "During the Iraq War, a Sergeant recently assigned to an army bomb squad is "
					+ "put at odds with his squad mates due to his maverick way of handling his work.", 
					"03-02-18 2000,", "images/hurtlocker.jpeg");
			
			filmXML.createsInitialFilm("I.T.", "Horror", "A group of bullied kids band together when a shapeshifting monster, taking the appearance "
					+ "of a clown, begins hunting children.", 
					"25-11-17 1200,", "images/IT.jpeg");
		}
		
		//CREATES A NEW FILM BOOKING XML FILE IF ONE DOESN'T EXIST TO PROVIDE SOME INITIAL
		//FILM BOOKING INFORMATION WHEN THE PROGRAM IS FIRST RUN
		File file3 = new File("filmBookings.xml");
		if(!file3.exists()){
			CreateFilmBookingsXML bookingsXML = new CreateFilmBookingsXML("filmBookings.xml", "bookings");
			bookingsXML.getsRoot();
			bookingsXML.createsInitialBookings("I.T. 25-11-17 1200", "A1 A2 A3 A4 A5 A6 B1 D4 D1 D3 B2", "11", "25");
			bookingsXML.createsInitialBookings("Titanic 22-11-17 1900", "D3 C7 B8 A3", "4", "32");
			bookingsXML.createsInitialBookings("Titanic 01-02-18 1700", "", "0", "36");
			bookingsXML.createsInitialBookings("Titanic 02-02-18 1900", "D9 D8 A1", "3", "33");
			bookingsXML.createsInitialBookings("The Producers 23-11-17 2000", "A1 A2 A3 A4 A5 A6 A7 A8 A9", "9", "27");
			bookingsXML.createsInitialBookings("The Producers 11-02-18 1600", "D4 B5 A9", "3", "33");
			bookingsXML.createsInitialBookings("The Producers 11-02-18 1400", "B2 A3", "2", "34");
			bookingsXML.createsInitialBookings("Spirited Away 09-02-18 2000", "", "0", "36");
			bookingsXML.createsInitialBookings("Jaws 08-02-18 1200", "", "0", "36");
			bookingsXML.createsInitialBookings("American Beauty 02-11-18 1200", "", "0", "36");
			bookingsXML.createsInitialBookings("American Beauty 03-02-18 1200", "", "0", "36");
			bookingsXML.createsInitialBookings("The Hurt Locker 03-02-18 2000", "", "0", "36");
		}
		
		
		//CREATES A NEW USERS XML FILE IF ONE DOESN'T EXIST TO PROVIDE SOME AN
		//INITIAL EMPLOYEE AND CUSTOMER LOGIN WHEN THE PROGRAM IS FIRST RUN
		File file2 = new File("users.xml");
		if(!file2.exists()) {
			CreateUsersXML usersXML = new CreateUsersXML("users.xml", "users");
			usersXML.getsRoot();
			usersXML.createInitialUser("304", "SAdmin", "SAdmin", "SAdmin@cinema", "123456", "employee", "sadmin", "123456", "images/greencamera.jpeg");
			usersXML.createInitialUser("305", "Cust", "Cust", "Cust@cinema", "888777", "customer", "customer", "123456", "images/greencamera.jpeg");
		}
		
		//CREATES A NEW USER BOOKING XML FILE IF ONE DOESN'T EXIST TO PROVIDE SOME INITIAL
		//USER BOOKING INFORMATION FOR THE DEFAULT CUSTOMER ABOVE WHEN THE PROGRAM IS FIRST RUN
		File file4 = new File("userBookings.xml");
		if(!file4.exists()) {
			CreateUserBookingsXML newBooking = new CreateUserBookingsXML("userBookings.xml", "userBookings");
			newBooking.getsRoot();
			newBooking.CreateInitUserBooking("305", "I.T.", "25-11-17", "1200", "D4");
		}
		
		
		//LAUNCHES THE JAVAFX APPLICATION
		launch(args);
	}
}
