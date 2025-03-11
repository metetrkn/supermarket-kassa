package se.systementor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private Database database = new Database();

    public CashRegisterForm() {
        for (String name : database.activeProducts()) {
            JButton button = new JButton(name);
            buttonsPanel.add(button);
        }
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
        JFrame frame = new JFrame("Cash Register");
        frame.setContentPane(new CashRegisterForm().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize( 1000, 800 ) ;


        frame.setVisible(true);



    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
