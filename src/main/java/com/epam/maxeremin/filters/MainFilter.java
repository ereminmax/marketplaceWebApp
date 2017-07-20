package com.epam.maxeremin.filters;

import com.epam.maxeremin.controller.MainController;
import com.epam.maxeremin.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 19-Jul-17
 */
@WebFilter(servletNames = {"Main", "userStore", "guestStore", "myItems"})
public class MainFilter implements Filter{

    private MainController controller;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        controller = MainController.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpSession session = request.getSession(true);
        String requestURI = request.getRequestURI();

        User user = controller.login(request, response);

        if (requestURI.equals("/login")) {
            if (user != null) {
                session.setAttribute("user", user);
                response.sendRedirect("index.html");
            } else {
                // no user in db with such login / password
                response.sendRedirect("login.html");
            }
        } else if (requestURI.equals("/showAllItems")) {
            if (session.getAttribute("user") == null) {
                response.sendRedirect("/guestStore");
            } else {
                response.sendRedirect("/userStore");
            }
        } else {
            // redirect a guest to login page
            if (session.getAttribute("user") == null && (requestURI.equals("/edit") || requestURI.equals("/showMyItems") || requestURI.equals("/userStore"))) {
                response.sendRedirect("login.html");
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
