package ca.hccis.restaurant.reservation.controller;

import ca.hccis.restaurant.reservation.bo.ReservationBO;
import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
import ca.hccis.restaurant.reservation.repositories.CodeValueRepository;
import ca.hccis.restaurant.reservation.repositories.ReservationRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationRepository _bpr;
    private final CodeValueRepository _cvr;

    @Autowired
    public ReservationController(ReservationRepository bpr, CodeValueRepository cvr) {
        _bpr = bpr;
        _cvr = cvr;
    }

//    private final ReservationService reservationService;

//    @Autowired
//    public ReservationController(ReservationService reservationService) {
//        this.reservationService = reservationService;
//    }
@Autowired
private MessageSource messageSource;
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @RequestMapping("")
    public String home(Model model, HttpSession session) {

        Iterable<Reservation> reservations = _bpr.findAll();
        model.addAttribute("reservations", reservations);
        model.addAttribute("reservation", new Reservation());
        return "reservation/list";
    }
//    // Display list of reservations
//    @GetMapping("/view")
//    public String listReservations(Model model) {
//        model.addAttribute("reservations", reservationService.getAllReservations());
//        model.addAttribute("reservation", new Reservation());
//        return "reservation/list";
//    }

//    // Display form for adding a new reservation
//    @GetMapping("/add")
//    public String addReservationForm(Model model) {
//        model.addAttribute("reservation", new Reservation());
//        return "reservation/add_edit";
//    }

    // Process adding a new reservation
    @RequestMapping("/add")
    public String add(Model model, HttpSession session) {

        ReservationBO.setReservationTypes(_cvr, session);

        ReservationBO.setReservationTypes(_cvr, session);
        Reservation reservation = new Reservation();
        ReservationBO.setReservationDefaults(reservation);
        model.addAttribute("reservation", reservation);
        return "reservation/add";
    }

//    // Display form for updating an existing reservation
//    @GetMapping("/update/{id}")
//    public String updateReservationForm(@PathVariable("id") Integer id, Model model) {
//        Reservation reservation = reservationService.getReservationById(id);
//        if (reservation != null) {
//            model.addAttribute("reservation", reservation);
//            return "reservation/add_edit";
//        }
//        return "redirect:/reservation/view";
//    }
//
//    // Process updating an existing reservation
//    @PostMapping("/update/{id}")
//    public String updateReservationSubmit(@PathVariable("id") Integer id, @Valid @ModelAttribute("reservation") Reservation reservation, BindingResult result) {
//        if (result.hasErrors()) {
//            return "reservation/add_edit";
//        }
//        reservation.setId(id);
//        reservationService.processAndSaveReservation(reservation);
//        return "redirect:/reservation/view";
//    }

    // Delete a reservation by ID
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        _bpr.deleteById(id);
        return "redirect:/reservation";
    }

//    // Search for reservations by name
//    @RequestMapping("/search")
//    public String searchReservations(@ModelAttribute("reservation") Reservation reservation, Model model) {
//        String searchName = reservation.getName();
//        model.addAttribute("reservations", reservationService.findByNameContaining(searchName));
//        return "reservation/list";
//    }
@RequestMapping("/search")
public String search(Model model, @ModelAttribute("reservation") Reservation reservation) {

    //**********************************************************************
    //Use repository method created to find any entities which contain
    //the name entered on the list page.
    //**********************************************************************
    model.addAttribute("reservation", _bpr.findByNameContaining(reservation.getName()));
    logger.debug("searched for Reservation name:" + reservation.getName());
    return "reservation/list";
}
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model, HttpSession session) {

        ReservationBO.setReservationTypes(_cvr, session);

        Optional reservation = _bpr.findById(id);
        if (reservation.isPresent()) {
            model.addAttribute("reservation", reservation.get());
            return "reservation/add";
        }

        //todo How can we communcicate this to the view.
        model.addAttribute("message", "Could not load the reservation");
        return "redirect:/reservation";
    }
    @RequestMapping("/submit")
    public String submit(Model model, HttpServletRequest request, @Valid @ModelAttribute("reservation") Reservation reservation, BindingResult bindingResult) {
        boolean valid = true;
        if (!valid || bindingResult.hasErrors()) {
            System.out.println("--------------------------------------------");
            System.out.println("Validation error - BJM");
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.getObjectName() + "-" + error.toString() + "-" + error.getDefaultMessage());
            }
            System.out.println("--------------------------------------------");
            reservation.setTotalCost(new BigDecimal(0));
            model.addAttribute("reservation", reservation);
            return "reservation/add";
        }

        ReservationBO.calculateReservationCost(reservation);
        _bpr.save(reservation);
        return "redirect:/reservation";
    }
}
