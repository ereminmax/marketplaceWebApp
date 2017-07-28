package com.epam.maxeremin.dao;

import com.epam.maxeremin.model.User;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 28-Jul-17
 */
public class UserDAOTest {

    private DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("oracle.jdbc.OracleDriver");
        config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521/xe");
        config.setUsername("maxim");
        config.setPassword("root");

        DataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Before
    public void setUp() throws Exception {
        DataSource testDataSource = dataSource();
        OracleDAO.setDataSource(testDataSource);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void add() throws Exception {

        User testUser = new User(0, "testFullName1", "testBillingAddress1", "testLogin1", "testPassword1");
        IUserDAO dao = DAOFactory.getInstance().getUserDAO();
        int sizeBeforeInsert = getAllUsers().size();
        dao.add(testUser);

        ArrayList<User> arrayAfterInsert = getAllUsers();

        assertEquals("check if the amount of users increased", sizeBeforeInsert + 1, arrayAfterInsert.size());

    }

    @Test
    public void getUserById() throws Exception {
        IUserDAO dao = DAOFactory.getInstance().getUserDAO();
        int testId = 1;

        User userFromDAO = dao.getUserById(testId);

        User userFromMethod = null;
        ArrayList<User> allUsers = getAllUsers();
        for (User user:
             allUsers) {
            if (user.getId() == testId) {
                userFromMethod = user;
            }
        }

        boolean isEqual = userFromDAO.equals(userFromMethod);

        assertTrue(isEqual);
    }

    @Test
    public void isRegistered() throws Exception {
        IUserDAO dao = DAOFactory.getInstance().getUserDAO();
        String testLogin1 = "login1";
        String testLogin2 = "qwerty";

        boolean isReg1 = dao.isRegistered(testLogin1);
        boolean isReg2 = dao.isRegistered(testLogin2);

        assertTrue(isReg1);
        assertFalse(isReg2);
    }

    @Test
    public void findUserByLogin() throws Exception {

        IUserDAO dao = DAOFactory.getInstance().getUserDAO();
        String testLogin = "login1";

        User userFromDAO = dao.findUserByLogin(testLogin);

        ArrayList<User> userList = getAllUsers();
        User userFromMethod = null;
        for (User user:
                userList) {
            if (user.getLogin().equals(testLogin)) {
                userFromMethod = user;
            }
        }

        boolean isEqual = userFromDAO.equals(userFromMethod);
        assertTrue(isEqual);

    }

    @Test
    public void getLoggedInUser() throws Exception {

        IUserDAO dao = DAOFactory.getInstance().getUserDAO();
        String testLogin = "login1";
        String testPassword = "password";

        User userFromDAO = dao.getLoggedInUser(testLogin, testPassword);

        ArrayList<User> userList = getAllUsers();
        User userFromMethod = null;
        for (User user:
             userList) {
            if (user.getLogin().equals(testLogin) && user.getPassword().equals(testPassword)) {
                userFromMethod = user;
            }
        }

        boolean isEqual = userFromDAO.equals(userFromMethod);
        assertTrue(isEqual);

    }

    private ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();

        try (Connection con = OracleDAO.getDataSource().getConnection();
             PreparedStatement ps = printStatement(con);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return users;
    }

    private PreparedStatement printStatement(Connection con) throws SQLException {
        String sql = "SELECT * FROM maxim.Users";
        PreparedStatement ps = con.prepareStatement(sql);
        return ps;
    }

}