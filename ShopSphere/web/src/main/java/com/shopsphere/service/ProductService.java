package com.shopsphere.service;

import com.shopsphere.dao.ProductDAO;
import com.shopsphere.model.Product;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final ProductDAO productDAO;
    public ProductService(ProductDAO productDAO) { this.productDAO = productDAO; }

    public List<Product> browseActiveProducts() throws SQLException {
        return productDAO.findActive();
    }

    public List<Product> searchProducts(String text, Integer categoryId, String sort) throws SQLException {
        return productDAO.search(text, categoryId, sort);
    }

    public Optional<Product> findProduct(int productId) throws SQLException {
        if (productId <= 0) throw new IllegalArgumentException("Product id must be positive.");
        return productDAO.findById(productId);
    }

    public int createProduct(Product product) throws SQLException {
        validate(product);
        return productDAO.create(product);
    }

    public boolean updateProduct(Product product) throws SQLException {
        if (product.getProductId() <= 0) throw new IllegalArgumentException("Product id must be positive.");
        validate(product);
        return productDAO.update(product);
    }

    private void validate(Product product) {
        if (product == null || product.getName() == null || product.getName().isBlank())
            throw new IllegalArgumentException("Product name is required.");
        if (product.getPrice() == null || product.getPrice().signum() < 0)
            throw new IllegalArgumentException("Product price must be zero or more.");
        if (product.getDiscount() == null || product.getDiscount().signum() < 0
                || product.getDiscount().compareTo(new java.math.BigDecimal("100")) > 0)
            throw new IllegalArgumentException("Discount must be between 0 and 100.");
        if (product.getStock() < 0) throw new IllegalArgumentException("Stock cannot be negative.");
    }
}
