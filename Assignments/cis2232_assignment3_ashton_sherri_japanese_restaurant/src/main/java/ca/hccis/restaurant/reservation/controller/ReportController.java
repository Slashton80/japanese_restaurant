package ca.hccis.restaurant.reservation.controller;

import ca.hccis.restaurant.reservation.dao.ReservationDAO;
import ca.hccis.restaurant.reservation.entity.ReportReservation;
import ca.hccis.restaurant.reservation.entity.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

/**
 * Controller to administer reports of the project.
 *
 * @author BJM
 * @since 20220616
 */
@Controller
@RequestMapping("/report")
public class ReportController {

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

    /**
     * Method to send user to the date range report.
     *
     * @param model
     * @return view for list
     * @author BJM
     * @since 2024-10-10
     */


        //**********************************************************************
        // Put the report object in the model and send the user
        // to the report input view.
        //**********************************************************************
    @RequestMapping("/reservation/daterange")
    public String reportReservationDateRange(Model model) {
        model.addAttribute("reportInput", new ReportReservation());
        return "reportReservationDateRange"; // Check if this matches your Thymeleaf view name
    }


    /**
     * Process the report
     *
     * @param model
     * @param reportReservation Object containing inputs for the report
     * @return view to show report
     * @author BJM
     * @since 2024-10-10
     */
    @RequestMapping("/reservation/daterange/submit")
    public String reportReservationDateRangeSubmit(Model model, @ModelAttribute("reportInput") ReportReservation reportReservation) {

        ReservationDAO reservationDAO = new ReservationDAO();
        String start = reportReservation.getDateStart();
        String end = reportReservation.getDateEnd();

        // Use List<Reservation> instead of ArrayList<Reservation>
        List<Reservation> reservations = reservationDAO.selectAllByDateRange(start, end);
        reportReservation.setReservations((ArrayList<Reservation>) reservations);

        if (reservations != null && reservations.isEmpty()) {
            model.addAttribute("message", "No data found");
            System.out.println("No data found");
        }

        model.addAttribute("reportInput", reportReservation);

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
    @RequestMapping("/reservation/minlength/submit")
    public String reportReservationMinLengthSubmit(Model model, @ModelAttribute("reportInput") ReportReservation reportReservation) {

        //**********************************************************************
        // This could be done using the repository but there will be times when
        // jdbc will be useful.  For the reports, the requirements state that you
        // are to use jdbc to obtain the data for the report.
        //**********************************************************************
        ReservationDAO reservationDAO = new ReservationDAO();
        int minLength = reportReservation.getMinLength();

        // Use List<Reservation> instead of ArrayList<Reservation>
        ArrayList<Reservation> reservations = new ArrayList<>();

        reservations = (ArrayList<Reservation>) reservationDAO.selectAllWithMinLength(minLength);
        reportReservation.setReservations(reservations);

        if (reservations != null && reservations.isEmpty()) {
            model.addAttribute("message", "No data found");
            System.out.println("No data found");
        }

        model.addAttribute("reportInput", reportReservation);

        return "report/reportReservationMinLength";


}}