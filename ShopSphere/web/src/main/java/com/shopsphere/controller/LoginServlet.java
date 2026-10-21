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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        char[] password = Optional.ofNullable(request.getParameter("password")).orElse("").toCharArray();
        try (Connection connection = DatabaseConnection.open()) {
            Optional<User> result = new AuthService(new UserDAO(connection))
                    .login(request.getParameter("email"), password);
            if (result.isEmpty()) {
                request.setAttribute("error", "Email or password is incorrect.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
            User user = result.get();
            user.setPassword(null);
            HttpSession session = request.getSession(true);
            request.changeSessionId();
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());
            if ("CUSTOMER".equals(user.getRole())) {
                session.setAttribute("cartCount", new com.shopsphere.dao.CartDAO(connection).countItemsByUser(user.getUserId()));
            }
            String destination = "ADMIN".equals(user.getRole()) ? "/admin/dashboard" : "/products";
            response.sendRedirect(request.getContextPath() + destination);
        } catch (SQLException | IllegalStateException ex) {
            throw new ServletException("Login could not be completed.", ex);
        } finally {
            java.util.Arrays.fill(password, '\0');
        }
    }
}
