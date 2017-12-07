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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class CustomerHomeController implements Initializable {

	@FXML
	private AnchorPane carouselAnchor, menuAnchor, homeAnchor;
	@FXML
	private BorderPane borderPane;
	@FXML
	private Button forwardButton, backButton, freeTicket;
	@FXML
	private VBox vBox1, vBox2;

	Element root;
	List list;

	ArrayList<String> imageList = new ArrayList<String>();

	ImageView viewPic;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		String content_Url1 = "<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/19jGdktyGgk\" frameborder=\"0\" gesture=\"media\" allow=\"encrypted-media\" allowfullscreen></iframe>";
		String content_Url2 = "<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/8O6eQBjDWDM\" frameborder=\"0\" gesture=\"media\" allow=\"encrypted-media\" allowfullscreen></iframe>";
		loadVideo(content_Url1, vBox1);
		loadVideo(content_Url2, vBox2);
		

		File xmlFile = new File("film.xml");

		if (xmlFile.exists()) {
			try {
				ReadXMLFile read = new ReadXMLFile("film.xml");
				root = read.readsXML();
			} catch (Exception e) {
				CinemaMain.LOGGER.warning("Couldn't parse film.XML");
			}

			// PARSES XML: ITERATES THROUGH THE 'FILM' LIST, GETS
			// ELEMENT/ATTRIBUTES
			// AND PASSES IT TO FILM VARIABLES
			list = root.getChildren("film");

			for (int i = 0; i < list.size(); i++) {

				Element node = (Element) list.get(i);
				String filmDateTimes = node.getChildText("dateTimes");

				List<String> allDates = new ArrayList<String>();

				for (String element : filmDateTimes.split(",")) {
					String date = element.substring(0, 8);
					allDates.add(date);
				}
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

				if (allDates.size() > 0)
					imageList.add(node.getChildText("image"));
			}
		}

		if (imageList.size() > 0) {
			Image filmPic = new Image(imageList.get(0), 200, 200, false, false);
			viewPic = new ImageView(filmPic);
			viewPic.setId("0");

			carouselAnchor.getChildren().add(viewPic);
			viewPic.fitWidthProperty().bind(carouselAnchor.widthProperty());
			viewPic.fitHeightProperty().bind(carouselAnchor.heightProperty());
			viewPic.setPreserveRatio(true);
		}

	}

	@FXML
	public void scrollsForward() {

		if (imageList.size() > 0) {

			int a = Integer.parseInt(viewPic.getId());

			if (a != imageList.size() - 1) {

				ListIterator<String> it = imageList.listIterator(a);

				if (it.hasNext()) {
					Image filmPic = new Image(imageList.get(a + 1), 200, 200, false, false);
					viewPic = new ImageView(filmPic);
					viewPic.setId(Integer.toString(a + 1));

					carouselAnchor.getChildren().add(viewPic);
					viewPic.fitWidthProperty().bind(carouselAnchor.widthProperty());
					viewPic.fitHeightProperty().bind(carouselAnchor.heightProperty());
					viewPic.setPreserveRatio(true);
				}
			}
		}

	}

	@FXML
	public void scrollsBack() {

		if (imageList.size() > 0) {

			int b = Integer.parseInt(viewPic.getId());

			if (b != 0) {

				ListIterator<String> itBack = imageList.listIterator(b);

				if (itBack.hasPrevious()) {
					Image filmPic = new Image(imageList.get(b - 1), 200, 200, false, false);
					viewPic = new ImageView(filmPic);
					viewPic.setId(Integer.toString(b - 1));

					carouselAnchor.getChildren().add(viewPic);
					viewPic.fitWidthProperty().bind(carouselAnchor.widthProperty());
					viewPic.fitHeightProperty().bind(carouselAnchor.heightProperty());
					viewPic.setPreserveRatio(true);
				}
			}
		}

	}

	
	public void loadVideo(String content, VBox vbox) {

		WebView webView = new WebView();
		WebEngine webEngine = webView.getEngine();
		webEngine.loadContent(content);
		vbox.getChildren().add(webView);

	}
	
	
	

	//////// NAVIGATION FUNCTIONS///////
	@FXML
	private void loadfilms(ActionEvent event) {
		// TAKES YOU TO 'WhatsOn' PAGE WHEN 'ALL FILM' BUTTON OR 'WHATS ON' MENU
		// ITEM PRESSED
		CinemaMain main = new CinemaMain();
		main.goToNextPage("customer_view/WhatsOnCustomer.fxml", "What's On");
	}

	@FXML
	private void logsOut(ActionEvent event) {
		// TAKES YOU TO BACK TO LOGIN SCREEN
		CinemaMain main = new CinemaMain();
		main.goToLoginPage("shared_view/LoginScreen.fxml", "Cinema Login");
	}

	@FXML
	private void goToMyAccount() {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("customer_view/CustomerAccount.fxml", "My Account");
	}
	
	@FXML 
	public void goesToQuestionnaire() {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("customer_view/Questionnaire.fxml", "Questionnaire");
	}

}
