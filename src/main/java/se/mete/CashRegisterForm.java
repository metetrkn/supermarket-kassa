package se.mete;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.mete.repository.Database;
import java.util.List;
import java.util.Arrays;

@Component
public class CashRegisterForm {
    private JPanel panel1;
    private JPanel panelRight;
    private JPanel panelLeft;
    private JTextArea receiptArea;
    private JTextArea productInfoArea;
    private JPanel buttonsPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton addButton;
    private JButton payButton;
    private final Database database;

    @Autowired
    public CashRegisterForm(Database database) {
        this.database = database;
    }

    @PostConstruct
    public void init() {
        // Left panel with vertical layout
        panelLeft.setLayout(new BorderLayout(5, 5));
        
        // Create product info display area
        productInfoArea = new JTextArea(1, 20);
        productInfoArea.setEditable(false);
        productInfoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane infoScroll = new JScrollPane(productInfoArea);
        infoScroll.setPreferredSize(new Dimension(300, 30));

        // Fetch products from the database
        List<String> products = database.allProducts();

        // Create panel for product buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        
        // Add each product as a button to buttonPanel
        for (String productName : products) {
            JButton productButton = new JButton(productName);
            buttonPanel.add(productButton);

            // Add action listener to handle button clicks
            productButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Get product details from database
                    String[] productDetails = database.getProductDetails(productName);
                    String category = productDetails[0];
                    String price = productDetails[1];
                    
                    // Update product info display only in left panel
                    productInfoArea.setText(productName + " - " + category + " - " + price);
                }
            });
            
            // Add button panel and info area to left panel
            panelLeft.add(buttonPanel, BorderLayout.CENTER);
            panelLeft.add(new JScrollPane(productInfoArea), BorderLayout.SOUTH);
        }

        // Add quantity input field with proper layout
        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField quantityField = new JTextField("1", 5);
        quantityPanel.add(new JLabel("Antal:"));
        quantityPanel.add(quantityField);
        panelRight.add(quantityPanel, BorderLayout.NORTH);
        
        // Make receipt area scrollable
        JScrollPane scrollPane = new JScrollPane(receiptArea);
        panelRight.add(scrollPane, BorderLayout.CENTER);
        
        // Add action listener for the "Add" button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get current product details
                    String currentText = receiptArea.getText();
                    String[] lines = currentText.split("\n");
                    String lastLine = lines[lines.length - 1];
                    String[] productInfo = lastLine.split(" - ");
                    
                    // Get and validate quantity
                    String quantityText = quantityField.getText().trim();
                    int quantity;
                    try {
                        quantity = Integer.parseInt(quantityText);
                        if (quantity < 1) {
                            quantity = 1;
                            quantityField.setText("1");
                            throw new NumberFormatException("Quantity must be at least 1");
                        }
                    } catch (NumberFormatException ex) {
                        quantityField.setText("1");
                        throw new NumberFormatException("Invalid quantity: '" + quantityText + "'. Please enter a positive number.");
                    }
                    
                    // Calculate total price
                    double price = Double.parseDouble(productInfo[2]);
                    double total = price * quantity;
                    
                    // Format receipt line
                    String receiptLine = String.format("%-20s %2d * %8.2f = %8.2f\n",
                        productInfo[0], quantity, price, total);
                        
                    // Add to receipt
                    if (currentText.isEmpty()) {
                        // First product - create receipt header
                        receiptArea.setText("                     STEFANS SUPERSHOP\n");
                        receiptArea.append("----------------------------------------------------\n");
                        receiptArea.append("\n");
                        receiptArea.append("Kvittonummer: 122        Datum: 2024-09-01 13:00:21\n");
                        receiptArea.append("----------------------------------------------------\n");
                    }
                    receiptArea.append(receiptLine);
                    
                    // Clear current product line
                    receiptArea.append("\n");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel1,
                        "Please enter a valid quantity",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void run() {
        // Ensure the GUI runs on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Cash Register");
            frame.setContentPane(this.panel1);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setSize(1000, 800);

            frame.setVisible(true);
        });
    }

    private void createUIComponents() {
        // Initialize panels
        panel1 = new JPanel(new BorderLayout());
        panelLeft = new JPanel(new BorderLayout());
        panelRight = new JPanel(new BorderLayout());
        buttonsPanel = new JPanel(new FlowLayout());
        
        // Add panels to main panel
        panel1.add(panelLeft, BorderLayout.WEST);
        panel1.add(panelRight, BorderLayout.CENTER);
        panel1.add(buttonsPanel, BorderLayout.SOUTH);
        
        // Initialize text areas
        receiptArea = new JTextArea();
        productInfoArea = new JTextArea();
        
        // Initialize buttons
        addButton = new JButton("Add");
        payButton = new JButton("Pay");
        
        // Add buttons to button panel
        buttonsPanel.add(addButton);
        buttonsPanel.add(payButton);
    }
}