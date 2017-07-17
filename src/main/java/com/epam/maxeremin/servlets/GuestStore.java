package com.epam.maxeremin.servlets;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 17-Jul-17
 */
import com.epam.maxeremin.controller.MainController;
import com.epam.maxeremin.model.IItemDAO;
import com.epam.maxeremin.model.Item;
import com.epam.maxeremin.model.ItemDAO;
import com.epam.maxeremin.model.ItemTable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "guestStore", urlPatterns = "/guestStore")
public class GuestStore extends HttpServlet {

    MainController controller = MainController.getInstance();
    IItemDAO itemDAO = new ItemDAO();

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
                "<table><tr>\n" +
                "<th>Title</th>\n<th>Description</th>\n<th>Seller</th>\n</tr>\n");

        // Writes the content from the Items table
        /*ArrayList<Item> items = itemDAO.getAll();
        for (Item item: items) {
            out.println(
                    "<tr>\n<td>" + item.getTitle() +
                    "</td>\n<td>" + item.getDescription() +
                    "</td>\n<td>" + item.getSeller() +
                    "</td>\n</tr>\n"
            );
        }*/

        ArrayList<ItemTable> itemTables = controller.getReadableItemList();

        for (ItemTable item: itemTables) {
            out.println("<tr>");

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

        out.println("</table></body>");
    }
}