package com.epam.maxeremin;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 11-Jul-17
 */
import com.epam.maxeremin.controller.MainController;
import com.epam.maxeremin.model.IItemDAO;
import com.epam.maxeremin.model.Item;
import com.epam.maxeremin.model.ItemDAO;
import com.epam.maxeremin.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "home", urlPatterns = {"/home", "/loginPage", "/registerPage", "/login", "/register", "/search", "/logout"})
public class Main extends HttpServlet {
    MainController controller = MainController.getInstance();

    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        String requestURI = httpServletRequest.getRequestURI();

        switch (requestURI) {
            case "/home": {
                httpServletResponse.sendRedirect("/guestStore");
                break;
            }
            case "/loginPage": {
                httpServletResponse.sendRedirect("login.html");
                break;
            }
            case "/registerPage": {
                httpServletResponse.sendRedirect( "register.html");
                break;
            }
            case "/search": {
                httpServletRequest.getRequestDispatcher("/searchResults").forward(httpServletRequest, httpServletResponse);
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(true);

        switch (requestURI) {
            case "/login": {
                User user = null;
                // if registered then get object of this user and set it to the session attribute
                if (request.getAttribute("user") == null) {
                    user = controller.login(request, response);
                }
                if (user == null) {
                    // todo сообщение что пара логин пароль не совпадают либо пользователь уже вошел в систему
                } else {
                    session.setAttribute("user", user);
                }
                break;
            }
            case "/register": {
                if (controller.isRegistered(request, response)) {
                    // todo сообщение что данный логин зарегистрирован
                } else {
                    controller.register(request, response);
                    response.sendRedirect("/login.html");
                }
                break;
            }
            case "/logout": {
                session.removeAttribute("user");
                response.sendRedirect( "login.html");
            }
        }
    }
}