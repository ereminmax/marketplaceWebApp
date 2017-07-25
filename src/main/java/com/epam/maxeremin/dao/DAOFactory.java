package com.epam.maxeremin.dao;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 23-Jul-17
 */
public class DAOFactory {
    private static DAOFactory instance;
    private IItemDAO itemDAO;
    private IBidDAO bidDAO;
    private IUserDAO userDAO;

    private DAOFactory() {
        itemDAO = new ItemDAO();
        bidDAO = new BidDAO();
        userDAO = new UserDAO();
    }

    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    public IItemDAO getItemDAO() {
        return itemDAO;
    }

    public IBidDAO getBidDAO() {
        return bidDAO;
    }

    public IUserDAO getUserDAO() {
        return userDAO;
    }
}
