package cinema.control;

import java.net.URL;
import java.util.ResourceBundle;

import cinema.CinemaMain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class WhatsOnEmpController extends WhatsOnCustController {

	// TAKES USER BACK TO 'Cinema Login' PAGE WHEN 'LOG OUT' MENU ITEM CLICKED
	@FXML
	private void goBackHome(ActionEvent event) {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("view/EmployeeHome.fxml", "Employee Home");
	}

	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

		for (int i = 0; i < super.centreAnchor.getChildren().size(); i++) {

			GridPane grid = (GridPane) super.centreAnchor.getChildren().get(i);
			grid.getChildren().remove(6);

			Button bookingInfo = new Button("Booking Info");
			bookingInfo.setOnAction(buttonHandler);
			bookingInfo.setPrefSize(120, 20);

			grid.add(bookingInfo, 4, 3, 1, 1);
		}
	}

	// EVENTHANDLER FOR THE BOOKNG INFO BUTTON - WILL TAKE YOU TO THE BOOKING
	// INFO PAGE
	final static public EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(final ActionEvent event) {

		}
	};
}
