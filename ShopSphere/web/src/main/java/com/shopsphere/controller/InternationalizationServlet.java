package com.shopsphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet("/syllabus/i18n")
public class InternationalizationServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        Locale locale="hi".equalsIgnoreCase(request.getParameter("lang"))?Locale.forLanguageTag("hi-IN"):Locale.US;
        ResourceBundle messages=ResourceBundle.getBundle("messages",locale);
        request.setAttribute("welcome",messages.getString("welcome"));request.setAttribute("dateLabel",messages.getString("date"));
        request.setAttribute("formattedPrice",NumberFormat.getCurrencyInstance(locale).format(1299.50));
        request.setAttribute("formattedDate",LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(java.time.format.FormatStyle.LONG).withLocale(locale)));
        request.getRequestDispatcher("/syllabus/i18n.jsp").forward(request,response);
    }
}
