package pl.rpieja.ethminestats;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by radix on 15.06.2017.
 */
public class JSONReader implements Runnable {

	private URL url;
	private DataSetChangeListener dataSetChangeListener;
	private ExceptionListener exceptionListener;

	private HttpURLConnection httpcon;

	JSONReader(URL url, DataSet dataSet, DataSetChangeListener dataSetChangeListener, ExceptionListener exceptionListener) {
		this.url = url;
		this.dataSetChangeListener = dataSetChangeListener;
		this.exceptionListener = exceptionListener;
	}

	private JSONObject getJSON() throws IOException {

		try {
			httpcon = (HttpURLConnection) url.openConnection();
			httpcon.addRequestProperty("User-Agent", "Mozilla/4.76");
			httpcon.addRequestProperty("Accept", "application/json");
		} catch (Exception e) {
			exceptionListener.handleRequestError();
		}


		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpcon.getInputStream()))) {

			StringBuilder everything = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				everything.append(line);
			}
			String s = everything.toString();
			System.out.println(s);
			return new JSONObject(s);
		}
		catch (IOException e){
			exceptionListener.handleRequestError();
			return null;
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				JSONObject data = getJSON();
				if(data==null) throw new NullPointerException();
				JSONObject workers = data.getJSONObject("workers");
				int shares = workers.keySet().stream()
						.mapToInt((key) -> workers.getJSONObject(key).getInt("validShares"))
						.sum();
				double currentHashrate = workers.keySet().stream()
						.map((key) -> workers.getJSONObject(key).getString("hashrate"))
						.map((x) -> x.substring(0, x.length() - 5))
						.mapToDouble(Double::valueOf)
						.sum();
				double averageHashrate = data.getDouble("avgHashrate");
				BigDecimal unpaidBalance = data.getBigDecimal("unpaid");
				dataSetChangeListener.handleAvgHashrateChange(averageHashrate * 10e-7);
				dataSetChangeListener.handleCurrHashrateChange(currentHashrate);
				dataSetChangeListener.handleShareCountChange(shares);
				dataSetChangeListener.handleUnpaidBalanceChange(unpaidBalance);

				System.out.println("Current Hashrate: " + currentHashrate);
				System.out.println("Average Hashrate: " + String.format("%.5f", averageHashrate * 10e-7));
				System.out.println("Valid shares: " + shares);

			} catch (IOException e) {
				exceptionListener.handleJSONParsingError();
				e.printStackTrace();
			}
			catch (NullPointerException e){

			}
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				System.err.println("Interrupted Exception");
			}
		}
	}
}
