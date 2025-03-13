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
        // Left-aligned, 5px horizontal and vertical gaps
        panelLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        // Fetch products from the database
        List<String> products = database.allProducts();

        // Add each product as a button to panelLeft
        for (String productName : products) {
            JButton productButton = new JButton(productName);
            panelLeft.add(productButton);

            // Add action listener to handle button clicks (optional)
            productButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Get product details from database
                    String[] productDetails = database.getProductDetails(productName);
                    String category = productDetails[0];
                    String price = productDetails[1];
                    
                    // Update current line with product info
                    String currentText = receiptArea.getText();
                    if (currentText.isEmpty() || currentText.endsWith("\n")) {
                        receiptArea.setText(productName + " - " + category + " - " + price + "\n");
                    } else {
                        // Remove last line and replace with new product info
                        String[] lines = currentText.split("\n");
                        String newText = String.join("\n", Arrays.copyOf(lines, lines.length - 1)) + "\n";
                        receiptArea.setText(newText + productName + " - " + category + " - " + price + "\n");
                    }
                }
            });
        }

        // Add action listener for the "Add" button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                receiptArea.append("                     STEFANS SUPERSHOP\n");
                receiptArea.append("----------------------------------------------------\n");
                receiptArea.append("\n");
                receiptArea.append("Kvittonummer: 122        Datum: 2024-09-01 13:00:21\n");
                receiptArea.append("----------------------------------------------------\n");
                receiptArea.append("Kaffe Gevalia           5 *     51.00    =   255.00  \n");
                receiptArea.append("Nallebjörn              1 *     110.00   =   110.00  \n");
                receiptArea.append("Total                                        ------\n");
                receiptArea.append("                                             306.00\n");
                receiptArea.append("TACK FÖR DITT KÖP\n");
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
        // TODO: place custom component creation code here
    }
}