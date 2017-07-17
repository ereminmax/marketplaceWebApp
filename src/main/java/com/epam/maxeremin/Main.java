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

@WebServlet(name = "home", urlPatterns = {"/home", "/loginPage", "/registerPage", "/login", "/register"})
public class Main extends HttpServlet {
    MainController controller = MainController.getInstance();

    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        String requestURI = httpServletRequest.getRequestURI();

        /*if (requestURI.equals("/home")) {
            httpServletResponse.sendRedirect("/guestStore");
        }*/

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
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(true);

        switch (requestURI) {
            case "/login": {
                // if registered then get object of this user and set it to the session attribute
                if (controller.isRegistered(request, response)) {
                    //User user = controller.login(request, response);
                    //session.setAttribute("user", user);
                }
                break;
            }
            case "/register": {
                if (controller.isRegistered(request, response)) {
                    // todo сообщение что данный логин зарегистрирован
                } else {
                    //controller.register(request, response);
                    response.sendRedirect("/loginPage");
                }
                break;
            }
            case "/search": {

            }
        }
    }
}