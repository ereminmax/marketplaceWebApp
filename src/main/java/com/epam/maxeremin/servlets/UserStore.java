package com.epam.maxeremin.servlets;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 19-Jul-17
 */
import com.epam.maxeremin.controller.MainController;
import com.epam.maxeremin.model.ItemTable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "userStore", urlPatterns = "/userStore")
public class UserStore extends HttpServlet {

    MainController controller = MainController.getInstance();

    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletResponse.setContentType("text/html");
        PrintWriter out = httpServletResponse.getWriter();
        // Header of the table
        out.println("<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<title></title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>All items</h1>\n" +
                "<table border=1><tr>\n" +
                "<th>Id</th>\n<th>Title</th>\n<th>Description</th>\n<th>Seller</th><th>Start Price</th><th>Bid increment</th><th>Max Bid</th><th>Bidder</th><th>Stop Date</th><th>Bidding Form</th>\n</tr>\n");

        ArrayList<ItemTable> itemTables = controller.getReadableItemList();

        for (ItemTable item: itemTables) {
            out.println("<tr>");

            out.println("<td>" + item.getId() + "</td>");
            out.println("<td>" + item.getTitle() + "</td>");
            out.println("<td>" + item.getDescription() + "</td>");
            out.println("<td>" + item.getSeller() + "</td>");
            out.println("<td>" + item.getStartPrice() + "</td>");
            out.println("<td>" + item.getBidIncrement() + "</td>");
            out.println("<td>" + item.getBid() + "</td>");
            out.println("<td>" + item.getBuyer() + "</td>");
            out.println("<td>" + item.getStopDate() + "</td>");

            out.println("<td><form method=\"post\" action=\"bid\">\n<label>Your Bid</label>\n" +
                    "    <input name=\"bidAmount\" type=\"text\" id=\"bidAmount\">\n" +
                    "    <input type=\"hidden\" name=\"itemId\" value=\""+ item.getId() + "\">" +
                    "    <button type=\"submit\">Make bid</button>\n" +
                    "</form></td>");

            out.println("</tr>");
        }

        out.println("</table><form action=\"logout\" method=\"post\">\n" +
                "  <button type=\"submit\" name=\"logout\">Logout</button>\n" +
                "</form></body>");
    }
}