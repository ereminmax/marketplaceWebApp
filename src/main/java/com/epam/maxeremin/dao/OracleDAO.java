package com.epam.maxeremin.dao;

import javax.naming.*;
import javax.sql.DataSource;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 14-Jul-17
 */
public class OracleDAO {
    private static DataSource dataSource;

    private static void jndiDataSource() {
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/mpdb");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            jndiDataSource();
        }
        return dataSource;
    }

    public static void setDataSource(DataSource dataSource) {
        OracleDAO.dataSource = dataSource;
    }
}
