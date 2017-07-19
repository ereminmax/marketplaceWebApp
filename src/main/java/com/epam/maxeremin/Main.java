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

@WebServlet(name = "Main", urlPatterns = {"/showAllItems", "/login", "/register", "/search", "/logout", "/edit", "/showMyItems"})
public class Main extends HttpServlet {
    private MainController controller = MainController.getInstance();

    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(true);

        switch (requestURI) {
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