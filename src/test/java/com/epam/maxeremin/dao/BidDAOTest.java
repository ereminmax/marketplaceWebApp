package com.epam.maxeremin.dao;

import com.epam.maxeremin.model.Bid;
import com.sun.xml.internal.bind.v2.util.DataSourceSource;
import oracle.jdbc.OracleDriver;
import org.junit.*;

import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 26-Jul-17
 */
public class BidDAOTest {
    static Connection connection;
    @BeforeClass
    public static void tearUpBeforeClass() throws Exception {

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        connection.close();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void add() throws Exception {
        Bid testBid = new Bid(0, 3, 8, 200);
        IBidDAO dao = DAOFactory.getInstance().getBidDAO();
        dao.add(testBid);


    }

    @Test
    public void getMaxBid() throws Exception {
    }

    @Test
    public void testDemo() {
        int value = 0;
        value += 1;

        assertEquals("check if arithmetic works in java", 1, value);
    }

}