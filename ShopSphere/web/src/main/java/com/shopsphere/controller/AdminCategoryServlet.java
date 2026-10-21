package com.shopsphere.controller;

import com.shopsphere.dao.CategoryDAO;
import com.shopsphere.model.Category;
import com.shopsphere.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/admin/categories")
public class AdminCategoryServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        try(Connection c=DatabaseConnection.open()){CategoryDAO dao=new CategoryDAO(c);request.setAttribute("categories",dao.findAll());String id=request.getParameter("id");if(id!=null)try{request.setAttribute("editCategory",dao.findById(Integer.parseInt(id)).orElse(null));}catch(NumberFormatException ignored){}request.getRequestDispatcher("/admin/categories.jsp").forward(request,response);}
        catch(Exception ex){throw new ServletException("Categories could not be loaded.",ex);}
    }
    @Override protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        try(Connection c=DatabaseConnection.open()){
            CategoryDAO dao=new CategoryDAO(c);String action=request.getParameter("action");
            if("delete".equals(action))dao.delete(Integer.parseInt(request.getParameter("categoryId")));
            else{Category category=new Category();category.setName(request.getParameter("name").trim());category.setDescription(request.getParameter("description"));category.setStatus("ACTIVE".equals(request.getParameter("status")));
                if("update".equals(action)){category.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));dao.update(category);}else dao.create(category);}
            response.sendRedirect(request.getContextPath()+"/admin/categories?saved=1");
        }catch(RuntimeException ex){request.setAttribute("error","Check the category fields and try again.");doGet(request,response);}
        catch(Exception ex){throw new ServletException("Category could not be saved.",ex);}
    }
}
