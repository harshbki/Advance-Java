package com.shopsphere.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@WebListener
public class AppListener implements ServletContextListener {
    @Override public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().setAttribute("appName", "ShopSphere");
        try {
            DataSource dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/ShopSphere");
            event.getServletContext().setAttribute("shopSphereDataSource", dataSource);
        } catch (NamingException ignored) {
            // The dedicated JNDI/JSTL SQL demonstration stays unavailable until Tomcat is configured.
        }
    }
}
