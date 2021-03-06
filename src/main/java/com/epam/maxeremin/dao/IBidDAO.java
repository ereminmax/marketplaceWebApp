package com.epam.maxeremin.dao;

import com.epam.maxeremin.model.Bid;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 12-Jul-17
 */
public interface IBidDAO {
    void add(Bid bid);
    void edit(Bid bid);
    void delete(Bid bid);

    Bid getMaxBid(int itemId);
}
