package cinema.employee_controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.CinemaMain;
import cinema.customer_controllers.WhatsOnCustController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Controller class for the employee what's on page. It extends the controller for the customer
 * what's on page. Calls the <code>super.initialize</code> method to lay out the page with all the film
 * showings. Extends the initialize method of it's parent, to delete the booking button and replace it with
 * a 'booking information' button - employees can view the film bookings but not book themselves. 
 * @see <code>cinema.customer_controllers.WhatsOnCustController</code>
 * @author carolinesmith, daianabassi
 *
 */
public class WhatsOnEmpController extends WhatsOnCustController {

	//NEW BUTTON TO REPLACE 'BOOK' BUTTON
	private Button bookingInfo;
    
	//DECLARES ALL THE FXML ELEMENTS USED BY THIS CONTROLLER
	@FXML
	public VBox centreAnchor;
	@FXML
	public DatePicker filterDates;
	public ComboBox filterGenre;
	public Button allFilms;

	/**
	 * Called to initialize a controller after its root element has been completely processed. 
	 * Parses 'film.xml' and lays out the information for each film in a gridpane. Sets the central 
	 * VBox with a vertical list of gridpanes (films). Removes the book button from the parent class
	 * and replaces with a booking information button.
	 */
	public void initialize(URL location, ResourceBundle resources) {

		super.initialize(location, resources);

		// ITERATES THROUGH THE ALL THE GRIDPANES ON THE PAGE AND REMOVES THE 'BOOK' BUTTON
		// ADDS A 'BOOKING INFO' BUTTON FOR EMPLOYEE TO VIEW SEAT BOOKING INFO
		if (gridList.size() > 0) {

			for (int i = 0; i < super.centreAnchor.getChildren().size(); i++) {

				GridPane grid = (GridPane) super.centreAnchor.getChildren().get(i);
				grid.getChildren().remove(6);

				bookingInfo = new Button("Booking Info");
				bookingInfo.setId("book");
				bookingInfo.setOnAction(super.buttonHandler);
				bookingInfo.setPrefSize(120, 20);

				grid.add(bookingInfo, 4, 4, 1, 1);

			}
		}
	}

	//////////// NAVIGATION FUNCTIONS////////////

	/**
	 * Loads the employee home page when home menu item selected.
	 * Calls <code>goToNextPage</code> function from <code>cinema.CinemaMain</code>
	 * @param event the menu item click event
	 */
	@FXML
	private void goBackHome(ActionEvent event) {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("employee_view/EmployeeHome.fxml", "Employee Home");
	}

	/**
	 * Loads the customer 'all bookings' page when menu item selected.
	 * Calls <code>goToNextPage</code> function from <code>cinema.CinemaMain</code>
	 * @param event the menu item click event
	 */
	@FXML
	private void goToAllBookings(ActionEvent event) {

		// TAKES USER TO ALL BOOKINGS PAGE FROM MENU ITEM
		CinemaMain main = new CinemaMain();
		main.goToNextPage("employee_view/AllBookings.fxml", "All Bookings");

	}
	
	

}