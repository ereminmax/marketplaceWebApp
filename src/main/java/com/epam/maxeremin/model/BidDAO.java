package com.epam.maxeremin.model;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 12-Jul-17
 */
public class BidDAO implements IBidDAO{

    @Resource(name = "jdbc/mpdb")
    private DataSource dataSource;

    @Override
    public void add(Bid bid) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = addStatement(con, bid)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PreparedStatement addStatement(Connection con, Bid bid) throws SQLException {
        String sql = "INSERT INTO Bids (bidder_id, item_id, bid) values (?, ?, ?)";

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
}
