package cinema.control;

import java.net.URL;
import java.util.ResourceBundle;

import cinema.CinemaMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class CustAccountController implements Initializable {

	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	
	
	
	
	
	
///////////NAVIGATION FUNCTIONS////////
	@FXML
	private void goToWhatsOn(ActionEvent event) {
		//TAKES YOU TO 'WhatsOn' PAGE WHEN 'ALL FILM' BUTTON OR 'WHATS ON' MENU ITEM PRESSED
		CinemaMain main = new CinemaMain();
		main.goToNextPage("view/WhatsOn.fxml", "What's On");
	}
	
	@FXML
	private void logsOut(ActionEvent event) {
		//TAKES YOU TO 'WhatsOn' PAGE WHEN 'ALL FILM' BUTTON OR 'WHATS ON' MENU ITEM PRESSED
		CinemaMain main = new CinemaMain();
		main.goToNextPage("view/LoginScreen.fxml", "Cinema Login");
	}
	
	@FXML 
	private void goToHome() {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("view/CustomerHome.fxml", "Customer Home");
	}

}
