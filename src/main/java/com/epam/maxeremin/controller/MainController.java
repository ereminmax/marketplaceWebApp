package com.epam.maxeremin.controller;

import com.epam.maxeremin.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;

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

        userDAO.add(login, password, fullName, billingAddress);
    }

    public boolean isHigher(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession(true).getAttribute("user");
        double bidAmount = Double.parseDouble(request.getParameter("bidAmount"));
        int itemId = Integer.parseInt(request.getParameter("itemId"));

        Item item = itemDAO.findItemById(itemId);
        Bid maxBid = bidDAO.getMaxBid(itemId);

        if (maxBid.getBid() >= bidAmount || (bidAmount - maxBid.getBid() < item.getBidIncrement()) || user.getId() == item.getSeller() || item.isBuyItNow() == 1 || bidAmount <= item.getStartPrice()) {
            return false;
        }

        return true;
    }

    public void setMaxBid(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession(true).getAttribute("user");
        double bidAmount = Double.parseDouble(request.getParameter("bidAmount"));
        int itemId = Integer.parseInt(request.getParameter("itemId"));
        Bid bid = new Bid(0, user.getId(), itemId, bidAmount);

        bidDAO.add(bid);
    }

    public boolean isOwner(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession(true).getAttribute("user");
        int itemId = Integer.parseInt(request.getParameter("itemEditId"));
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
        int itemId = Integer.parseInt(request.getParameter("itemEditId"));
        Item item = itemDAO.findItemById(itemId);

        if (item == null) {
            return false;
        }

        return true;
    }

    public void updateItem(HttpServletRequest request, HttpServletResponse response) {

        int itemId = Integer.parseInt(request.getParameter("itemEditId"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int startPrice = Integer.parseInt(request.getParameter("startPrice"));
        int timeLeft = Integer.parseInt(request.getParameter("timeLeft"));
        double bidIncrement = Double.parseDouble(request.getParameter("bidIncrement"));
        int buyItNow = Integer.parseInt(request.getParameter("buyItNow"));
        Date startDate = Date.valueOf(request.getParameter("startDate"));

        User user = (User) request.getSession(true).getAttribute("user");
        Item item;

        if (buyItNow == 1) {
            item = new Item(itemId, user.getId(), title, description, startPrice, null, startDate, 1, null);
        } else {
            item = new Item(itemId, user.getId(), title, description, startPrice, timeLeft, startDate, 0, bidIncrement);
        }

        itemDAO.edit(item);
    }

    public void addItem(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession(true).getAttribute("user");

        int itemId = Integer.parseInt(request.getParameter("itemEditId"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int startPrice = Integer.parseInt(request.getParameter("startPrice"));
        int timeLeft = Integer.parseInt(request.getParameter("timeLeft"));
        double bidIncrement = Double.parseDouble(request.getParameter("bidIncrement"));
        int buyItNow = Integer.parseInt(request.getParameter("buyItNow"));
        Date startDate = Date.valueOf(request.getParameter("startDate"));

        Item item;

        if (buyItNow == 1) {
            item = new Item(itemId, user.getId(), title, description, startPrice, null, startDate, 1, null);
        } else {
            item = new Item(itemId, user.getId(), title, description, startPrice, timeLeft, startDate, 0, bidIncrement);
        }

        itemDAO.add(item);
    }
}
