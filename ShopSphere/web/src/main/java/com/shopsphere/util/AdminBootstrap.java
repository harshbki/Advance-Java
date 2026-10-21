package com.shopsphere.util;

import com.shopsphere.dao.UserDAO;
import com.shopsphere.model.User;
import java.io.Console;
import java.sql.Connection;

/** Run once from an IDE or console to create the first ShopSphere administrator. */
public class AdminBootstrap {
    public static void main(String[] args) throws Exception {
        Console console = System.console();
        if (console == null) throw new IllegalStateException("Run this class from a terminal so the password can be entered privately.");
        String name = console.readLine("Admin name: ");
        String email = console.readLine("Admin email: ");
        char[] password = console.readPassword("Admin password (minimum 8 characters): ");
        try {
            if (password == null || password.length < 8) throw new IllegalArgumentException("Password must be at least 8 characters.");
            try (Connection connection = DatabaseConnection.open()) {
                UserDAO dao = new UserDAO(connection);
                if (dao.findByEmail(email.trim()).isPresent()) throw new IllegalArgumentException("That email already exists; bootstrap only creates a new administrator.");
                User admin = new User(); admin.setName(name.trim()); admin.setEmail(email.trim().toLowerCase());
                admin.setPassword(PasswordUtil.hash(password)); admin.setMobile(null); admin.setRole("ADMIN"); admin.setStatus(true);
                dao.create(admin);
            }
            console.printf("Administrator account created for %s.%n", email);
        } finally {
            java.util.Arrays.fill(password, '\0');
        }
    }
}
