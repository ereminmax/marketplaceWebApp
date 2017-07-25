package com.epam.maxeremin.dao;

import com.epam.maxeremin.model.User;

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
        try (Connection con = OracleDAO.getDataSource().getConnection();
             PreparedStatement ps = addStatement(con, user)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    @Override
    public User findUserByLogin(String login) {
        User user = null;

        try (Connection con = OracleDAO.getDataSource().getConnection();
             PreparedStatement ps = searchByName(con, login);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User getLoggedInUser(String login, String password) {
        User user = null;

        try (Connection con = OracleDAO.getDataSource().getConnection();
             PreparedStatement ps = searchByLoginPassword(con, login, password);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    private PreparedStatement searchByLoginPassword(Connection con, String login, String password) throws SQLException{
        String sql = "SELECT * FROM maxim.Users WHERE login = ? AND password = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, login);
        ps.setString(2, password);
        return ps;
    }

    private PreparedStatement searchStatement(Connection con, String login) throws SQLException {
        String sql = "SELECT * FROM maxim.Users WHERE login = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, login);
        return ps;
    }

    private PreparedStatement searchByName(Connection con, String login) throws SQLException {
        String sql = "SELECT * FROM maxim.Users WHERE full_name = ?";

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

    private PreparedStatement addStatement(Connection con, User user) throws SQLException {
        String sql = "insert into maxim.users (full_name, billing_address, login, password) values (?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, user.getFullName());
        ps.setString(2, user.getBillingAddress());
        ps.setString(3, user.getLogin());
        ps.setString(4, user.getPassword());

        return ps;
    }
}
