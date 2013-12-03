package edu.ucla.cs.cs144;

import java.util.Date;

public class BidBean {
	private UserBean bidder;
	private Date time;
	private String amount;
	
	public BidBean() {}

	public UserBean getBidder() {
		return bidder;
	}

	public void setBidder(UserBean bidder) {
		this.bidder = bidder;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	

}
