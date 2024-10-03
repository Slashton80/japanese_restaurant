package ca.hccis.restaurant.reservation.controller;

import ca.hccis.restaurant.reservation.entity.Reservation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    // Static list to store reservations (for simplicity)
    private static List<Reservation> reservations = new ArrayList<>();

    // Show the form to create a new reservation
    @GetMapping("/new")
    public String showNewReservationForm(Model model) {
        model.addAttribute("reservationInput", new Reservation());
        return "reservations/new"; // Return the Thymeleaf template for new reservation
    }

    // Handle the form submission, calculate the total cost, and store the reservation
    @PostMapping("/new/submit")
    public String submitNewReservation(@ModelAttribute Reservation reservationInput) {
        // Calculate total cost of the reservation (ensure you have calculateTotalCost method in Reservation class)
        double totalCost = reservationInput.calculateTotalCost();
        reservationInput.setTotalCost(totalCost); // Assuming you have a totalCost field in the Reservation class

        // Add the reservation to the list
        reservations.add(reservationInput);

        // Redirect to view all reservations
        return "redirect:/reservations/view";
    }

    // Show the list of all reservations
    @GetMapping("/view")
    public String viewReservations(Model model) {
        model.addAttribute("reservations", reservations); // Add all reservations to the model
        return "reservations/view"; // Return the Thymeleaf template for viewing reservations
    }

}
