package cinema.control;

import java.net.URL;
import java.util.ResourceBundle;

import cinema.CinemaMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class WhatsOnEmpController extends WhatsOnCustController {
	
		

	// TAKES USER BACK TO 'Cinema Login' PAGE WHEN 'LOG OUT' MENU ITEM CLICKED
		@FXML
		private void goBackHome(ActionEvent event) {
			CinemaMain main = new CinemaMain();
			main.goToNextPage("view/EmployeeHome.fxml", "Employee Home");
		}
	
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		
		book.setVisible(false);
		
		
	}
}
