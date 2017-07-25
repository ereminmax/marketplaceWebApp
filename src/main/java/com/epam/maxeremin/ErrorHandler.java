package com.epam.maxeremin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorHandler extends HttpServlet {

    private static final String EXCEPTION_PROP = "javax.servlet.error.exception";
    private static final String STATUS_CODE_PROP = "javax.servlet.error.status_code";
    private static final String REQUEST_URI_PROP = "javax.servlet.error.request_uri";

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        final Throwable throwable = (Throwable) request.getAttribute(EXCEPTION_PROP);
        final Integer statusCode = (Integer) request.getAttribute(STATUS_CODE_PROP);
        final String requestUri = (String) request.getAttribute(REQUEST_URI_PROP);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Error Information</title></head>");
        out.println("<body bgcolor='#f0f0f0'>");
        if (throwable == null) {
            out.println("<h2>Error information is missing</h2>");
            out.println("Please return to the Home Page");
        } else {
            out.println("The status code : " + statusCode + "</br>");
            out.println("Message : " + throwable.getMessage() + "<br>");
            out.println("The request URI: " + requestUri);
        }
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
