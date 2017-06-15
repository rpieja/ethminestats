package pl.rpieja.ethminestats;

import javafx.application.Platform;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by radix on 15.06.2017.
 */
public class DataSetChangeListener {
	private DataSet dataSet;
	private MainWindowController mainWindowController;

	DataSetChangeListener(DataSet dataSet, MainWindowController mainWindowController){
		this.dataSet=dataSet;
		this.mainWindowController=mainWindowController;

	}

	void handleAvgHashrateChange(Double newValue){
		dataSet.setAvgRate(newValue);
		Platform.runLater(() -> mainWindowController.avgHashrateLabel.setText(String.format("%.2f", newValue)));
	}
	void handleCurrHashrateChange(Double newValue){
		dataSet.setCurrRate(newValue);
		Platform.runLater(() -> mainWindowController.curHashrateLabel.setText(String.format("%.2f", newValue)));
	}
	void handleUnpaidBalanceChange(BigDecimal newValue){
		Double finalBalance = newValue.divide(new BigDecimal(1.e18),5 ,BigDecimal.ROUND_HALF_UP).doubleValue();
		dataSet.setUnpaidBalance(finalBalance);
		Platform.runLater(() -> {
			mainWindowController.unpaidBalanceLabel.setText(String.format("%.5f", finalBalance));
		});
		System.out.println("Unpaid balance (formatted): "+String.format("%.5f", finalBalance));
	}
	void handleShareCountChange(Integer newValue){
		dataSet.setShares(newValue);
		Platform.runLater(() -> mainWindowController.acceptedSharesLabel.setText(String.valueOf(newValue)));
	}
}
