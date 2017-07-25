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

public class LoginController implements IGTVGController {


    public LoginController() {
        super();
    }


    public void process(
            final HttpServletRequest request, final HttpServletResponse response,
            final ServletContext servletContext, final ITemplateEngine templateEngine)
            throws Exception {

        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        User user = login(request, response);

        if (user != null) {
            ((HttpServletRequest)request).getSession().setAttribute("user", user);
            response.sendRedirect("/");
        }

        templateEngine.process("login", ctx, response.getWriter());

    }

    private User login(HttpServletRequest request, HttpServletResponse response) {
        String login = "";
        String password = "";

        if (request.getParameter("login") != null && request.getParameter("password") != null) {
            login = request.getParameter("login");
            password = request.getParameter("password");
        }

        return DAOFactory.getInstance().getUserDAO().getLoggedInUser(login, password);
    }

}