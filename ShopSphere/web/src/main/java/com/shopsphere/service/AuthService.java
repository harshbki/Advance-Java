package com.shopsphere.service;

import com.shopsphere.dao.UserDAO;
import com.shopsphere.model.User;
import com.shopsphere.util.PasswordUtil;
import java.sql.SQLException;
import java.util.Optional;

public class AuthService {
    private final UserDAO userDAO;
    public AuthService(UserDAO userDAO) { this.userDAO = userDAO; }

    public int register(String name, String email, String password, String mobile) throws SQLException {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name is required.");
        if (email == null || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"))
            throw new IllegalArgumentException("Enter a valid email address.");
        if (password == null || password.length() < 8)
            throw new IllegalArgumentException("Password must be at least 8 characters.");
        if (userDAO.findByEmail(email.trim()).isPresent())
            throw new IllegalArgumentException("An account with this email already exists.");

        User user = new User();
        user.setName(name.trim()); user.setEmail(email.trim().toLowerCase());
        user.setPassword(PasswordUtil.hash(password.toCharArray()));
        user.setMobile(mobile == null || mobile.isBlank() ? null : mobile.trim());
        user.setRole("CUSTOMER"); user.setStatus(true);
        return userDAO.create(user);
    }

    public Optional<User> login(String email, char[] password) throws SQLException {
        if (email == null || password == null) return Optional.empty();
        Optional<User> match = userDAO.findByEmail(email.trim().toLowerCase());
        if (match.isEmpty()) return Optional.empty();
        User user = match.get();
        if (!user.isStatus() || !PasswordUtil.verify(password, user.getPassword())) return Optional.empty();
        return Optional.of(user);
    }
}
