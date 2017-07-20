package com.epam.maxeremin.servlets;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 19-Jul-17
 */
import com.epam.maxeremin.controller.MainController;
import com.epam.maxeremin.model.ItemTable;
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

@WebServlet(name = "myItems", urlPatterns = "/showMyItems")
public class MyItems extends HttpServlet {

    MainController controller = MainController.getInstance();

    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        HttpSession session = httpServletRequest.getSession(true);
        User user = (User) session.getAttribute("user");

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
                "<th>Id</th>\n<th>Title</th>\n<th>Description</th>\n<th>Seller</th><th>Start Price</th><th>Bid increment</th><th>Max Bid</th><th>Bidder</th><th>Stop Date</th>\n</tr>\n");

        ArrayList<ItemTable> itemTables = controller.getReadableItemList();

        for (ItemTable item : itemTables) {
            // todo вынуть айдишник продавца
            int sellerId = Integer.parseInt(item.getSeller());
            if (sellerId == user.getId()) {
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

                out.println("</tr>");
            }
        }

        out.println("</table><form action=\"logout\" method=\"post\">\n" +
                "<button type=\"submit\" name=\"logout\">Logout</button>\n" +
                "</form></body>");
    }
}
