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
import com.epam.maxeremin.model.Item;
import com.epam.maxeremin.model.User;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

public class EditController implements IGTVGController {


    public EditController() {
        super();
    }


    public void process(
            final HttpServletRequest request, final HttpServletResponse response,
            final ServletContext servletContext, final ITemplateEngine templateEngine)
            throws Exception {



        if (request.getParameter("title") != null) {
            if (isItemExist(request, response)) {
                updateItem(request, response);
            } else {
                addItem(request, response);
            }

            response.sendRedirect("/");
        } else {
            final Integer prodId = Integer.valueOf(request.getParameter("id"));

            Item item = DAOFactory.getInstance().getItemDAO().findItemById(prodId);

            final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
            ctx.setVariable("item", item);

            templateEngine.process("edit", ctx, response.getWriter());
        }


    }

    private boolean isItemExist(HttpServletRequest request, HttpServletResponse response) {
        int itemId = Integer.parseInt(request.getParameter("id"));
        Item item = DAOFactory.getInstance().getItemDAO().findItemById(itemId);

        if (item == null) {
            return false;
        }

        return true;
    }

    private void updateItem(HttpServletRequest request, HttpServletResponse response) {

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

        DAOFactory.getInstance().getItemDAO().edit(item);
    }

    private void addItem(HttpServletRequest request, HttpServletResponse response) {

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

        DAOFactory.getInstance().getItemDAO().add(item);
    }

}
