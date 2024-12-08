package ca.hccis.restaurant.reservation.controller;

import ca.hccis.restaurant.reservation.bo.ReservationBO;
import ca.hccis.restaurant.reservation.bo.ReservationValidationBO;
import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
import ca.hccis.restaurant.reservation.repositories.CodeValueRepository;
import ca.hccis.restaurant.reservation.repositories.ReservationRepository;
import ca.hccis.restaurant.reservation.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Controller class for managing reservation-related operations in the restaurant reservation system.
 * This controller provides endpoints for CRUD operations on reservations, including:
 * * Viewing all reservations
 * * Adding a new reservation
 * * Editing an existing reservation
 * * Deleting a reservation
 * * Searching for reservations by name
 * The controller interacts with repositories, the {@link ReservationBO} business logic,
 * and the {@link Model} object to manage data and pass it to views.
 *
 * @author Sherri Ashton
 * @since 2024-11-16
 */
@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationRepository _bpr;
    private final CodeValueRepository _cvr;

    /**
     * Constructor to initialize dependencies for {@link ReservationController}.
     * This constructor initializes the repositories used for fetching and managing reservations
     * and code values.
     *
     * @param bpr The {@link ReservationRepository} used for CRUD operations on reservations.
     * @param cvr The {@link CodeValueRepository} used for fetching reservation types and code values.
     * @author Sherri Ashton
     * @since 2024-11-16
     */
    @Autowired
    public ReservationController(ReservationRepository bpr, CodeValueRepository cvr) {
        _bpr = bpr;
        _cvr = cvr;
    }

    @Autowired
    private MessageSource messageSource;
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);


    /**
     * Displays the list of all reservations.
     * This method fetches all reservations from the database and adds them to the model for rendering.
     * It also initializes an empty {@link Reservation} object for the search form.
     *
     * @param model   The {@link Model} object for passing data to the view.
     * @param session The {@link HttpSession} object for managing session attributes.
     * @return The name of the view ("reservation/list") for displaying the list of reservations.
     * @author Sherri Ashton
     * @since 2024-11-16
     */
    @GetMapping("")
    public String home(Model model, HttpSession session) {

        Iterable<Reservation> reservations = _bpr.findAll();
        // Formats the date and time for each reservation
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        reservations.forEach(reservation -> {
            String formattedDateTime = reservation.getReservationDateTime().format(formatter);
            reservation.setFormattedReservationDateTime(formattedDateTime);
        });
        model.addAttribute("reservations", reservations);
        model.addAttribute("reservation", new Reservation());
        return "reservation/list";
    }


    /**
     * Displays the form for adding a new reservation.
     * This method initializes a new {@link Reservation} object with default values
     * using {@link ReservationBO#setReservationDefaults} and passes it to the view.
     *
     * @param model   The {@link Model} object for passing data to the view.
     * @param session The {@link HttpSession} object for managing session attributes.
     * @return The name of the view ("reservation/add") for rendering the reservation form.
     * @author Sherri Ashton
     * @since 2024-11-16
     */

    @GetMapping("/add")
    public String add(Model model, HttpSession session) {
        ReservationBO.setReservationTypes(_cvr, session);
        Reservation reservation = new Reservation();
        ReservationBO.setReservationDefaults(reservation);
        model.addAttribute("reservation", reservation);
        return "reservation/add";
    }

    /**
     * Deletes a reservation by its ID.
     * This method deletes a reservation from the database using its ID and redirects
     * the user to the reservation list page.
     *
     * @param id The ID of the reservation to be deleted.
     * @return A redirect to the reservation list page.
     * @author Sherri Ashton
     * @since 2024-11-16
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        _bpr.deleteById(id);
        return "redirect:/reservation";
    }

    /**
     * Searches for reservations by name.
     * This method uses the {@link ReservationRepository#findByNameContaining} method
     * to find reservations whose names match the search query.
     *
     * @param model       The {@link Model} object for passing data to the view.
     * @param reservation The {@link Reservation} object containing the search query.
     * @return The name of the view ("reservation/list") for displaying the search results.
     * @author Sherri Ashton
     * @since 2024-11-16
     */
    @PostMapping("/search")
    public String search(Model model, @ModelAttribute("reservation") Reservation reservation) {

        //**********************************************************************
        //Use repository method created to find any entities which contain
        //the name entered on the list page.
        //**********************************************************************
        // Checks if the ID is filled for searching by ID
        if (reservation.getId() != null && reservation.getId() > 0) {
            Optional<Reservation> reservationOptional = _bpr.findById(reservation.getId());
            if (reservationOptional.isPresent()) {
                model.addAttribute("reservations", Collections.singletonList(reservationOptional.get()));
            } else {
                model.addAttribute("reservations", new ArrayList<>());
                model.addAttribute("message", "No reservation found with ID: " + reservation.getId());
            }
            logger.debug("searched for Reservation by ID: " + reservation.getId());
        }
        // If the ID is not provided, search by name
        else if (reservation.getName() != null && !reservation.getName().isEmpty()) {
            model.addAttribute("reservations", _bpr.findByNameContaining(reservation.getName()));
            logger.debug("searched for Reservation name: " + reservation.getName());
        }
        // If neither ID nor name are provided, return an empty list or an appropriate message
        else {
            model.addAttribute("reservations", new ArrayList<>());
            model.addAttribute("message", "* Please enter either a Name or an ID to search.");
        }
        // Clears the fields in the reservation object to reset the form
        reservation.setId(null);
        reservation.setName(null);

        model.addAttribute("reservation", reservation);

        return "reservation/list";

    }

    /**
     * Displays the form for editing an existing reservation.
     * This method fetches a reservation by its ID and passes it to the view for editing.
     * If the reservation is not found, a message is added to the model, and the user is redirected.
     *
     * @param id      The ID of the reservation to be edited.
     * @param model   The {@link Model} object for passing data to the view.
     * @param session The {@link HttpSession} object for managing session attributes.
     * @return The name of the view ("reservation/add") for editing the reservation,
     * or a redirect to the reservation list if the reservation is not found.
     * @author Sherri Ashton
     * @since 2024-11-16
     */
    @GetMapping("/edit/{id}")
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

    /**
     * Processes the submission of a reservation form.
     * This method validates the reservation data and calculates its total cost using
     * {@link ReservationBO#calculateReservationCost}. If validation errors occur,
     * the user is returned to the form with error messages.
     *
     * @param model         The {@link Model} object for passing data to the view.
     * @param request       The {@link HttpServletRequest} containing the request details.
     * @param reservation   The {@link Reservation} object to be submitted.
     * @param bindingResult The {@link BindingResult} object for validation errors.
     * @return A redirect to the reservation list page if successful, or the reservation
     * form view ("reservation/add") if validation errors occur.
     * @author Sherri Ashton
     * @since 2024-11-16
     */

    @PostMapping("/submit")
    public String submit(Model model, HttpServletRequest request, @Valid @ModelAttribute("reservation") Reservation reservation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> logger.error("Validation error: {}", error.getDefaultMessage()));
            model.addAttribute("reservation", reservation);
            return "reservation/add";
        }
        // Custom business logic validations for Sprint 5

        // Checks if the reservation date is not in the past
        if (!ReservationValidationBO.validateReservationDate(reservation)) {
            bindingResult.rejectValue("reservationDateTime", "error.reservationDateTime", "*Reservation date must be today or later.");
        }

        // Checks if there is at least one adult or one senior
        if (!ReservationValidationBO.validateNumberOfAdultsOrSeniors(reservation)) {
            bindingResult.rejectValue("numberOfAdults", "error.numberOfAdults", "*At least one adult or one senior must be included in the reservation.");
        }

        // Checks if there is at least one adult if there are children
        if (!ReservationValidationBO.validateChildrenSupervision(reservation)) {
            bindingResult.rejectValue("numberOfChildren", "error.numberOfChildren", "*Children must be supervised by at least one adult.");
        }

        // If any custom validation errors exist, return to the form
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> logger.error("Custom validation error: {}", error.getDefaultMessage()));
            model.addAttribute("reservation", reservation);
            return "reservation/add";
        }
        logger.info("Saving reservation: {}", reservation.getName());
        ReservationBO.calculateReservationCost(reservation);
        _bpr.save(reservation);
        return "redirect:/reservation";
    }

    /**
     * Displays today's reservations.
     * Fetches all reservations made for the current date and passes them to the view.
     *
     * @param model The {@link Model} object for passing data to the view.
     * @return The name of the view ("reservation/today") for displaying today's reservations.
     */
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/today")
    public String getTodayReservations(Model model) {
        List<Reservation> reservations = reservationService.getReservationsForToday();

        // Formats the reservation dates before passing to the view
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        reservations.forEach(reservation -> {
            String formattedDateTime = reservation.getReservationDateTime().format(formatter);
            reservation.setFormattedReservationDateTime(formattedDateTime);
        });

        model.addAttribute("reservations", reservations);
        return "reservation/today";
    }
}
