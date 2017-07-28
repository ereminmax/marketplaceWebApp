package com.epam.maxeremin.dao;

import com.epam.maxeremin.model.Bid;
import com.sun.xml.internal.bind.v2.util.DataSourceSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import oracle.jdbc.OracleDriver;
import org.junit.*;

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
 * Date: 26-Jul-17
 */
public class BidDAOTest {

    private DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("oracle.jdbc.OracleDriver");
        config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521/xe");
        config.setUsername("maxim");
        config.setPassword("root");

        DataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @BeforeClass
    public static void tearUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
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
        Bid testBid = new Bid(0, 3, 8, 200);
        IBidDAO dao = DAOFactory.getInstance().getBidDAO();

        int sizeBeforeInsert = getAllBids().size();
        dao.add(testBid);
        int sizeAfterInsert = getAllBids().size();

        assertEquals("checks if the size changed", sizeBeforeInsert + 1, sizeAfterInsert);

    }

    @Test
    public void getMaxBid() throws Exception {

        IBidDAO dao = DAOFactory.getInstance().getBidDAO();
        int testItemId = 2;
        Bid bidFromDAO = dao.getMaxBid(testItemId);

        ArrayList<Bid> bidList = getAllBids();
        Bid bidFromMethod = null;
        for (Bid bid:
             bidList) {
            if (bidFromMethod != null) {
                if (bid.getItem() == testItemId && bid.getBid() > bidFromMethod.getBid()) {
                    bidFromMethod = bid;
                }
            } else if (bid.getItem() == testItemId){
                bidFromMethod = bid;
            }
        }

        assertEquals(bidFromDAO, bidFromMethod);
    }

    @Test
    public void testDemo() {
        int value = 0;
        value += 1;

        assertEquals("check if arithmetic works in java", 1, value);
    }

    private ArrayList<Bid> getAllBids() {
        ArrayList<Bid> bids = new ArrayList<>();

        try (Connection con = OracleDAO.getDataSource().getConnection();
             PreparedStatement ps = printStatement(con);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                bids.add(new Bid(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getDouble(4)));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return bids;
    }

    private PreparedStatement printStatement(Connection con) throws SQLException {
        String sql = "SELECT * FROM maxim.Bids";
        PreparedStatement ps = con.prepareStatement(sql);
        return ps;
    }

}