package com.shopsphere.controller;

import com.shopsphere.dao.ReviewDAO;
import com.shopsphere.model.Review;
import com.shopsphere.model.User;
import com.shopsphere.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/reviews/add")
public class ReviewServlet extends HttpServlet {
    @Override protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        if (request.getSession(false) == null || request.getSession(false).getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User user = (User) request.getSession(false).getAttribute("user");
        int productId;
        try {
            productId = Integer.parseInt(request.getParameter("productId"));
            int rating = Integer.parseInt(request.getParameter("rating"));
            String text=request.getParameter("reviewText");if(rating<1||rating>5)throw new IllegalArgumentException();
            try(Connection c=DatabaseConnection.open()){
                ReviewDAO dao=new ReviewDAO(c);if(!dao.productExists(productId))throw new IllegalArgumentException();
                Review review=new Review();review.setUserId(user.getUserId());review.setProductId(productId);review.setRating(rating);review.setReviewText(text);dao.create(review);
            }
            response.sendRedirect(request.getContextPath()+"/product-details?id="+productId+"&review=added");
        }catch(IllegalArgumentException ex){response.sendRedirect(request.getContextPath()+"/products?error=review");}
        catch(Exception ex){throw new ServletException("Review could not be submitted.",ex);}
    }
}
