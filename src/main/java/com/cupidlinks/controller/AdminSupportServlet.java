package com.cupidlinks.controller;

import com.cupidlinks.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/support")
public class AdminSupportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Map<String, String>> tickets = new ArrayList<>();
            String sql = "SELECT * FROM support_tickets ORDER BY submitted_at DESC";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> ticket = new HashMap<>();
                    ticket.put("ticketId", String.valueOf(rs.getInt("ticket_id")));
                    ticket.put("userId", String.valueOf(rs.getInt("user_id")));
                    ticket.put("subject", rs.getString("subject"));
                    ticket.put("message", rs.getString("message"));
                    ticket.put("status", rs.getString("status"));
                    ticket.put("submittedAt", rs.getString("submitted_at"));
                    tickets.add(ticket);
                }
            }
            request.setAttribute("tickets", tickets);
            request.getRequestDispatcher("/WEB-INF/views/admin/support.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Could not load support tickets.");
            request.getRequestDispatcher("/WEB-INF/views/admin/support.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ticketId = request.getParameter("ticketId");
        String status   = request.getParameter("status");
        try {
            String sql = "UPDATE support_tickets SET status = ? WHERE ticket_id = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, status);
                stmt.setInt(2, Integer.parseInt(ticketId));
                stmt.executeUpdate();
            }
            response.sendRedirect(request.getContextPath() + "/admin/support?success=true");
        } catch (SQLException e) {
            response.sendRedirect(request.getContextPath() + "/admin/support?error=true");
        }
    }
}