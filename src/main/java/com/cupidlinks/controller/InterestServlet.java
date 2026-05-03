package com.cupidlinks.controller;

import com.cupidlinks.service.InterestService;
import com.cupidlinks.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller for sending interest from one user to another.
 */
@WebServlet("/interest")
public class InterestServlet extends HttpServlet {

    private final InterestService interestService = new InterestService();

    /**
     * Processes a like action and redirects to matches when mutual interest exists.
     *
     * @param request HTTP request containing the receiverId value
     * @param response HTTP response used to redirect after the action
     * @throws ServletException if the servlet container cannot process the request
     * @throws IOException if redirecting fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        int senderId = (int) session.getAttribute("userId");

        String receiverIdStr = ValidationUtil.sanitize(request.getParameter("receiverId"));

        if (ValidationUtil.isEmpty(receiverIdStr)) {
            response.sendRedirect(request.getContextPath() + "/discover");
            return;
        }

        try {
            int receiverId = Integer.parseInt(receiverIdStr);
            boolean matched = interestService.sendLike(senderId, receiverId);

            if (matched) {
                response.sendRedirect(request.getContextPath() + "/matches?newMatch=true");
            } else {
                response.sendRedirect(request.getContextPath() + "/discover?liked=true");
            }

        } catch (SQLException | NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/discover?error=true");
        }
    }
}
