package se.mete;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import se.mete.repository.Database;
import java.util.List;

/**
 * CashRegisterForm class representing the GUI for the cash register system.
 * Handles product selection, receipt generation, and payment processing.
 */
@Component // Marks this class as a Spring component for dependency injection
public class CashRegisterForm {
    private JPanel panel1; // Main panel for the GUI
    private JPanel panelRight; // Right panel (currently unused)
    private JPanel panelLeft; // Left panel for product selection and info
    private JTextArea receiptArea; // Text area for displaying the receipt
    private JTextArea productInfoArea; // Text area for displaying product info
    private JPanel buttonsPanel; // Panel for buttons
    private JTextField textField1; // Placeholder text field (currently unused)
    private JTextField textField2; // Placeholder text field (currently unused)
    private JTextField quantityField; // Text field for entering product quantity
    private JButton addButton; // Button to add a product to the receipt
    private JButton payButton; // Button to process payment
    private final Database database; // Database instance for fetching product data

    /**
     * Constructor for dependency injection.
     *
     * @param database The Database instance to be used
     */
    public CashRegisterForm(Database database) {
        this.database = database;
    }

    /**
     * Initializes the GUI components and layout.
     */
    @PostConstruct
    public void init() {
        // Initialize text fields (if needed)
        textField1 = new JTextField();
        textField1.setColumns(10);

        textField2 = new JTextField();
        textField2.setColumns(10);

        // Set layout for the left panel
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
        buttonPanel.setLayout(new GridLayout(0, 2)); // Use GridLayout for product buttons

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

                    // Update product info display
                    productInfoArea.setText(productName + " - " + price);
                }
            });
        }

        // Create a panel for product info and quantity
        JPanel productInfoAndQuantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Add product info display area
        productInfoAndQuantityPanel.add(new JLabel("Product:"));
        productInfoAndQuantityPanel.add(infoScroll);

        // Add quantity input field
        productInfoAndQuantityPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField("1", 5);
        quantityField.setHorizontalAlignment(JTextField.CENTER);
        productInfoAndQuantityPanel.add(quantityField);

        // Add Add button
        addButton = new JButton("Add");
        addButton.addActionListener(e -> addToReceipt());
        productInfoAndQuantityPanel.add(addButton);

        // Add button panel and info area to left panel
        panelLeft.add(buttonPanel, BorderLayout.CENTER);
        panelLeft.add(productInfoAndQuantityPanel, BorderLayout.SOUTH);

        // Add Pay button
        payButton = new JButton("Pay");
        payButton.addActionListener(e -> payAndClear());

        // Add buttons to bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(payButton);

        // Make receipt area scrollable
        receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(receiptArea);

        // Set up main layout
        panel1.setLayout(new BorderLayout());
        panel1.add(panelLeft, BorderLayout.WEST);
        panel1.add(scrollPane, BorderLayout.CENTER);
        panel1.add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Adds the selected product and quantity to the receipt.
     */
    private void addToReceipt() {
        String productInfo = productInfoArea.getText();
        if (productInfo.isEmpty()) {
            JOptionPane.showMessageDialog(panel1, "No product selected!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String productName = productInfo.split(" - ")[0];
            String[] productDetails = database.getProductDetails(productName);
            double price = Double.parseDouble(productDetails[1].trim().replace(',', '.'));

            int quantity = Integer.parseInt(quantityField.getText().trim());
            if (quantity <= 0) {
                throw new NumberFormatException("Quantity must be a positive integer.");
            }

            double totalPrice = price * quantity;

            // Check if receipt is empty and initialize if needed
            if (receiptArea.getText().isEmpty()) {
                initializeReceiptHeader();
            }

            // Create a structured receipt line
            String receiptLine = String.format("║ %-20s %3d × %7.2f = %8.2f SEK ║\n",
                    productName, quantity, price, totalPrice);
            receiptArea.append(receiptLine);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel1, "Invalid quantity: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Initializes the receipt header with store information and formatting.
     */
    private void initializeReceiptHeader() {
        receiptArea.setText("╔════════════════════════════════════════════╗\n");
        receiptArea.append("║  █████╗ ██╗  ██╗ ██╗  ██╗ █████╗ ██████╗  \n");
        receiptArea.append("║ ██╔══██╗██║  ██║██╔╝  ██║██╔══██╗██╔══██╗ \n");
        receiptArea.append("║ ███████║███████║██║ █╗ ██║███████║██████╔╝ \n");
        receiptArea.append("║ ██╔══██║██╔══██║██║███╗██║██╔══██║██╔══██╗ \n");
        receiptArea.append("║ ██║  ██║██║  ██║╚███╔███╔╝██║  ██║██║  ██║ \n");
        receiptArea.append("║ ╚═╝  ╚═╝╚═╝  ╚═╝ ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝  ╚═╝ \n");
        receiptArea.append("╠════════════════════════════════════════════╣\n");
        receiptArea.append("║          A101 Supermarket Receipt          \n");
        receiptArea.append("╠════════════════════════════════════════════╣\n");
        receiptArea.append("║ Kvittonummer: 122                          \n");
        receiptArea.append("║ Datum: 2024-09-01 13:00:21                 \n");
        receiptArea.append("╠════════════════════════════════════════════╣\n");
    }

    /**
     * Processes payment, calculates the total, and clears the receipt.
     */
    private void payAndClear() {
        if (receiptArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(panel1, "No items in the receipt!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calculate the total amount of all items in the receipt
        double totalInclVat = 0.0;
        String[] lines = receiptArea.getText().split("\n");
        for (String line : lines) {
            if (line.contains("=")) { // Check if the line contains a product total
                String[] parts = line.split("=");
                if (parts.length > 1) {
                    String amountStr = parts[1].replaceAll("[^\\d,]", "").replace(",", ".").trim();
                    try {
                        double lineTotal = Double.parseDouble(amountStr);
                        totalInclVat += lineTotal;
                    } catch (NumberFormatException e) {
                        System.err.println("Failed to parse line: " + line);
                    }
                }
            }
        }

        // Display the total and footer
        receiptArea.append("╠════════════════════════════════════════════╣\n");
        receiptArea.append(String.format("║ TOTAL: %38.2f SEK \n", totalInclVat));
        receiptArea.append("╠════════════════════════════════════════════╣\n");
        receiptArea.append("║             TACK FÖR DITT KÖP!            \n");
        receiptArea.append("║                                            \n");
        receiptArea.append("║  Besök oss på: www.a101supermarket.se      \n");
        receiptArea.append("║  Kundservice: 08-123 456 78                \n");
        receiptArea.append("║  Öppettider: 08:00 - 22:00                 \n");
        receiptArea.append("║                                            \n");
        receiptArea.append("║  Följ oss:                                 \n");
        receiptArea.append("║  [FB] [TW] [IG]                            \n");
        receiptArea.append("╚════════════════════════════════════════════╝\n\n");

        // Use a Timer to clear the receipt area after 1.5 seconds
        Timer timer = new Timer(1500, e -> receiptArea.setText(""));
        timer.setRepeats(false); // Ensure the timer only runs once
        timer.start(); // Start the timer
    }

    /**
     * Runs the GUI on the Event Dispatch Thread (EDT).
     */
    public void run() {
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

    /**
     * Creates and initializes UI components.
     */
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
