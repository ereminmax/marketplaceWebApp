package com.epam.maxeremin;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 11-Jul-17
 */
import com.epam.maxeremin.model.IItemDAO;
import com.epam.maxeremin.model.Item;
import com.epam.maxeremin.model.ItemDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "home", urlPatterns = "/home")
public class Main extends HttpServlet {
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
                    "<h1>Input SQL command below:</h1>\n" +
                    "<table><tr>\n" +
                    "<th>Title</th>\n<th>Description</th>\n<th>Seller</th>\n</tr>\n");

        // Writes the content from the Items table
        ArrayList<Item> items = itemDAO.getAll();
        for (Item item: items) {
            out.println(
                    "<tr>\n<td>" + item.getTitle() +
                    "</td>\n<td>" + item.getDescription() +
                    "</td>\n<td>" + item.getSeller() +
                    "</td>\n</tr>\n"
            );
        }

        out.println("</table></body>");
    }
}