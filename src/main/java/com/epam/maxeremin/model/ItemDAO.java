package com.epam.maxeremin.model;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 13-Jul-17
 */
public class ItemDAO implements IItemDAO {

    // Dependency injection isn't supported in Tomcat 7 @Resource(name = "jdbc/mpdb")
    private OracleDAO oracleDAO;
    private DAOFactory daoFactory = new DAOFactory();

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
        try (//Connection con = OracleDAO.getInstance().getDataSource().getConnection();
             Connection con = DAOFactory.createConnection();
             PreparedStatement ps = printStatement(con);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                items.add(new Item(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getDate(7), rs.getBoolean(8), rs.getDouble(9)));
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        return items;

        /*System.out.println("-------- Oracle JDBC Connection Testing ------");

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            //return null;

        }

        System.out.println("Oracle JDBC Driver Registered!");

        ArrayList<Item> items = new ArrayList<Item>();
        Connection connection = null;

        try {

            String sql = "SELECT * FROM Items";
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "maxim", "root");
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                items.add(new Item(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getDate(7), rs.getBoolean(8), rs.getDouble(9)));
            }

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            //return null;

        }

        return items;*/
    }

    private PreparedStatement printStatement(Connection con) throws SQLException {
        String sql = "SELECT * FROM maxim.Items";
        PreparedStatement ps = con.prepareStatement(sql);
        return ps;
    }
}
