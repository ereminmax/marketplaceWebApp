package com.epam.maxeremin.model;

import java.sql.*;
import java.util.ArrayList;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 13-Jul-17
 */
public class ItemDAO implements IItemDAO {

    @Override
    public void add(Item item) {
        try (Connection con = OracleDAO.getDataSource().getConnection();
             PreparedStatement ps = addStatement(con, item)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Item item) {

    }

    @Override
    public void edit(Item item) {
        try (Connection con = OracleDAO.getDataSource().getConnection();
             PreparedStatement ps = updateStatement(con, item)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Item> getAll() {
        ArrayList<Item> items = new ArrayList<Item>();
        try (Connection con = OracleDAO.getDataSource().getConnection();
             PreparedStatement ps = printStatement(con);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                items.add(new Item(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getDate(7), rs.getInt(8), rs.getDouble(9)));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public ArrayList<Item> search(String keyWord) {
        ArrayList<Item> items = new ArrayList<Item>();
        try (Connection con = OracleDAO.getDataSource().getConnection();
             PreparedStatement ps = searchStatement(con, keyWord);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                items.add(new Item(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getDate(7), rs.getInt(8), rs.getDouble(9)));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public Item findItemById(int itemId) {
        Item item = null;
        try (Connection con = OracleDAO.getDataSource().getConnection();
             PreparedStatement ps = searchById(con, itemId);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                item = new Item(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getDate(7), rs.getInt(8), rs.getDouble(9));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    private PreparedStatement searchById(Connection con, int itemId) throws SQLException {
        String sql = "SELECT * FROM maxim.Items WHERE item_id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, itemId);
        return ps;
    }

    private PreparedStatement searchStatement(Connection con, String keyWord) throws SQLException {
        String sql = "SELECT * FROM maxim.Items WHERE title LIKE ?";

        String searchPattern = "%" + keyWord + "%";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, searchPattern);
        return ps;
    }

    private PreparedStatement printStatement(Connection con) throws SQLException {
        String sql = "SELECT * FROM maxim.Items";
        PreparedStatement ps = con.prepareStatement(sql);
        return ps;
    }

    private PreparedStatement addStatement(Connection con, Item item) throws SQLException {
        String sql = "INSERT INTO maxim.Items (seller_id, title, description, start_price, time_left, start_bidding_date, buy_it_now, bid_increment) values (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, item.getSeller());
        ps.setString(2, item.getTitle());
        ps.setString(3, item.getDescription());
        ps.setInt(4, item.getStartPrice());
        ps.setDate(6, item.getStartBiddingDate());
        ps.setDouble(7, item.isBuyItNow());

        if (item.getBidIncrement() == 0 || item.getTimeLeft() == 0) {
            ps.setNull(5, Types.NULL);
            ps.setNull(8, Types.NULL);
        } else {
            ps.setInt(5, item.getTimeLeft());
            ps.setDouble(8, item.getBidIncrement());
        }

        return ps;
    }

    private PreparedStatement updateStatement(Connection con, Item item) throws SQLException {
        String sql = "UPDATE maxim.Items SET seller_id = ?, title = ?, description = ?, start_price = ?, time_left = ?, start_bidding_date = TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS'), buy_it_now = ?, bid_increment = ? WHERE item_id = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, item.getSeller());
        ps.setString(2, item.getTitle());
        ps.setString(3, item.getDescription());
        ps.setInt(4, item.getStartPrice());
        ps.setInt(5, item.getTimeLeft());
        ps.setDate(6, item.getStartBiddingDate());
        ps.setDouble(7, item.isBuyItNow());
        ps.setDouble(8, item.getBidIncrement());

        ps.setInt(9, item.getId());

        return ps;
    }
}
