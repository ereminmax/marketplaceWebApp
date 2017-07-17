package com.epam.maxeremin.model;

import java.util.Date;
/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 12-Jul-17
 */
public class Item {
    private int id;
    private int seller;
    private String title;
    private String description;
    private int startPrice;
    private int timeLeft;
    private Date startBiddingDate;
    private int buyItNow;
    private double bidIncrement;

    public Item() {
    }

    public Item(int id, int seller, String title, String description, int startPrice, int timeLeft, Date startBiddingDate, int buyItNow, double bidIncrement) {
        this.id = id;
        this.seller = seller;
        this.title = title;
        this.description = description;
        this.startPrice = startPrice;
        this.timeLeft = timeLeft;
        this.startBiddingDate = startBiddingDate;
        this.buyItNow = buyItNow;
        this.bidIncrement = bidIncrement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeller() {
        return seller;
    }

    public void setSeller(int seller) {
        this.seller = seller;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public Date getStartBiddingDate() {
        return startBiddingDate;
    }

    public void setStartBiddingDate(Date startBiddingDate) {
        this.startBiddingDate = startBiddingDate;
    }

    public int isBuyItNow() {
        return buyItNow;
    }

    public void setBuyItNow(int buyItNow) {
        this.buyItNow = buyItNow;
    }

    public double getBidIncrement() {
        return bidIncrement;
    }

    public void setBidIncrement(double bidIncrement) {
        this.bidIncrement = bidIncrement;
    }
}
