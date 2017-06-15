package pl.rpieja.ethminestats;

/**
 * Created by radix on 15.06.2017.
 */
class DataSet {
	private Integer shares;
	private Double avgRate, currRate, unpaidBalance;

	DataSet(){
		shares=0;
		avgRate=0.0;
		currRate=0.0;
		unpaidBalance=0.0;
	}

	void setShares(Integer shares) {
		this.shares = shares;
	}

	void setAvgRate(Double avgRate) {
		this.avgRate = avgRate;
	}

	void setCurrRate(Double currRate) {
		this.currRate = currRate;
	}

	void setUnpaidBalance(Double unpaidBalance) {
		this.unpaidBalance = unpaidBalance;
	}
}
