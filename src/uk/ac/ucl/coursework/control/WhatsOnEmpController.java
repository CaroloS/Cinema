package uk.ac.ucl.coursework.control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class WhatsOnEmpController implements Initializable {

	
	@FXML public AnchorPane centreAnchor;
	
	private VBox container = new VBox();
	private Label hello = new Label();
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		hello.setText("hello");
		container.getChildren().add(hello);
		centreAnchor.getChildren().add(container);
		
	}

}
