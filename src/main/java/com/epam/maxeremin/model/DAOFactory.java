package com.epam.maxeremin.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 14-Jul-17
 */
public class DAOFactory {
    private static DataSource dataSource;

    public synchronized static Connection createConnection() throws SQLException, NamingException {
        if(dataSource == null) {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/mpdb");
        }

        Connection conn = dataSource.getConnection();
        return conn;
    }
}
