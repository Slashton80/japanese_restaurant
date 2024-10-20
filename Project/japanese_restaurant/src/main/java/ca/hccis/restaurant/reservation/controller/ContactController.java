package ca.hccis.restaurant.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {

    @GetMapping("/contact")
    public String contact() {
        return "contact"; // Make sure your contact.html is placed in the templates folder if using Thymeleaf
    }
}

