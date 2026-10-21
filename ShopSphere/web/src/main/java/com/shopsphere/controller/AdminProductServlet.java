package com.shopsphere.controller;

import com.shopsphere.dao.CategoryDAO;
import com.shopsphere.dao.ProductDAO;
import com.shopsphere.model.Product;
import com.shopsphere.service.ProductService;
import com.shopsphere.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;

@WebServlet("/admin/products")
public class AdminProductServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        try(Connection c=DatabaseConnection.open()){
            ProductDAO products=new ProductDAO(c);
            String filter = request.getParameter("filter");
            if ("low_stock".equalsIgnoreCase(filter)) {
                request.setAttribute("products", products.findLowStock(5));
                request.setAttribute("isLowStockFilter", true);
            } else {
                request.setAttribute("products", products.findAll());
            }
            request.setAttribute("categories",new CategoryDAO(c).findAll());
            String id=request.getParameter("id");if(id!=null)try{request.setAttribute("editProduct",products.findById(Integer.parseInt(id)).orElse(null));}catch(NumberFormatException ignored){}
            request.getRequestDispatcher("/admin/products.jsp").forward(request,response);
        }catch(Exception ex){throw new ServletException("Product management could not be loaded.",ex);}
    }
    @Override protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        try(Connection c=DatabaseConnection.open()){
            ProductDAO dao=new ProductDAO(c);String action=request.getParameter("action");
            if("delete".equals(action)) dao.delete(Integer.parseInt(request.getParameter("productId")));
            else{
                Product p=new Product();p.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
                p.setName(request.getParameter("name").trim());p.setBrand(request.getParameter("brand"));p.setDescription(request.getParameter("description"));
                p.setPrice(new BigDecimal(request.getParameter("price")));p.setDiscount(new BigDecimal(request.getParameter("discount")));
                p.setStock(Integer.parseInt(request.getParameter("stock")));p.setImageUrl(request.getParameter("imageUrl"));p.setStatus("ACTIVE".equals(request.getParameter("status")));
                ProductService service = new ProductService(dao);
                if("update".equals(action)){p.setProductId(Integer.parseInt(request.getParameter("productId")));service.updateProduct(p);}else service.createProduct(p);
            }
            response.sendRedirect(request.getContextPath()+"/admin/products?saved=1");
        }catch(RuntimeException ex){request.setAttribute("error","Check the product fields and try again.");doGet(request,response);}
        catch(Exception ex){throw new ServletException("Product could not be saved.",ex);}
    }
}
