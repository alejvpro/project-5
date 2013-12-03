package edu.ucla.cs.cs144;

import java.util.Date;

public class ItemBean {
	private String itemId;
	private String name;
	private String[] categories;
	private String currently;
	private String firstBid;
	private String numberOfBids;
	private BidBean[] bids;
	private String location;
	private String country;
	private Date started;
	private Date ends;
	private UserBean seller;
	private String description;
	
	public ItemBean() {}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getCategories() {
		return categories;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	public String getCurrently() {
		return currently;
	}

	public void setCurrently(String currently) {
		this.currently = currently;
	}

	public String getFirstBid() {
		return firstBid;
	}

	public void setFirstBid(String firstBid) {
		this.firstBid = firstBid;
	}

	public String getNumberOfBids() {
		return numberOfBids;
	}

	public void setNumberOfBids(String numberOfBids) {
		this.numberOfBids = numberOfBids;
	}

	public BidBean[] getBids() {
		return bids;
	}

	public void setBids(BidBean[] bids) {
		this.bids = bids;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getStarted() {
		return started;
	}

	public void setStarted(Date started) {
		this.started = started;
	}

	public Date getEnds() {
		return ends;
	}

	public void setEnds(Date ends) {
		this.ends = ends;
	}

	public UserBean getSeller() {
		return seller;
	}

	public void setSeller(UserBean seller) {
		this.seller = seller;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
