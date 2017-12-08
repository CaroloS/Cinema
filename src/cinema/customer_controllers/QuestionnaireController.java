package cinema.customer_controllers;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import org.jdom2.Element;

import cinema.CinemaMain;
import cinema.XML.ReadXMLFile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class QuestionnaireController implements Initializable {

	@FXML
	private Label questionNumber, question;
	@FXML
	private RadioButton optionA, optionB, optionC, optionD;
	@FXML
	private Button nextQuestion;

	// DECALRES THE ROOT ELEMENT TO BE SET BY PARSING 'quiz.xml'
	Element root;
	List list;

	// USES THE INITIALIZE METHOD TO PARSE XML AND LAYOUT THE QUESTIONS CURRENTLY IN
	// THE XML FILE WHEN PAGE IS LOADED
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		

		File xmlFile = new File("quiz.xml");

		if (xmlFile.exists()) {
			 System.out.println("file exists");

			try {
				ReadXMLFile read = new ReadXMLFile("quiz.xml");
				root = read.readsXML();
			} catch (Exception e) {
				CinemaMain.LOGGER.warning("Couldn't parse users.XML");
			}
			list = root.getChildren("Test");
			System.out.println("Question" + root.getChildText("Question"));
			
//			questionNumber.setText("1");
//			question.setText(String.valueOf(root.getChildren("Question")));
		}
		
		

	}
	
//	private void populateQuestion () {
		// GENERATE RANDOM NUMBER FOR Question
//				Random rand = new Random();
//				int n = rand.nextInt(2);
				
//	}

}
