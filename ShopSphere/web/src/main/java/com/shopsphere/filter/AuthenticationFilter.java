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

@WebFilter(urlPatterns = {
    "/cart", "/cart/*",
    "/checkout", "/checkout/*",
    "/orders", "/orders/*",
    "/profile", "/profile/*",
    "/wishlist", "/wishlist/*",
    "/reviews", "/reviews/*"
})
public class AuthenticationFilter implements Filter {
    @Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Object user = httpRequest.getSession(false) == null ? null
                : httpRequest.getSession(false).getAttribute("user");
        if (user instanceof User) chain.doFilter(request, response);
        else httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
    }
}
