package se.mete.model;


/**
 * Product class representing a product in the supermarket.
 */
public class Product {
    // Fields corresponding to the columns in the Products table
    private int productID;
    private String productName;
    private double price;
    private String category;


    // Constructor
    public Product(int productID, String productName, double price, String category) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.category = category;
    }


    // Getters and setters
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    
    // toString method for easy printing
    @Override
    public String toString() {
        return "Product [productID=" + productID + ", productName=" + productName + ", price=" + price + ", category=" + category + "]";
    }
}