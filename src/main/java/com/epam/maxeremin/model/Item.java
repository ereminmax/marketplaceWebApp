package com.epam.maxeremin.model;

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
    private String startBiddingDate;
    private int buyItNow;
    private double bidIncrement;

    public Item() {
    }

    public Item(int id, int seller, String title, String description, int startPrice, int timeLeft, String startBiddingDate, int buyItNow, double bidIncrement) {
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

    public String getStartBiddingDate() {
        return startBiddingDate;
    }

    public void setStartBiddingDate(String startBiddingDate) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (id != item.id) return false;
        if (seller != item.seller) return false;
        if (startPrice != item.startPrice) return false;
        if (timeLeft != item.timeLeft) return false;
        if (buyItNow != item.buyItNow) return false;
        if (Double.compare(item.bidIncrement, bidIncrement) != 0) return false;
        if (!title.equals(item.title)) return false;
        if (description != null ? !description.equals(item.description) : item.description != null) return false;
        return startBiddingDate.equals(item.startBiddingDate);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + seller;
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + startPrice;
        result = 31 * result + timeLeft;
        result = 31 * result + startBiddingDate.hashCode();
        result = 31 * result + buyItNow;
        temp = Double.doubleToLongBits(bidIncrement);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
