package com.shopsphere.model;

public class Category {
    private int categoryId;
    private String name;
    private String description;
    private boolean status;

    public Category() { }
    public Category(int categoryId, String name, String description, boolean status) {
        this.categoryId = categoryId; this.name = name; this.description = description; this.status = status;
    }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int value) { this.categoryId = value; }
    public String getName() { return name; }
    public void setName(String value) { this.name = value; }
    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }
    public boolean isStatus() { return status; }
    public void setStatus(boolean value) { this.status = value; }
}
