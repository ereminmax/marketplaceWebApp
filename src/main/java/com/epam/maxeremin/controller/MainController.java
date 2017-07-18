package com.epam.maxeremin.controller;

import com.epam.maxeremin.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 17-Jul-17
 */
public class MainController {
    private static MainController ourInstance = new MainController();
    private IItemDAO itemDAO = new ItemDAO();
    private IBidDAO bidDAO = new BidDAO();
    private IUserDAO userDAO = new UserDAO();

    public static MainController getInstance() {
        return ourInstance;
    }

    private MainController() {
    }

    public boolean isRegistered(HttpServletRequest request, HttpServletResponse response) {
        if (userDAO.isRegistered(request.getParameter("login"))) {
            return true;
        }
        return false;
    }

    public ArrayList<ItemTable> getReadableItemList() {
        ArrayList<ItemTable> itemTables = new ArrayList<>();
        ArrayList<Item> items = itemDAO.getAll();

        for (Item item: items) {
            Bid maxBid = bidDAO.getMaxBid(item.getId());
            User seller = userDAO.getSellerById(item.getSeller());

            if (maxBid != null) {
                int maxBidder = maxBid.getBidder();
                User bidder = userDAO.findBestBidder(maxBidder);
                itemTables.add(new ItemTable(seller, bidder, item, Double.toString(maxBid.getBid())));
            } else {
                itemTables.add(new ItemTable(seller, seller, item, ""));
            }
        }

        return itemTables;
    }

    public ArrayList<ItemTable> getReadableResults(String keyWord) {
        ArrayList<ItemTable> itemTables = new ArrayList<>();
        ArrayList<Item> items = itemDAO.search(keyWord);

        for (Item item: items) {
            Bid maxBid = bidDAO.getMaxBid(item.getId());
            User seller = userDAO.getSellerById(item.getSeller());

            if (maxBid != null) {
                int maxBidder = maxBid.getBidder();
                User bidder = userDAO.findBestBidder(maxBidder);
                itemTables.add(new ItemTable(seller, bidder, item, Double.toString(maxBid.getBid())));
            } else {
                itemTables.add(new ItemTable(seller, seller, item, ""));
            }
        }

        return itemTables;
    }

    public User login(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        return userDAO.getLoggedInUser(login, password);
    }

    public void register(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String billingAddress = request.getParameter("billingAddress");

        userDAO.add(login, password, fullName, billingAddress);
    }
}
