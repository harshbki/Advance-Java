package com.shopsphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/syllabus/jndi")
public class JndiDemoServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        try{
            DataSource dataSource=(DataSource)new InitialContext().lookup("java:comp/env/jdbc/ShopSphere");
            try(var connection=dataSource.getConnection()){
                request.setAttribute("jndiStatus","JNDI lookup succeeded; database connection is ready ("+connection.getMetaData().getDatabaseProductName()+" "+connection.getMetaData().getDatabaseProductVersion()+").");
            }
        }catch(NamingException ex){request.setAttribute("jndiStatus","Configure jdbc/ShopSphere in Tomcat to run this JNDI demonstration.");}
        catch(Exception ex){request.setAttribute("jndiStatus","JNDI data source was found, but its connection could not be opened.");}
        request.getRequestDispatcher("/syllabus/jndi.jsp").forward(request,response);
    }
}
