package se.mete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component // Ensure this annotation is present
public class AppRunner implements CommandLineRunner {
    private final CashRegisterForm cashRegisterForm;

    @Autowired // Ensure this annotation is present
    public AppRunner(CashRegisterForm cashRegisterForm) {
        this.cashRegisterForm = cashRegisterForm;
    }

    @Override
    public void run(String... args) throws Exception {
        cashRegisterForm.run(); // Run the GUI
    }
}