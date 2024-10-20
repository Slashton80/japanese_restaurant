package ca.hccis.restaurant.reservation.controller;

import ca.hccis.restaurant.reservation.bo.ReservationBO;
import ca.hccis.restaurant.reservation.entity.ReportReservation;
import ca.hccis.restaurant.reservation.entity.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@RequestMapping("/report")
public class ReportController {

    // Static list to store reservations (for simplicity)
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    /**
     * Send the user to list of reports view.
     *
     * @param model
     * @param session
     * @return To the appropriate view
     * @author BJM
     * @since 20220624
     */
    @RequestMapping("")
    public String home(Model model, HttpSession session) {

        //BJM 20200602 Issue#1 Set the current date in the session
        logger.info("Running the reports controller base method");
        return "report/list";
    }

    // Shows the form to create a new reservation
    @GetMapping("/reservation/daterange")
    public String reportReservationDateRange(Model model) {
        model.addAttribute("reportInput", new ReportReservation());
        return "report/reportReservationDateRange"; // Return the Thymeleaf template for new reservation
    }
    //**********************************************************************
    // This could be done using the repository but there will be times when
    // jdbc will be useful.  For the reports, the requirements state that you
    // are to use jdbc to obtain the data for the report.
    //**********************************************************************
    // Handles the form submission, calculate the total cost, and store the reservation
    @PostMapping("/reservation/daterange/submit")
    public String reportReservationDateRangeSubmit(Model model, @ModelAttribute("reportInput") ReportReservation reportReservation) {
        // Fetches the start and end dates
        String start = reportReservation.getDateStart();
        String end = reportReservation.getDateEnd();

        // Validates that the start date is not after the end date
        if (start.compareTo(end) > 0) {
            model.addAttribute("message", "Start date cannot be after the end date.");
            return "report/reportReservationDateRange"; // Return the same form with the error message
        }

        // Calls the BO method to process the report
        ArrayList<Reservation> reservations = ReservationBO.processDateRangeReport(start, end);

        // Checks if there are no reservations found
        if (reservations == null || reservations.isEmpty()) {
            // Only shows the message if there are no reservations
            model.addAttribute("message", "No reservations found for the selected date range.");
            logger.info("No reservations found for the selected date range.");
        } else {
            // Clears the message if there are reservations found
            model.addAttribute("message", "Report generated successfully.");
            logger.info("Report generated successfully.");
        }

        // Adds reservations to the model for display in the view
        model.addAttribute("reservations", reservations);

        // Passes the report input object back to the view
        model.addAttribute("reportInput", reportReservation);

        // Return the Thymeleaf template for the report
        return "report/reportReservationDateRange";
    }

    /**
     * Method to send user to the min length report.
     *
     * @param model
     * @return view for list
     * @author BJM
     * @since 2024-10-10
     */
    @RequestMapping("/reservation/minlength")
    public String reportReservationMinLength(Model model) {

        //**********************************************************************
        // Put the report object in the model and send the user
        // to the report input view.
        //**********************************************************************
        model.addAttribute("reportInput", new ReportReservation());

        return "report/reportReservationMinLength";
    }

    /**
     * Process the report
     *
     * @param model
     * @param reportReservation Object containing inputs for the report
     * @return view to show report
     * @author BJM
     * @since 2024-10-11
     */
//    @RequestMapping("/reservation/minlength/submit")
//    public String reportReservationMinLengthSubmit(Model model, @ModelAttribute("reportInput") ReportReservation reportReservation) {
//
//        //**********************************************************************
//        // This could be done using the repository but there will be times when
//        // jdbc will be useful.  For the reports, the requirements state that you
//        // are to use jdbc to obtain the data for the report.
//        //**********************************************************************
//        ReservationDAO reservationDAO = new ReservationDAO();
//        int minLength = reportReservation.getMinLength();
//        ArrayList<Reservation> reservations = null;
//        try {
//            reservations = reservationDAO.selectAllWithMinLength(minLength);
//        } catch (SQLException e) {
//            model.addAttribute("message", "Exception accessing database");
//        }
//        reportReservation.setReservations(reservations);
//
//        if (reservations != null && reservations.isEmpty()) {
//            model.addAttribute("message", "No data found");
//            System.out.println("BJM - no data found");
//        }
//
//        model.addAttribute("reportInput", reportReservation);
//
//        return "report/reportReservationMinLength";
//    }

}

