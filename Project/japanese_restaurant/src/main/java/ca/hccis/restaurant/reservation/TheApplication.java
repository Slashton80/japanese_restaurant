package ca.hccis.restaurant.reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * The main entry point for the Spring Boot application.
 * This class bootstraps the application by invoking the {@link SpringApplication#run(Class, String...)} method.
 * The {@link SpringBootApplication} annotation enables autoconfiguration, component scanning,
 * and configuration properties for the application.
 *
 * @author Sherri Ashton
 * @since 2024-11-28
 */
@SpringBootApplication
public class TheApplication {

    public static void main(String[] args) {
        SpringApplication.run(TheApplication.class, args);
    }

}
