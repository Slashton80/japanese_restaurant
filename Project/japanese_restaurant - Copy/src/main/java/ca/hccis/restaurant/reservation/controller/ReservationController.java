package ca.hccis.restaurant.reservation.controller;

import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
import ca.hccis.restaurant.reservation.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Display list of reservations
    @GetMapping("/view")
    public String listReservations(Model model) {
        model.addAttribute("reservations", reservationService.getAllReservations());
        model.addAttribute("reservation", new Reservation()); // Provide a blank reservation object for the search form
        return "reservation/list"; // Maps to reservation/list.html for listing reservations
    }

    // Display form for adding a new reservation
    @GetMapping("/add")
    public String addReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "reservation/add_edit"; // Maps to reservation/add_edit.html
    }

    // Process adding a new reservation
    @PostMapping("/add")
    public String addReservationSubmit(@Valid @ModelAttribute("reservation") Reservation reservation, BindingResult result) {
        if (result.hasErrors()) {
            return "reservation/add_edit"; // Redirects back to add/edit form in case of validation errors
        }
        reservationService.processAndSaveReservation(reservation);
        return "redirect:/reservation/view"; // Redirects to the reservation list page
    }

    // Display form for updating an existing reservation
    @GetMapping("/update/{id}")
    public String updateReservationForm(@PathVariable("id") Integer id, Model model) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation != null) {
            model.addAttribute("reservation", reservation);
            return "reservation/add_edit"; // Maps to reservation/add_edit.html
        }
        return "redirect:/reservation/view"; // Redirects to the reservation list if the reservation does not exist
    }

    // Process updating an existing reservation
    @PostMapping("/update/{id}")
    public String updateReservationSubmit(@PathVariable("id") Integer id, @Valid @ModelAttribute("reservation") Reservation reservation, BindingResult result) {
        if (result.hasErrors()) {
            return "reservation/add_edit"; // Redirects back to add/edit form in case of validation errors
        }
        reservation.setId(id);
        reservationService.processAndSaveReservation(reservation);
        return "redirect:/reservation/view"; // Redirects to the reservation list page
    }

    // Delete a reservation by ID
    @GetMapping("/delete/{id}")
    public String deleteReservation(@PathVariable("id") Integer id) {
        reservationService.deleteReservationById(id);
        return "redirect:/reservation/view"; // Redirects to the reservation list page after deletion
    }
}
