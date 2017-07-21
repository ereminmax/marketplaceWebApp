package com.epam.maxeremin;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 11-Jul-17
 */
import com.epam.maxeremin.controller.MainController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Main", urlPatterns = {"/showAllItems", "/login", "/register", "/logout", "/edit", "/bid"})
public class Main extends HttpServlet {
    private MainController controller = MainController.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(true);

        switch (requestURI) {
            case "/register": {
                if (!controller.isRegistered(request, response)) {
                    controller.register(request, response);
                    response.sendRedirect("/login.html");
                }
                break;
            }
            case "/logout": {
                session.removeAttribute("user");
                response.sendRedirect( "login.html");
                break;
            }
            case "/bid": {
                if (controller.isHigher(request, response)) {
                    controller.setMaxBid(request, response);
                }
                break;
            }
            case "/edit": {
                /*if (!controller.isOwner(request, response)) {
                    break;
                }*/

                if (controller.isItemExist(request, response)) {
                    controller.updateItem(request, response);
                } else {
                    controller.addItem(request, response);
                }

                response.sendRedirect("index.html");
                break;
            }
        }
    }
}