package se.mete.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Database class for handling database operations, repository for the Products table.
 */
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
}
