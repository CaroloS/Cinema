package cinema.customer_controllers;

import java.net.URL;
import java.util.ResourceBundle;

import cinema.CinemaMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class CustomerHomeController implements Initializable {

	@FXML
	private AnchorPane videoAnchor;
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	    String content_Url = "<iframe width=\"560\" height=\"315\" src=\"http://www.youtube.com/embed/9bZkp7q19f0\" frameborder=\"0\" allowfullscreen></iframe>";

	    WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.loadContent(content_Url);
        webView.prefWidthProperty().bind(videoAnchor.widthProperty());
		webView.prefHeightProperty().bind(videoAnchor.heightProperty());
		
        videoAnchor.getChildren().add(webView);
	}
	

	
	
	
////////NAVIGATION FUNCTIONS///////
	@FXML
	private void loadfilms(ActionEvent event) {
		//TAKES YOU TO 'WhatsOn' PAGE WHEN 'ALL FILM' BUTTON OR 'WHATS ON' MENU ITEM PRESSED
		CinemaMain main = new CinemaMain();
		main.goToNextPage("customer_view/WhatsOnCustomer.fxml", "What's On");
	}
	
	@FXML
	private void logsOut(ActionEvent event) {
		//TAKES YOU TO BACK TO LOGIN SCREEN
		CinemaMain main = new CinemaMain();
		main.goToNextPage("shared_view/LoginScreen.fxml", "Cinema Login");
	}
	
	@FXML 
	private void goToMyAccount() {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("customer_view/CustomerAccount.fxml", "My Account");
	}

}
