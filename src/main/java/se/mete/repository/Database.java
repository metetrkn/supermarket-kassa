package se.mete.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;


/**
 * Database class for handling database operations, repository for the Products table.
 */
@Repository
public class Database {
    // Database connection credeantials, URL,username and password
    String url = "jdbc:mysql://localhost:3306/A101-db";
    String user = "root";
    String password = "123394";

    /**
     * Establishes a connection to the database.
     * 
     * @return Connection object
     * @throws SQLException if a database access error occurs
     */
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }


    /**
     * Retrieves all active products from the database.
     * 
     * @return List of product names
     */
    public List<String> allProducts() {
        // List to store product names
        ArrayList<String> products = new ArrayList<String>();

        try {
            // Establish connection
            Connection conn = getConnection();
            // Create statement
            Statement stmt = conn.createStatement();
            // Execute query to get all products
            ResultSet rs = stmt.executeQuery("SELECT ProductName FROM Products");

            // Iterate through the result set and add product names to the list
            while (rs.next()) {
                products.add(rs.getString("ProductName"));
            }

            // Close result set, statement, and connection
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            // Print stack trace if an exception occurs
            e.printStackTrace();
        }

        // Return the list of product names
        return products;
    }

    /**
     * Retrieves product details (category and price) for a given product name
     *
     * @param productName The name of the product to look up
     * @return String array containing [category, price]
     */
    public String[] getProductDetails(String productName) {
        String[] details = new String[2];
        
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT Category, Price FROM Products WHERE ProductName = ?");
            stmt.setString(1, productName);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                details[0] = rs.getString("Category");
                details[1] = String.format("%.2f", rs.getDouble("Price"));
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return details;
    }
}
