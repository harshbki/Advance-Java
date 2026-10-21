package com.shopsphere.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletConfigDemoServlet extends HttpServlet {
    private String demoValue;
    @Override public void init(ServletConfig config)throws ServletException{
        super.init(config);demoValue=config.getInitParameter("demoValue");
    }
    @Override protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        request.setAttribute("servletConfigValue",demoValue);
        request.setAttribute("contextAppName",getServletContext().getAttribute("appName"));
        request.getRequestDispatcher("/syllabus/servlet-config.jsp").forward(request,response);
    }
    @Override public void destroy(){getServletContext().log("ServletConfigDemoServlet is shutting down.");}
}
