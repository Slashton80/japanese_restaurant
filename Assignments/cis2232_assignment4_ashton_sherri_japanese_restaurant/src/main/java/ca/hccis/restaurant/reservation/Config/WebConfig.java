package ca.hccis.restaurant.reservation.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Configuration class for customizing Spring MVC behavior.
 *
 * This class adds a custom date-time formatter for LocalDateTime fields.
 *
 * @author Sherri Ashton
 * @since 2024-11-18
 * @Source https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-config/customize.html#page-title
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        registrar.registerFormatters(registry); // Registers the formatter with the registry
    }
}
