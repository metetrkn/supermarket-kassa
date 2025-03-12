package se.mete.kassan;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class AppRunner implements CommandLineRunner {

    private final CashRegisterForm cashRegisterForm;

    public AppRunner(CashRegisterForm cashRegisterForm) {
        this.cashRegisterForm = cashRegisterForm;
    }

    @Override
    public void run(String... args) throws Exception {
        cashRegisterForm.run();
    }
}