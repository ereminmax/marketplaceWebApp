package com.epam.maxeremin.model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 13-Jul-17
 */
public class ItemTable {
    private int id;
    private String seller;
    private String buyer;
    private String bidIncrement;
    private String bid;
    private String title;
    private String description;
    private String startPrice;
    private Date stopDate;

    public ItemTable(User seller, User buyer, Item item, String bid) {
        this.id = item.getId();
        this.seller = seller.getFullName();
        this.title = item.getTitle();
        this.description = item.getDescription();
        this.startPrice = Integer.toString(item.getStartPrice());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yy");

        java.util.Date date = null;
        try {
            date = simpleDateFormat.parse(item.getStartBiddingDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (item.isBuyItNow() == 1) {
            this.bid = "";
            this.bidIncrement = "";
            this.buyer = "";
            this.stopDate = new Date(date.getTime());
        } else {
            this.bid = bid;
            this.bidIncrement = Double.toString(item.getBidIncrement());
            this.buyer = buyer.getFullName();
            this.stopDate = new Date(date.getTime() + item.getTimeLeft());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getBidIncrement() {
        return bidIncrement;
    }

    public void setBidIncrement(String bidIncrement) {
        this.bidIncrement = bidIncrement;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
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

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }
}
