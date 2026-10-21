package com.shopsphere.controller;

import com.shopsphere.dao.ProductDAO;
import com.shopsphere.dao.ReviewDAO;
import com.shopsphere.model.Product;
import com.shopsphere.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@WebServlet("/product-details")
public class ProductDetailsServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        try{
            int id=Integer.parseInt(request.getParameter("id"));
            try(Connection c=DatabaseConnection.open()){
                Product product=new ProductDAO(c).findById(id).filter(Product::isStatus).orElse(null);
                if(product==null){response.sendError(HttpServletResponse.SC_NOT_FOUND);return;}
                request.setAttribute("product",product);request.setAttribute("reviews",new ReviewDAO(c).findByProduct(id));
                LinkedHashSet<Integer> recentIds=new LinkedHashSet<>();
                Cookie[] cookies=request.getCookies();
                if(cookies!=null)for(Cookie cookie:cookies)if("recentlyViewed".equals(cookie.getName())){
                    for(String value:cookie.getValue().split("[-_,]"))try{int recentId=Integer.parseInt(value.trim());if(recentId>0&&recentId!=id)recentIds.add(recentId);}catch(NumberFormatException ignored){}
                }
                recentIds.add(id);while(recentIds.size()>5)recentIds.remove(recentIds.iterator().next());
                String cookieVal = recentIds.stream().map(String::valueOf).collect(java.util.stream.Collectors.joining("-"));
                Cookie recentCookie=new Cookie("recentlyViewed", cookieVal);
                recentCookie.setPath(request.getContextPath().isEmpty()?"/":request.getContextPath());recentCookie.setMaxAge(30*24*60*60);recentCookie.setHttpOnly(true);recentCookie.setSecure(request.isSecure());response.addCookie(recentCookie);
                List<Product> recentlyViewed=new ArrayList<>();for(int recentId:recentIds){Product recent=new ProductDAO(c).findById(recentId).orElse(null);if(recent!=null&&recent.isStatus())recentlyViewed.add(recent);}
                request.setAttribute("recentlyViewed",recentlyViewed);
                request.getRequestDispatcher("/product-details.jsp").forward(request,response);
            }
        }catch(NumberFormatException ex){response.sendError(HttpServletResponse.SC_NOT_FOUND);}
        catch(Exception ex){throw new ServletException("Product details could not be loaded.",ex);}
    }
}
