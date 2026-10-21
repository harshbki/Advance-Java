package com.shopsphere.controller;

import com.shopsphere.dao.UserDAO;
import com.shopsphere.model.User;
import com.shopsphere.service.AuthService;
import com.shopsphere.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/admin/login")
public class AdminLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && "ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }
        request.setAttribute("adminMode", true);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        char[] password = Optional.ofNullable(request.getParameter("password")).orElse("").toCharArray();
        try (Connection connection = DatabaseConnection.open()) {
            Optional<User> result = new AuthService(new UserDAO(connection))
                    .login(request.getParameter("email"), password);
            if (result.isEmpty()) {
                request.setAttribute("adminMode", true);
                request.setAttribute("error", "Invalid admin email or password.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
            User user = result.get();
            if (!"ADMIN".equals(user.getRole())) {
                request.setAttribute("adminMode", true);
                request.setAttribute("error", "Access denied. Administrator privileges required.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
            user.setPassword(null);
            HttpSession session = request.getSession(true);
            request.changeSessionId();
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } catch (SQLException | IllegalStateException ex) {
            throw new ServletException("Admin login could not be completed.", ex);
        } finally {
            java.util.Arrays.fill(password, '\0');
        }
    }
}
