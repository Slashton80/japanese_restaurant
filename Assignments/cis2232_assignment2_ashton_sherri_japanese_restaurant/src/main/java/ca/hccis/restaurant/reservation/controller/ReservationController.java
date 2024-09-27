package ca.hccis.restaurant.reservation.controller;

import ca.hccis.restaurant.reservation.entity.Reservation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    @GetMapping("/new")
    public String showNewReservationForm(Model model) {
        model.addAttribute("reservationInput", new Reservation()); // Assuming 'Reservation' is the input model
        return "reservations/new"; // Maps to new.html in templates/reservations/
    }

    @PostMapping("/new/submit")
    public String submitNewReservation(@ModelAttribute Reservation reservationInput) {
        // Logic to save reservation goes here (e.g., using a service to save to a database)
        return "redirect:/reservations/view"; // Redirect to view.html after submission
    }

    @GetMapping("/view")
    public String viewReservations(Model model) {
        // Mock data or from database/service
        List<Reservation> reservations = new ArrayList<>();
        // Populate list with reservation data, e.g., from a database
        model.addAttribute("reservationInput", new Reservation()); // Use ReservationInput class if it's different
        model.addAttribute("reservationInput.reservations", reservations); // Pass the list to the view
        return "reservations/view"; // Maps to view.html in templates/reservations/

    }


}
