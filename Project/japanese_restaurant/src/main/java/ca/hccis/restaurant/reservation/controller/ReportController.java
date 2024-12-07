package ca.hccis.restaurant.reservation.controller;

import ca.hccis.restaurant.reservation.bo.ReservationBO;
import ca.hccis.restaurant.reservation.entity.ReportReservation;
import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
import ca.hccis.restaurant.reservation.util.CisUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
/**
 * Controller class for handling report-related operations in the restaurant reservation system.
 *
 * <p>
 * This controller provides endpoints for generating and viewing reservation reports, including:
 * <ul>
 *     <li>Reports based on date ranges</li>
 *     <li>Reports based on minimum reservation length</li>
 * </ul>
 * </p>
 *
 * <p>
 * The controller uses {@link ReservationBO} for business logic and interacts with the model to
 * pass data to the view for rendering.
 * </p>
 *
 * @author Sherri Ashton
 * @since 2024-10-25
 */
@Controller
@RequestMapping("/report")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
    /**
     * Displays the base view for reports.
     *
     * <p>
     * This method is the entry point for the report module and redirects users
     * to the report listing page.
     * </p>
     *
     * @param model The {@link Model} object for passing data to the view.
     * @param session The {@link HttpSession} object for managing session attributes.
     * @return The name of the view ("report/list") for displaying the report list.
     * @author Sherri Ashton
     * @since 2024-10-25
     */
    @RequestMapping("")
    public String home(Model model, HttpSession session) {
        logger.info("Running the reports controller base method");
        return "report/list";
    }
    /**
     * Displays the input form for generating a reservation report based on a date range.
     *
     * <p>
     * This method initializes the {@link ReportReservation} object with default start and
     * end dates and adds it to the model. The current date is also set in the session.
     * </p>
     *
     * @param model The {@link Model} object for passing data to the view.
     * @param session The {@link HttpSession} object for managing session attributes.
     * @return The name of the view ("report/reportReservationDateRange") for rendering the form.
     * @author Sherri Ashton
     * @since 2024-10-25
     */
    @GetMapping("/reservation/daterange")
    public String reportReservationDateRange(Model model, HttpSession session) {
        // Set the current date
        String currentDate = CisUtility.getCurrentDate(-30, "yyyy-MM-dd");
        session.setAttribute("currentDate", currentDate);

        String start = CisUtility.getCurrentDate(-30, "yyyy-MM-dd");
        String end = CisUtility.getCurrentDate(+30, "yyyy-MM-dd");

        //**********************************************************************
        // Put the report object in the model and send the user
        // to the report input view.
        //**********************************************************************
        ReportReservation reportReservation = new ReportReservation();
        //Set the default start/end dates for the report
        reportReservation.setDateStart(start);
        reportReservation.setDateEnd(end);

        model.addAttribute("reportInput", reportReservation);

        return "report/reportReservationDateRange";
    }

    /**
     * Processes the date range report and displays the results.
     *
     * <p>
     * This method uses {@link ReservationBO#processDateRangeReport} to fetch reservations
     * within the specified date range. If no reservations are found, a message is displayed.
     * </p>
     *
     * @param model The {@link Model} object for passing data to the view.
     * @param reportReservation The {@link ReportReservation} object containing the date range.
     * @return The name of the view ("report/reportReservationDateRange") for rendering the results.
     * @author Sherri Ashton
     * @since 2024-10-25
     */
    @PostMapping("/reservation/daterange/submit")
    public String reportReservationDateRangeSubmit(Model model, @ModelAttribute("reportInput") ReportReservation reportReservation) {
        LocalDate startDate = LocalDate.parse(reportReservation.getDateStart());
        LocalDate endDate = LocalDate.parse(reportReservation.getDateEnd());
        //Call BO method to process the report
        ArrayList<Reservation> reservations = ReservationBO.processDateRangeReport(reportReservation.getDateStart(), reportReservation.getDateEnd());
        if (endDate.isBefore(startDate)) {
            model.addAttribute("message", "End date cannot be before start date.");
            return "report/reportReservationDateRange";
        }

        // Format the date and time for each reservation
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        reservations.forEach(reservation -> {
            reservation.setFormattedReservationDateTime(reservation.getReservationDateTime().format(formatter));
        });
        //Put the list in the Java object
        reportReservation.setReservations(reservations);

        //Add a message in case the report does not contain any data
        if (reservations != null && reservations.isEmpty()) {
            model.addAttribute("message", "No data found");
            System.out.println("BJM - no data found");
        }

        //Put object in model so it can be used on the view (html)
        model.addAttribute("reportInput", reportReservation);

        return "report/reportReservationDateRange";

    }

    /**
     * Method to send user to the min length report.
     *
     * @param model
     * @return view for list
     * @author BJM
     * @since 2024-10-10 . Changed 2024-10-25 by Sherri Ashton for reservation.
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
     * @since 2024-10-11.  Changed 2024-10-25 by Sherri Ashton for reservation.
     */
    @RequestMapping("/reservation/minlength/submit")
    public String reportReservationMinLengthSubmit(Model model, @ModelAttribute("reportInput") ReportReservation reportReservation) {

        ArrayList<Reservation> reservations;
        try {
            reservations = ReservationBO.processMinLengthReport(reportReservation.getMinLength());
        } catch (SQLException e) {
            model.addAttribute("message", "Exception accessing database");
            reservations = null;
        }

        reportReservation.setReservations(reservations);

        if (reservations != null && reservations.isEmpty()) {
            model.addAttribute("message", "No data found");
            System.out.println("BJM - no data found");
        }

        model.addAttribute("reportInput",reportReservation);

        return "report/reportReservationMinLength";
    }
}



