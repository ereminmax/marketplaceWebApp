/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package com.epam.maxeremin.filters;

import com.epam.maxeremin.controller.HomeController;
import com.epam.maxeremin.controller.LoginController;
import com.epam.maxeremin.dao.DAOFactory;
import org.thymeleaf.ITemplateEngine;
import com.epam.maxeremin.model.User;
import com.epam.maxeremin.application.MarketplaceApp;
import com.epam.maxeremin.controller.IGTVGController;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class GTVGFilter implements Filter {

    
    private ServletContext servletContext;
    private MarketplaceApp application;
    
    
    public GTVGFilter() {
        super();
    }
    
    
    
    private static void addUserToSession(final HttpServletRequest request) {
        // Simulate a real user session by adding a user object
        request.getSession(true).setAttribute("user", new User(1, "fullName1", "billingAddress1", "login1", "password"));
    }




    public void init(final FilterConfig filterConfig) throws ServletException {
        this.servletContext = filterConfig.getServletContext();
        this.application = new MarketplaceApp(this.servletContext);
    }




    public void doFilter(final ServletRequest request, final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException {
        addUserToSession((HttpServletRequest)request);

        if (!process((HttpServletRequest)request, (HttpServletResponse)response)) {

            chain.doFilter(request, response);

        }

    }

    private void redirect(HttpServletRequest request, HttpServletResponse response) {

    }


    public void destroy() {
        // nothing to do
    }

    


    private boolean process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        String requestURI = request.getRequestURI();

        try {

            // This prevents triggering engine executions for resource URLs
            if (request.getRequestURI().startsWith("/css") ||
                    request.getRequestURI().startsWith("/images") ||
                    request.getRequestURI().startsWith("/js")) {
                return false;
            }

            /*if (request.getSession().getAttribute("user") == null && (requestURI.equals("/edit") ||
                    requestURI.equals("/bid") || requestURI.equals("/showMyItems"))) {
                return false;
            }*/


            /*
             * Query controller/URL mapping and obtain the controller
             * that will process the request. If no controller is available,
             * return false and let other filters/servlets process the request.
             */
            IGTVGController controller = this.application.resolveControllerForRequest(request);
            if (controller == null) {
                return false;
            }

            /*
             * Obtain the TemplateEngine instance.
             */
            ITemplateEngine templateEngine = this.application.getTemplateEngine();

            /*
             * Write the response headers
             */
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            if (request.getSession().getAttribute("user") == null && (requestURI.equals("/edit") ||
                    requestURI.equals("/bid") || requestURI.equals("/showMyItems"))) {
                IGTVGController loginController = new LoginController();
                loginController.process(
                        request, response, this.servletContext, templateEngine);
                return true;
            }

            if (request.getSession().getAttribute("user") != null && requestURI.equals("/register")) {
                IGTVGController homeController = new HomeController();
                homeController.process(
                        request, response, this.servletContext, templateEngine);
                return true;
            }

            if (request.getSession().getAttribute("user") != null && requestURI.equals("/logout")) {
                request.getSession().removeAttribute("user");
                IGTVGController homeController = new HomeController();
                homeController.process(
                        request, response, this.servletContext, templateEngine);
                return true;
            }



            /*
             * Execute the controller and process view template,
             * writing the results to the response writer.
             */
            controller.process(
                    request, response, this.servletContext, templateEngine);

            return true;

        } catch (Exception e) {
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (final IOException ignored) {
                // Just ignore this
            }
            throw new ServletException(e);
        }

    }
    
    
    
}
