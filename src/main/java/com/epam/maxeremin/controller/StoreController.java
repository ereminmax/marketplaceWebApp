package com.epam.maxeremin.controller;

import com.epam.maxeremin.dao.DAOFactory;
import com.epam.maxeremin.model.Bid;
import com.epam.maxeremin.model.Item;
import com.epam.maxeremin.model.ItemTable;
import com.epam.maxeremin.model.User;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 23-Jul-17
 */
public class StoreController implements IGTVGController {
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext, ITemplateEngine templateEngine) throws Exception {

        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        ArrayList<ItemTable> itemTables = new ArrayList<>();
        ArrayList<Item> items = DAOFactory.getInstance().getItemDAO().getAll();

        for (Item item: items) {
            Bid maxBid = DAOFactory.getInstance().getBidDAO().getMaxBid(item.getId());
            User seller = DAOFactory.getInstance().getUserDAO().getSellerById(item.getSeller());

            if (maxBid != null) {
                int maxBidder = maxBid.getBidder();
                User bidder = DAOFactory.getInstance().getUserDAO().findBestBidder(maxBidder);
                itemTables.add(new ItemTable(seller, bidder, item, Double.toString(maxBid.getBid())));
            } else {
                itemTables.add(new ItemTable(seller, seller, item, ""));
            }
        }

        String keyWord = request.getParameter("keyWord");
        String showMyItems = request.getParameter("showMyItems");

        String empty = "";

        if (keyWord != null) {
            ctx.setVariable("keyWord", keyWord);
        } else {
            ctx.setVariable("keyWord", empty);
        }

        ctx.setVariable("prods", itemTables);
        ctx.setVariable("showMyItems", showMyItems);

        templateEngine.process("store", ctx, response.getWriter());

    }
}
