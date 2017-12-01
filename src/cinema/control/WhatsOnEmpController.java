package cinema.control;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.CinemaMain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class WhatsOnEmpController extends WhatsOnCustController {

	private Button bookingInfo;
	
	@FXML
	public VBox centreAnchor;
	
	//public static String pageTitle;

	// TAKES USER BACK TO 'Employee Home' PAGE WHEN 'HOME' MENU ITEM CLICKED
	@FXML
	private void goBackHome(ActionEvent event) {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("view/EmployeeHome.fxml", "Employee Home");
	}

	public void initialize(URL location, ResourceBundle resources) {

		// CALLS THE PARENT INITIALSE METHOD (FROM 'WhatsOnCustController' CLASS)
		// WHICH LAYS OUT THE PAGE WITH ALL THE FILMS IN GRIDPANES AS FOR THE CUSTOMER
		super.initialize(location, resources);

		// ITERATES THROUGH THE ALL THE GRIDPANES ON THE PAGE AND REMOVES THE 'BOOK' BUTTON
		// ADDS A 'BOOKING INFO' BUTTON FOR EMPLOYEE TO VIEW SEAT BOOKING INFO
		for (int i = 0; i < super.centreAnchor.getChildren().size(); i++) {

			GridPane grid = (GridPane) super.centreAnchor.getChildren().get(i);
			grid.getChildren().remove(7);

			bookingInfo = new Button("Booking Info");
			bookingInfo.setId(filmIDs.get(i));
			
			
			bookingInfo.setOnAction(super.buttonHandler);
			bookingInfo.setPrefSize(120, 20);

			grid.add(bookingInfo, 4, 4, 1, 1);
		}

	}

	
}