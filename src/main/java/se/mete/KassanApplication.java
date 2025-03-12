package se.mete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.mete.kassan.CashRegisterForm;

@SpringBootApplication
public class KassanApplication {

    public static void main(String[] args) {
        SpringApplication.run(KassanApplication.class, args
        );
    }

}
