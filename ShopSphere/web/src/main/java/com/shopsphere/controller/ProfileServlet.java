package com.shopsphere.controller;

import com.shopsphere.dao.AddressDAO;
import com.shopsphere.model.Address;
import com.shopsphere.model.User;
import com.shopsphere.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.Set;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession(false).getAttribute("user");
        try (Connection connection = DatabaseConnection.open()) {
            request.setAttribute("addresses", new AddressDAO(connection).findByUser(user.getUserId()));
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
        } catch (Exception ex) { throw new ServletException("Profile could not be loaded.", ex); }
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        User user = (User) request.getSession(false).getAttribute("user");
        String line = request.getParameter("addressLine");
        String city = request.getParameter("city"); String state = request.getParameter("state");
        String pincode = request.getParameter("pincode"); String type = request.getParameter("addressType");
        if (line == null || line.isBlank() || city == null || city.isBlank() || state == null || state.isBlank()
                || pincode == null || pincode.isBlank() || !Set.of("HOME", "WORK", "OTHER").contains(type)) {
            request.setAttribute("error", "Complete all address fields with a valid address type.");
            doGet(request, response); return;
        }
        Address address = new Address(); address.setUserId(user.getUserId());
        address.setAddressLine(line.trim()); address.setCity(city.trim()); address.setState(state.trim());
        address.setPincode(pincode.trim()); address.setAddressType(type);
        try (Connection connection = DatabaseConnection.open()) {
            new AddressDAO(connection).create(address);
            response.sendRedirect(request.getContextPath() + "/profile?addressAdded=1");
        } catch (Exception ex) { throw new ServletException("Address could not be saved.", ex); }
    }
}
