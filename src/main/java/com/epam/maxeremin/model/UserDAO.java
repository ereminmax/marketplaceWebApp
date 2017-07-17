package com.epam.maxeremin.model;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 13-Jul-17
 */
public class UserDAO implements IUserDAO {

    @Override
    public void add(User user) {

    }

    @Override
    public User getSellerById(int id) {
        User user = null;

        try (Connection con = OracleDAO.getDataSource().getConnection();
             PreparedStatement ps = searchById(con, id);
             ResultSet rs = ps.executeQuery()) {

             if (rs.next()) user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public boolean isRegistered(String login) {
        try (Connection con = OracleDAO.getDataSource().getConnection();
             PreparedStatement ps = searchStatement(con, login)) {
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public User findBestBidder(int bidderId) {
        User user = null;

        try (Connection con = OracleDAO.getDataSource().getConnection();
             PreparedStatement ps = searchById(con, bidderId);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    private PreparedStatement searchStatement(Connection con, String login) throws SQLException {
        String sql = "SELECT * FROM maxim.Users WHERE login = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, login);
        return ps;
    }

    private PreparedStatement searchById(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM maxim.Users WHERE user_id = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }
}
