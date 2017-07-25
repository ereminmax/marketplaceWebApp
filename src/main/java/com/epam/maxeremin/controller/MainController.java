package com.epam.maxeremin.controller;

import com.epam.maxeremin.dao.*;
import com.epam.maxeremin.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        String login = "";
        String password = "";

        if (request.getParameter("login") != null && request.getParameter("password") != null) {
            login = request.getParameter("login");
            password = request.getParameter("password");
        }

        return userDAO.getLoggedInUser(login, password);
    }

    public void register(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String billingAddress = request.getParameter("billingAddress");

        User user = new User(0, fullName, billingAddress, login, password);

        userDAO.add(user);
    }

    public boolean isHigher(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession(true).getAttribute("user");
        double bidAmount = Double.parseDouble(request.getParameter("bidAmount"));
        int itemId = Integer.parseInt(request.getParameter("id"));

        Item item = itemDAO.findItemById(itemId);
        Bid maxBid = bidDAO.getMaxBid(itemId);

        if (maxBid == null && (user.getId() == item.getSeller() || bidAmount < item.getStartPrice())) {
            return false;
        } else if (maxBid.getBid() >= bidAmount || (bidAmount - maxBid.getBid() < item.getBidIncrement()) || user.getId() == item.getSeller() || bidAmount <= item.getStartPrice()) {
            return false;
        }

        return true;
    }

    public void setMaxBid(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession(true).getAttribute("user");
        double bidAmount = Double.parseDouble(request.getParameter("bidAmount"));
        int itemId = Integer.parseInt(request.getParameter("id"));
        Bid bid = new Bid(0, user.getId(), itemId, bidAmount);

        bidDAO.add(bid);
    }

    public boolean isOwner(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession(true).getAttribute("user");
        String idParameter = request.getParameter("id");
        int itemId = Integer.parseInt(idParameter);
        Item item = itemDAO.findItemById(itemId);
        if (item == null) {
            return false;
        }
        if (user.getId() == item.getSeller()) {
            return true;
        }

        return false;
    }

    public boolean isItemExist(HttpServletRequest request, HttpServletResponse response) {
        int itemId = Integer.parseInt(request.getParameter("id"));
        Item item = itemDAO.findItemById(itemId);

        if (item == null) {
            return false;
        }

        return true;
    }

    public void updateItem(HttpServletRequest request, HttpServletResponse response) {

        User user = (User) request.getSession(true).getAttribute("user");
        Item item;

        int itemId = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int startPrice = Integer.parseInt(request.getParameter("startPrice"));

        int timeLeft = 0;
        double bidIncrement = 0;
        if (request.getParameter("timeLeft") != "" || request.getParameter("bidIncrement") != "") {
            timeLeft = Integer.parseInt(request.getParameter("timeLeft"));
            bidIncrement = Double.parseDouble(request.getParameter("bidIncrement"));
        }

        String startDate = request.getParameter("startDate");


        boolean buyItNow = false;
        if (request.getParameter("buyItNow") != null) {
            buyItNow = true;
        }

        if (buyItNow) {
            item = new Item(itemId, user.getId(), title, description, startPrice, timeLeft, startDate, 1, bidIncrement);
        } else {
            item = new Item(itemId, user.getId(), title, description, startPrice, timeLeft, startDate, 0, bidIncrement);
        }

        itemDAO.edit(item);
    }

    public void addItem(HttpServletRequest request, HttpServletResponse response) {

        User user = (User) request.getSession(true).getAttribute("user");
        Item item;

        int itemId = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int startPrice = Integer.parseInt(request.getParameter("startPrice"));

        int timeLeft = 0;
        double bidIncrement = 0;
        if (request.getParameter("timeLeft") != "" || request.getParameter("bidIncrement") != "") {
            timeLeft = Integer.parseInt(request.getParameter("timeLeft"));
            bidIncrement = Double.parseDouble(request.getParameter("bidIncrement"));
        }

        String startDate = request.getParameter("startDate");


        boolean buyItNow = false;
        if (request.getParameter("buyItNow") != null) {
            buyItNow = true;
        }

        if (buyItNow) {
            item = new Item(itemId, user.getId(), title, description, startPrice, timeLeft, startDate, 1, bidIncrement);
        } else {
            item = new Item(itemId, user.getId(), title, description, startPrice, timeLeft, startDate, 0, bidIncrement);
        }

        itemDAO.add(item);
    }
}
