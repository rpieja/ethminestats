package pl.rpieja.ethminestats;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Created by radix on 15.06.2017.
 */
public class MainWindowController {

	@FXML
	private Label avgHashrateLabel;
	@FXML
	private Label curHashrateLabel;
	@FXML
	private JFXTextField walletAdressTextField;
	@FXML
	private Label acceptedSharesLabel;
	@FXML
	private JFXButton saveButton;
	@FXML
	private Label unpaidBalanceLabel;

	@FXML
	void handleSaveButtonClick(ActionEvent event) {

	}

}
