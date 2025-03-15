package se.mete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * AppRunner class to initialize and run the application.
 * Implements CommandLineRunner to execute logic after the application context is loaded.
 */
@Component // Marks this class as a Spring component for dependency injection
public class AppRunner implements CommandLineRunner {
    private final CashRegisterForm cashRegisterForm; // Dependency for the cash register form

    /**
     * Constructor for dependency injection.
     *
     * @param cashRegisterForm The CashRegisterForm instance to be used
     */
    @Autowired
    public AppRunner(CashRegisterForm cashRegisterForm) {
        this.cashRegisterForm = cashRegisterForm;
    }

    /**
     * Executes the application logic after the Spring context is loaded.
     *
     * @param args Command-line arguments passed to the application
     * @throws Exception if an error occurs during execution
     */
    @Override
    public void run(String... args) throws Exception {
        cashRegisterForm.run(); // Run the GUI or main logic of the application
    }
}
