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

    @GetMapping("/new")
    public String showNewReservationForm(Model model) {
        model.addAttribute("reservationInput", new Reservation());
        return "reservations/new";
    }

    @PostMapping("/new/submit")
    public String submitNewReservation(@ModelAttribute Reservation reservationInput) {

        return "redirect:/reservations/view";
    }

    @GetMapping("/view")
    public String viewReservations(Model model) {

        List<Reservation> reservations = new ArrayList<>();

        model.addAttribute("reservationInput", new Reservation());
        model.addAttribute("reservationInput.reservations", reservations);
        return "reservations/view";

    }


}
