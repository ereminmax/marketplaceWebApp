package com.epam.maxeremin.model;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 13-Jul-17
 */
public interface IUserDAO {
    void add(String login, String password, String fullName, String billingAddress);
    User getSellerById(int id);
    boolean isRegistered(String login);

    User findBestBidder(int bidderId);

    User getLoggedInUser(String login, String password);
}
