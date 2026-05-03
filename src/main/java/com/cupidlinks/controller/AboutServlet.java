package com.cupidlinks.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Controller for displaying the about page.
 */
@WebServlet("/about")
public class AboutServlet extends HttpServlet {

    /**
     * Forwards logged-in users to the shared about JSP page.
     *
     * @param request HTTP request from the browser
     * @param response HTTP response used to forward to the JSP
     * @throws ServletException if forwarding to the JSP fails
     * @throws IOException if the response cannot be written
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/shared/about.jsp").forward(request, response);
    }
}
