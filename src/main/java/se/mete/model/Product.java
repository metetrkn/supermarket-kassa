package se.mete.model;

import jakarta.persistence.*;

/**
 * Product class representing a product in the supermarket.
 */
@Entity
@Table(name = "Products")
public class Product {
    // Fields corresponding to the columns in the Products table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated unique identifier for the product
    @Column(name = "ProductID")
    private int productID;

    @Column(name = "ProductName") // Name of the product
    private String productName;

    @Column(name = "Price") // Price of the product
    private double price;

    @Column(name = "Category") // Category to which the product belongs
    private String category;

    // Default constructor
    public Product() {
    }

    // Parameterized constructor to initialize all fields
    public Product(int productID, String productName, double price, String category) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.category = category;
    }

    // Getter for productID
    public int getProductID() {
        return productID;
    }

    // Setter for productID
    public void setProductID(int productID) {
        this.productID = productID;
    }

    // Getter for productName
    public String getProductName() {
        return productName;
    }

    // Setter for productName
    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Getter for price
    public double getPrice() {
        return price;
    }

    // Setter for price
    public void setPrice(double price) {
        this.price = price;
    }

    // Getter for category
    public String getCategory() {
        return category;
    }

    // Setter for category
    public void setCategory(String category) {
        this.category = category;
    }

    // toString method to provide a string representation of the Product object
    @Override
    public String toString() {
        return "Product [productID=" + productID + ", productName=" + productName + ", price=" + price + ", category=" + category + "]";
    }
}
