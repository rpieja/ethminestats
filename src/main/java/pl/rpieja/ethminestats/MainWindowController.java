package pl.rpieja.ethminestats;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by radix on 15.06.2017.
 */
public class MainWindowController implements Initializable {

	private String fileName = ".config";
	private Properties prop = new Properties();

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
	@FXML
	Tab statsTab;
	@FXML
	Tab chartTab;
	@FXML
	Tab settingsTab;

	JFXSnackbar snackbar;

	@FXML
	JFXTabPane mainTabPane;

	void start(String adress){
		try {
			reader = new JSONReader(new URL("https://ethermine.org/api/miner_new/"+adress), dataSet, dataSetChangeListener, exceptionListener);
			new Thread(reader).start();
		}catch (MalformedURLException e){
			exceptionListener.handleBadWalletAdress();
		}
	}

	@FXML
	void handleSaveButtonClick(ActionEvent event) {
		if( !( new File( fileName ).isFile() ) )
			createConfigAndRun();
		else
			readConfigAndRun();
	}

	private void readConfigAndRun(){
		InputStream input = null;
		try
		{
			File file = new File( fileName );
			input = new FileInputStream( fileName );

			if( file.isFile() )
			{
				prop.load( input );

				if( prop.containsKey( "walletAdress" ) )
				{
					String walletAdress = prop.getProperty("walletAdress");
					walletAdressTextField.setText(walletAdress);
					start(walletAdress);
				}
			}
		}
		catch( IOException ex ) {ex.printStackTrace();}
		finally
		{
			if( input != null )
			{
				try {input.close();}
				catch( IOException e ) {e.printStackTrace();}
			}
		}
	}

	private void createConfigAndRun() {
		OutputStream output = null;
		try {
			output = new FileOutputStream( fileName );

			String walletAdress = walletAdressTextField.getText();

			prop.setProperty("walletAdress", walletAdress);
			prop.store( output, null );
			start(walletAdress);
		}
		catch ( IOException io ) {io.printStackTrace();}
		finally {
			if ( output != null ) {
				try {output.close();}
				catch ( IOException e ) {e.printStackTrace();}
			}
		}
	}




	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if( !( new File( fileName ).isFile() ) )
			mainTabPane.getSelectionModel().select(2);
		else
			readConfigAndRun();
		snackbar = new JFXSnackbar(rootPane);
		snackbar.setPrefWidth(350);
	}
}
