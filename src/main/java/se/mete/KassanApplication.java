package se.mete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class to bootstrap the Spring Boot application.
 * Enables Spring Boot auto-configuration and component scanning.
 */
@SpringBootApplication // Combines @Configuration, @EnableAutoConfiguration, and @ComponentScan
public class KassanApplication {

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args Command-line arguments passed to the application
     */
    public static void main(String[] args) {
        // Disable headless mode to allow GUI components to run
        System.setProperty("java.awt.headless", "false");

        // Run the Spring Boot application
        SpringApplication.run(KassanApplication.class, args);
    }
}
