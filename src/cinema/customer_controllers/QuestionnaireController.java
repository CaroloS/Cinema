package cinema.customer_controllers;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import org.jdom2.Attribute;
import org.jdom2.Element;

import cinema.CinemaMain;
import cinema.XML.ReadXMLFile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class QuestionnaireController implements Initializable {
	
	public String questionText;
//	public Attribute testAttribute;

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

			for (int i = 0; i < list.size(); i++) {

				Element node = (Element) list.get(i);

				// SPLITS THE FILMDATES FROM XML AND CREATES AN ARRAYLIST TO
				// PASS TO
				// COMBOBOX
				
				Random rand = new Random();
				int n = rand.nextInt(2);
//				Attribute testAttribute = node.getAttributes();
				questionText = node.getChildText("Question");
				System.out.println("Question: " + questionText);

				// questionNumber.setText("1");
				// question.setText(String.valueOf(root.getChildren("Question")));
			}

		}

	}

	// private void populateQuestion () {
	// GENERATE RANDOM NUMBER FOR Question
	// Random rand = new Random();
	// int n = rand.nextInt(2);

	// }

}
