package pl.rpieja.ethminestats;

import javafx.application.Platform;

/**
 * Created by radix on 15.06.2017.
 */
public class ExceptionListener{
	private MainWindowController mainWindowController;

	ExceptionListener(MainWindowController mainWindowController){
		this.mainWindowController=mainWindowController;
	}

	void handleBadWalletAdress(){
		Platform.runLater(() -> mainWindowController.snackbar.show("", 2000));
	}

	void handleRequestError(){
		Platform.runLater(() -> mainWindowController.snackbar.show("Invalid wallet adress", 3000));
	}

	void handleJSONParsingError(){
		Platform.runLater(() -> mainWindowController.snackbar.show("Error while parsing JSON", 2000));
	}
}
