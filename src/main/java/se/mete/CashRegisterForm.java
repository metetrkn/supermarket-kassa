package se.mete;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import se.mete.repository.Database;
import java.util.List;

@Component
public class CashRegisterForm {
    private JPanel panel1;
    private JPanel panelRight;
    private JPanel panelLeft;
    private JTextArea receiptArea;
    private JTextArea productInfoArea;
    private JPanel buttonsPanel;
    private JTextField textField1; // Add this field
    private JTextField textField2; // Add this field
    private JTextField quantityField;
    private JButton addButton;
    private JButton payButton;
    private JButton dailyStatsButton;
    private final Database database;

    // Constructor injection without @Autowired
    public CashRegisterForm(Database database) {
        this.database = database;
    }

    @PostConstruct
    public void init() {
        // Initialize textField1 (if needed)
        textField1 = new JTextField();
        textField1.setColumns(10); // Set the number of columns

        // Initialize textField2 (if needed)
        textField2 = new JTextField();
        textField2.setColumns(10); // Set the number of columns

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

                    // Update product info display only in left panel
                    productInfoArea.setText(productName + " - " + category + " - " + price);
                }
            });
        }

        // Add button panel and info area to left panel
        panelLeft.add(buttonPanel, BorderLayout.CENTER);
        panelLeft.add(infoScroll, BorderLayout.SOUTH);

        // Add quantity input field and Add button
        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        quantityField = new JTextField("1", 5);
        quantityField.setHorizontalAlignment(JTextField.CENTER);
        addButton = new JButton("Add");
        addButton.addActionListener(e -> addToReceipt());
        quantityPanel.add(new JLabel("Quantity:"));
        quantityPanel.add(quantityField);
        quantityPanel.add(addButton);

        // Add Pay button
        payButton = new JButton("Pay");
        payButton.addActionListener(e -> payAndClear());

        // Add Daily Stats button
        dailyStatsButton = new JButton("Daily Stats");
        dailyStatsButton.addActionListener(e -> showDailyStats());

        // Add buttons to bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(addButton);
        bottomPanel.add(payButton);
        bottomPanel.add(dailyStatsButton);

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
            String receiptLine = String.format("%-20s %2d * %8.2f = %8.2f SEK\n",
                    productName, quantity, price, totalPrice);

            if (receiptArea.getText().isEmpty()) {
                receiptArea.setText("                     A101 SUPERSHOP\n");
                receiptArea.append("----------------------------------------------------\n");
                receiptArea.append("\n");
                receiptArea.append("Kvittonummer: 122        Datum: 2024-09-01 13:00:21\n");
                receiptArea.append("----------------------------------------------------\n");
            }
            receiptArea.append(receiptLine);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel1, "Invalid quantity: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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
                // Extract the part after the "=" sign
                String[] parts = line.split("=");
                if (parts.length > 1) {
                    // Remove non-numeric characters (e.g., "SEK", whitespace) and replace comma with dot for decimal
                    String amountStr = parts[1].replaceAll("[^\\d,]", "").replace(",", ".").trim();
                    try {
                        double lineTotal = Double.parseDouble(amountStr);
                        totalInclVat += lineTotal;
                    } catch (NumberFormatException e) {
                        // Ignore lines that cannot be parsed (e.g., header lines)
                        System.err.println("Failed to parse line: " + line);
                    }
                }
            }
        }

        // Display the total and "Thanks for shopping" message
        receiptArea.append(String.format("TOTAL: %.2f SEK\n", totalInclVat));
        receiptArea.append("TACK FÖR DITT KÖP!\n\n");

        // Use a Timer to clear the receipt area after 1.5 seconds
        Timer timer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                receiptArea.setText(""); // Clear the receipt area
            }
        });
        timer.setRepeats(false); // Ensure the timer only runs once
        timer.start(); // Start the timer
    }

    private void showDailyStats() {
        String dateStr = JOptionPane.showInputDialog(panel1, "Enter date (YYYY-MM-DD) for stats:", "2025-03-01");
        if (dateStr == null || dateStr.isBlank()) {
            return;
        }

        // Fetch daily stats from the database (dummy implementation)
        String stats = "Daily stats for " + dateStr + "\n" +
                "Total Sales: 1000 SEK\n" +
                "Total VAT: 200 SEK\n" +
                "Number of Receipts: 10";

        JOptionPane.showMessageDialog(panel1, stats, "Daily Stats", JOptionPane.INFORMATION_MESSAGE);
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
        dailyStatsButton = new JButton("Daily Stats");

        // Add buttons to button panel
        buttonsPanel.add(addButton);
        buttonsPanel.add(payButton);
        buttonsPanel.add(dailyStatsButton);
    }
}