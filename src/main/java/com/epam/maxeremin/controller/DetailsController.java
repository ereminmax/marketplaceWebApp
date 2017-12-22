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

public class DetailsController implements IGTVGController{

    public DetailsController() {
        super();
    }

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext, ITemplateEngine templateEngine) throws Exception {
        final Integer prodId = Integer.valueOf(request.getParameter("id"));

        Item item = DAOFactory.getInstance().getItemDAO().findItemById(prodId);
        Bid maxBid = DAOFactory.getInstance().getBidDAO().getMaxBid(item.getId());
        User seller = DAOFactory.getInstance().getUserDAO().getUserById(item.getSeller());

        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        if (maxBid != null) {
            int maxBidder = maxBid.getBidder();
            User bidder = DAOFactory.getInstance().getUserDAO().getUserById(maxBidder);
            ctx.setVariable("prod", new ItemTable(seller, bidder, item, Double.toString(maxBid.getBid())));
        } else {
            ctx.setVariable("prod", new ItemTable(seller, seller, item, ""));
        }

        templateEngine.process("details", ctx, response.getWriter());
    }
}
