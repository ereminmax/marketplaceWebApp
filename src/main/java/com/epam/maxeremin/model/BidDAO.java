package com.epam.maxeremin.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 12-Jul-17
 */
public class BidDAO implements IBidDAO{

    @Override
    public void add(Bid bid) {
        try (Connection con = OracleDAO.getDataSource().getConnection();
             PreparedStatement ps = addStatement(con, bid)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PreparedStatement addStatement(Connection con, Bid bid) throws SQLException {
        String sql = "INSERT INTO maxim.Bids (bidder_id, item_id, bid) values (?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, bid.getBidder());
        ps.setInt(2, bid.getItem());
        ps.setDouble(3, bid.getBid());

        return ps;
    }

    @Override
    public void edit(Bid bid) {

    }

    @Override
    public void delete(Bid bid) {

    }

    @Override
    public Bid getMaxBid(int itemId) {
        Bid bid = null;
        try (Connection con = OracleDAO.getDataSource().getConnection();
             PreparedStatement ps = searchByItemId(con, itemId);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) bid = new Bid(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bid;
    }

    private PreparedStatement searchByItemId(Connection con, int itemId) throws SQLException {
        String sql = "SELECT bids.bid_id, bids.bidder_id, bids.item_id, bids.bid FROM maxim.bids WHERE bids.item_id = ? AND bids.bid IN (SELECT max(bid) FROM maxim.bids WHERE bids.item_id = ?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, itemId);
        ps.setInt(2, itemId);
        return ps;
    }
}
