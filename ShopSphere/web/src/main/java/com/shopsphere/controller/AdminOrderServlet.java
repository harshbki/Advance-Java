package com.shopsphere.controller;

import com.shopsphere.dao.OrderDAO;
import com.shopsphere.dao.OrderItemDAO;
import com.shopsphere.service.OrderService;
import com.shopsphere.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/admin/orders")
public class AdminOrderServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        try(Connection c=DatabaseConnection.open()){request.setAttribute("orders",new OrderDAO(c).findAll());request.getRequestDispatcher("/admin/orders.jsp").forward(request,response);}
        catch(Exception ex){throw new ServletException("Orders could not be loaded.",ex);}
    }
    @Override protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        try(Connection c=DatabaseConnection.open()){
            new OrderService(new OrderDAO(c),new OrderItemDAO(c)).updateOrderStatus(Integer.parseInt(request.getParameter("orderId")),request.getParameter("orderStatus"));
            response.sendRedirect(request.getContextPath()+"/admin/orders?saved=1");
        }catch(RuntimeException ex){request.setAttribute("error","Choose a valid order status.");doGet(request,response);}
        catch(Exception ex){throw new ServletException("Order status could not be updated.",ex);}
    }
}
