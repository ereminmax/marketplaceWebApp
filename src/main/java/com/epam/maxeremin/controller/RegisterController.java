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
import com.epam.maxeremin.model.User;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

public class RegisterController implements IGTVGController {


    public RegisterController() {
        super();
    }


    public void process(
            final HttpServletRequest request, final HttpServletResponse response,
            final ServletContext servletContext, final ITemplateEngine templateEngine)
            throws Exception {

        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        if (request.getParameter("login") != null) {
            if (!isRegistered(request, response)) {
                register(request, response);
            }
            response.sendRedirect("/");
        }

        templateEngine.process("register", ctx, response.getWriter());

    }

    private void register(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String billingAddress = request.getParameter("billingAddress");

        User user = new User(0, fullName, billingAddress, login, password);

        DAOFactory.getInstance().getUserDAO().add(user);
    }

    private boolean isRegistered(HttpServletRequest request, HttpServletResponse response) {
        if (DAOFactory.getInstance().getUserDAO().isRegistered(request.getParameter("login"))) {
            return true;
        }
        return false;
    }

}