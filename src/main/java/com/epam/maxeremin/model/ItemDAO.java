package com.epam.maxeremin.model;

import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.sql.DataSource;
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

    }

    @Override
    public void remove(Item item) {

    }

    @Override
    public void edit(Item item) {

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

    private PreparedStatement searchStatement(Connection con, String keyWord) throws SQLException {
        //String sql = "SELECT * FROM maxim.Items WHERE title LIKE '%?%'";
        String sql = "SELECT * FROM maxim.Items WHERE title = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, keyWord);
        return ps;
    }

    private PreparedStatement printStatement(Connection con) throws SQLException {
        String sql = "SELECT * FROM maxim.Items";
        PreparedStatement ps = con.prepareStatement(sql);
        return ps;
    }
}
