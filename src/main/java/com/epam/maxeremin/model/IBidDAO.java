package com.epam.maxeremin.model;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 12-Jul-17
 */
public interface IBidDAO {
    void add(Bid bid);
    void edit(Bid bid);
    void delete(Bid bid);
}
