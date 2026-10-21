package com.shopsphere.controller;

import com.shopsphere.dao.WishlistDAO;
import com.shopsphere.model.User;
import com.shopsphere.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/wishlist/*")
public class WishlistServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        jakarta.servlet.http.HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User user=(User)session.getAttribute("user");
        try(Connection c=DatabaseConnection.open()){
            request.setAttribute("products",new WishlistDAO(c).findProducts(user.getUserId()));
            request.getRequestDispatcher("/wishlist.jsp").forward(request,response);
        } catch(Exception ex){throw new ServletException("Wishlist could not be loaded.",ex);}
    }
    @Override protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        jakarta.servlet.http.HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User user=(User)session.getAttribute("user");
        try{
            int productId=Integer.parseInt(request.getParameter("productId"));
            try(Connection c=DatabaseConnection.open()){
                WishlistDAO dao=new WishlistDAO(c);
                if("/remove".equals(request.getPathInfo())) dao.remove(user.getUserId(),productId);
                else dao.add(user.getUserId(),productId);
            }
            response.sendRedirect(request.getContextPath()+"/wishlist");
        } catch(Exception ex){throw new ServletException("Wishlist could not be updated.",ex);}
    }
}
