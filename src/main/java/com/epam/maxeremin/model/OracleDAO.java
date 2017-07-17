package com.epam.maxeremin.model;

import javax.naming.*;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 14-Jul-17
 */
public class OracleDAO {
    private static DataSource dataSource;
    private static OracleDAO ourInstance = new OracleDAO();

    public static OracleDAO getInstance() {
        return ourInstance;
    }
    public static Map toMap(Context ctx) throws NamingException {
        String namespace = ctx instanceof InitialContext ? ctx.getNameInNamespace() : "";
        HashMap<String, Object> map = new HashMap<String, Object>();
        NamingEnumeration<NameClassPair> list = ctx.list(namespace);
        while (list.hasMoreElements()) {
            NameClassPair next = list.next();
            String name = next.getName();
            String jndiPath = namespace + name;
            Object lookup;
            try {
                Object tmp = ctx.lookup(jndiPath);
                if (tmp instanceof Context) {
                    lookup = toMap((Context) tmp);
                } else {
                    lookup = tmp.toString();
                }
            } catch (Throwable t) {
                lookup = t.getMessage();
            }
            map.put(name, lookup);

        }
        return map;
    }
    private OracleDAO() {
        try {
            Context context = new InitialContext();
            System.out.println(toMap(context));
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/mpdb");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
