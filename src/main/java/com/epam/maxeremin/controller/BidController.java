package com.epam.maxeremin.controller;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 24-Jul-17
 */
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.maxeremin.dao.DAOFactory;
import com.epam.maxeremin.model.Bid;
import com.epam.maxeremin.model.Item;
import com.epam.maxeremin.model.User;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;


public class BidController implements IGTVGController {


    public BidController() {
        super();
    }


    public void process(
            final HttpServletRequest request, final HttpServletResponse response,
            final ServletContext servletContext, final ITemplateEngine templateEngine)
            throws Exception {

        if (request.getParameter("bidAmount") != null) {
            if (isHigher(request, response)) {
                setMaxBid(request, response);
            }
            response.sendRedirect("/store");
        } else {
            final Integer itemId = Integer.valueOf(request.getParameter("id"));

            final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
            ctx.setVariable("id", itemId);

            templateEngine.process("bid", ctx, response.getWriter());
        }

    }

    private boolean isHigher(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession(true).getAttribute("user");
        double bidAmount = Double.parseDouble(request.getParameter("bidAmount"));
        int itemId = Integer.parseInt(request.getParameter("id"));

        Item item = DAOFactory.getInstance().getItemDAO().findItemById(itemId);
        Bid maxBid = DAOFactory.getInstance().getBidDAO().getMaxBid(itemId);

        if (user.getId() == item.getSeller() || bidAmount < item.getStartPrice() || (bidAmount - item.getStartPrice()) < item.getBidIncrement()) {
            return false;
        } else if (maxBid == null) {
            return true;
        } else if (//maxBid.getBid() >= bidAmount ||
                (bidAmount - maxBid.getBid()) < item.getBidIncrement()) {
            return false;
        }

        return true;
    }

    private void setMaxBid(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession(true).getAttribute("user");
        double bidAmount = Double.parseDouble(request.getParameter("bidAmount"));
        int itemId = Integer.parseInt(request.getParameter("id"));
        Bid bid = new Bid(0, user.getId(), itemId, bidAmount);

        DAOFactory.getInstance().getBidDAO().add(bid);
    }
}