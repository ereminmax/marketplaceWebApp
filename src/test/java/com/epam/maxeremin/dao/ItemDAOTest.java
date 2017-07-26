package com.epam.maxeremin.dao;

import com.epam.maxeremin.model.Item;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 26-Jul-17
 */
public class ItemDAOTest {

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

        Item testItem = new Item(0, 1, "testItem1", "testDesk1", 200, 3600000, "26-jul-2017", 0, 3);
        IItemDAO dao = DAOFactory.getInstance().getItemDAO();
        int sizeBeforeInsert = dao.getAll().size();
        dao.add(testItem);

        ArrayList<Item> arrayAfterInsert = dao.getAll();
        assertEquals("check if the amount of items increased", sizeBeforeInsert + 1, arrayAfterInsert.size());
    }

    @Test
    public void edit() throws Exception {
        Item testItem = new Item(26, 1, "updatedtestItem1", "testDesk1", 200, 3600000, "26-jul-2017", 0, 3);
        IItemDAO dao = DAOFactory.getInstance().getItemDAO();
        int sizeBeforeInsert = dao.getAll().size();
        dao.edit(testItem);

        ArrayList<Item> arrayAfterEdit = dao.getAll();
        Item editedItem = null;
        for (Item item:
             arrayAfterEdit) {
            if (item.getTitle().equals("updatedtestItem1")) {
                editedItem = item;
                return;
            }
        }

        assertNotNull("check if the edited file with specified name exists", editedItem);
    }

    @Test
    public void getAll() throws Exception {
        IItemDAO dao = DAOFactory.getInstance().getItemDAO();
        ArrayList<Item> listFromTheDAO = dao.getAll();


        ArrayList<Item> items = new ArrayList<Item>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yy");
        try (Connection con = OracleDAO.getDataSource().getConnection();
             PreparedStatement ps = printStatement(con);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                items.add(new Item(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        simpleDateFormat.format(new Date(rs.getDate(7).getTime())),
                        rs.getInt(8),
                        rs.getDouble(9)));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        listFromTheDAO.retainAll(items);

        for (Item item:
             items) {
            System.out.println(item.getTitle());
        }
        System.out.println("=======================");
        for (Item item:
                listFromTheDAO) {
            System.out.println(item.getTitle());
        }

        boolean isEmpty = listFromTheDAO.isEmpty();

        //assertTrue("checks if results from the data layer are the same as returned from the actual method", isModified);
        assertTrue(isEmpty);
    }

    private PreparedStatement printStatement(Connection con) throws SQLException {
        String sql = "SELECT * FROM maxim.Items";
        PreparedStatement ps = con.prepareStatement(sql);
        return ps;
    }

    @Test
    public void search() throws Exception {
        IItemDAO dao = DAOFactory.getInstance().getItemDAO();

        ArrayList<Item> resultsFromDAO = dao.search("title");
        ArrayList<Item> allResults = dao.getAll();
        ArrayList<Item> copyAllResults = new ArrayList<>(allResults);
        ArrayList<Item> basket = new ArrayList<>();

        copyAllResults.removeAll(resultsFromDAO);
        basket.addAll(resultsFromDAO);
        basket.addAll(copyAllResults);
        allResults.removeAll(basket);

        boolean isEmpty = allResults.isEmpty();

        assertTrue(isEmpty);
    }

    @Test
    public void findItemById() throws Exception {
        IItemDAO dao = DAOFactory.getInstance().getItemDAO();
        ArrayList<Item> itemListFromDAO = dao.getAll();

        int id = 8;

        Item itemFromDAO = dao.findItemById(id);
        Item itemFromMethod = null;

        for (Item item:
             itemListFromDAO) {
            if (item.getId() == id) {
                itemFromMethod = item;
            }
        }

        boolean isEqual = itemFromDAO.equals(itemFromMethod);

        assertTrue(isEqual);
    }

}