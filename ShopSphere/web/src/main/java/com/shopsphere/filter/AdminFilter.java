package com.shopsphere.filter;

import com.shopsphere.model.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/admin/*")
public class AdminFilter implements Filter {
    @Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Object user = httpRequest.getSession(false) == null ? null
                : httpRequest.getSession(false).getAttribute("user");
        String uri = httpRequest.getRequestURI();
        if (uri.endsWith("/admin/login")) {
            chain.doFilter(request, response);
            return;
        }
        if (user instanceof User && "ADMIN".equals(((User) user).getRole())) {
            chain.doFilter(request, response);
        } else if (user instanceof User) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }
}
