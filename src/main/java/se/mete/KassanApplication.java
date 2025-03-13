package se.mete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KassanApplication {


    public static void main(String[] args) {
        // Disable headless mode to allow GUI
        System.setProperty("java.awt.headless", "false");

        SpringApplication.run(KassanApplication.class, args
        );
    }

}
