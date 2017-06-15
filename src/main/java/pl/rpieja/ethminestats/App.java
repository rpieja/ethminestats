package pl.rpieja.ethminestats;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by radix on 15.06.2017.
 */
public class App extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		DataSet dataSet=new DataSet();

		FXMLLoader root = new FXMLLoader(this.getClass().getResource("MainWindow.fxml"));
		Parent chatNode = root.load();
		MainWindowController mainWindowController = root.getController();
		DataSetChangeListener dataSetChangeListener = new DataSetChangeListener(dataSet, mainWindowController);
		mainWindowController.setDataSetChangeListener(dataSetChangeListener);
		mainWindowController.setExceptionListener(new ExceptionListener(mainWindowController));
		mainWindowController.setDataSet(dataSet);
		Scene mainScene = new Scene(chatNode);
		mainScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		primaryStage.setScene(mainScene);
		primaryStage.setTitle("Ethereum Mining Stats");
		primaryStage.show();
	}
}
