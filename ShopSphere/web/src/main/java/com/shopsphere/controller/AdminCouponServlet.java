package com.shopsphere.controller;

import com.shopsphere.dao.CouponDAO;
import com.shopsphere.model.Coupon;
import com.shopsphere.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;

@WebServlet("/admin/coupons")
public class AdminCouponServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        try(Connection c=DatabaseConnection.open()){request.setAttribute("coupons",new CouponDAO(c).findAll());request.getRequestDispatcher("/admin/coupons.jsp").forward(request,response);}
        catch(Exception ex){throw new ServletException("Coupons could not be loaded.",ex);}
    }
    @Override protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        try(Connection c=DatabaseConnection.open()){
            Coupon coupon=new Coupon();coupon.setCode(request.getParameter("code").trim().toUpperCase());
            coupon.setDiscountType(request.getParameter("discountType"));coupon.setDiscountValue(new BigDecimal(request.getParameter("discountValue")));
            coupon.setMinimumOrder(new BigDecimal(request.getParameter("minimumOrder")));
            String maximum=request.getParameter("maximumDiscount");coupon.setMaximumDiscount(maximum==null||maximum.isBlank()?null:new BigDecimal(maximum));
            String expiry=request.getParameter("expiryDate");coupon.setExpiryDate(expiry==null||expiry.isBlank()?null:LocalDateTime.parse(expiry));
            coupon.setStatus("ACTIVE".equals(request.getParameter("status"))?"ACTIVE":"INACTIVE");new CouponDAO(c).create(coupon);
            response.sendRedirect(request.getContextPath()+"/admin/coupons?saved=1");
        }catch(RuntimeException ex){request.setAttribute("error","Check the coupon fields and try again.");doGet(request,response);}
        catch(Exception ex){throw new ServletException("Coupon could not be saved.",ex);}
    }
}
