package pl.rpieja.ethminestats;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by radix on 15.06.2017.
 */
public class MainWindowController implements Initializable {

	DataSet dataSet = new DataSet();
	DataSetChangeListener dataSetChangeListener;
	ExceptionListener exceptionListener;
	JSONReader reader;

	public void setExceptionListener(ExceptionListener exceptionListener) {
		this.exceptionListener = exceptionListener;
	}

	public void setDataSetChangeListener(DataSetChangeListener dataSetChangeListener){
		this.dataSetChangeListener=dataSetChangeListener;
	}

	public void setDataSet(DataSet dataSet){
		this.dataSet=dataSet;
	}

	@FXML
	Label avgHashrateLabel;
	@FXML
	Label curHashrateLabel;
	@FXML
	JFXTextField walletAdressTextField;
	@FXML
	Label acceptedSharesLabel;
	@FXML
	JFXButton saveButton;
	@FXML
	Label unpaidBalanceLabel;
	@FXML
	BorderPane rootPane;

	JFXSnackbar snackbar;

	@FXML
	void handleSaveButtonClick(ActionEvent event) {
		try {
			reader = new JSONReader(new URL("https://ethermine.org/api/miner_new/"+walletAdressTextField.getText()), dataSet, dataSetChangeListener, exceptionListener);
			new Thread(reader).start();
		}catch (MalformedURLException e){
			exceptionListener.handleBadWalletAdress();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		snackbar = new JFXSnackbar(rootPane);
		snackbar.setPrefWidth(350);
	}
}
