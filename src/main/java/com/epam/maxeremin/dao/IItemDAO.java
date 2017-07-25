package com.epam.maxeremin.dao;

import com.epam.maxeremin.model.Item;

import java.util.ArrayList;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 13-Jul-17
 */
public interface IItemDAO {
    void add(Item item);
    void remove(Item item);
    void edit(Item item);
    ArrayList<Item> getAll();
    ArrayList<Item> search(String keyWord);
    Item findItemById(int itemId);
}
