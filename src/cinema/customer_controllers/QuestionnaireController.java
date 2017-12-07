package cinema.customer_controllers;

import cinema.CinemaMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Alert.AlertType;

public class QuestionnaireController {

	@FXML
	private RadioButton radio1_1, radio1_2, radio1_3, radio1_4, radio2_1, radio2_2, radio2_3, radio2_4, radio3_1,
			radio3_2, radio3_3, radio3_4;

	public static int count = 0;


	@FXML
	public void submit(ActionEvent event) {

		Button btn = (Button) event.getSource();

		if (btn.getId().equalsIgnoreCase("submit1")) {
			if (radio1_2.isSelected()) {
				count++;
				correctAnswer();
			} else 
				wrongAnswer();
			btn.setDisable(true);
		}
		

		if (btn.getId().equalsIgnoreCase("submit2")) {
			if (radio2_4.isSelected()) {
				count++;
				correctAnswer();
			}
			else
				wrongAnswer();
			btn.setDisable(true);
		}

		if (btn.getId().equalsIgnoreCase("submit3")) {
			if (radio3_1.isSelected()) {
				count++;
				correctAnswer();
			}
			else
				wrongAnswer();
			btn.setDisable(true);
			
		}

		
	}

	public void correctAnswer() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Questionnaire");
		alert.setHeaderText("Correct Answer!");
		alert.setContentText("That's one free ticket to a film! Go to your account to see your credit");
		alert.showAndWait();
	}
	
	public void wrongAnswer() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Questionnaire");
		alert.setHeaderText("Ah Sorry Wrong Answer!");
		alert.setContentText("No free ticket : ( ");
		alert.showAndWait();
	}

	@FXML
	public void goesBackHome() {
		CinemaMain main = new CinemaMain();
		main.goToNextPage("customer_view/CustomerHome.fxml", "Customer Home");
	}
}
