package com.epam.maxeremin.model;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 12-Jul-17
 */
public class Bid {
    private int id;
    private int bidder;
    private int item;
    private double bid;

    public Bid() {
    }

    public Bid(int id, int bidder, int item, double bid) {
        this.id = id;
        this.bidder = bidder;
        this.item = item;
        this.bid = bid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBidder() {
        return bidder;
    }

    public void setBidder(int bidder) {
        this.bidder = bidder;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bid bid1 = (Bid) o;

        if (id != bid1.id) return false;
        if (bidder != bid1.bidder) return false;
        if (item != bid1.item) return false;
        return Double.compare(bid1.bid, bid) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + bidder;
        result = 31 * result + item;
        temp = Double.doubleToLongBits(bid);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
