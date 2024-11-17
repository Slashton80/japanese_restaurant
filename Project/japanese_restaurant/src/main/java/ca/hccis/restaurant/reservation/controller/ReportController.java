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
import java.util.ArrayList;

@Controller
@RequestMapping("/report")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

//    private final ReservationBO reservationBO;

//    @Autowired
//    public ReportController(ReservationBO reservationBO) {
//        this.reservationBO = reservationBO;
//    }

    @RequestMapping("")
    public String home(Model model, HttpSession session) {
        logger.info("Running the reports controller base method");
        return "report/list";
    }

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
//        // Calculate start and end dates manually
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        LocalDateTime startDate = currentDateTime.minusDays(30);
//        LocalDateTime endDate = currentDateTime.plusDays(30);
//
//        // Format the dates using DateTimeFormatter
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String start = startDate.format(formatter);
//        String end = endDate.format(formatter);
//
//        // Set the date range in the report reservation
//        ReportReservation reportReservation = new ReportReservation();
//        reportReservation.setDateStart(start);
//        reportReservation.setDateEnd(end);

        // Add the report reservation to the model
        model.addAttribute("reportInput", reportReservation);

        return "report/reportReservationDateRange";
    }


    @PostMapping("/reservation/daterange/submit")
    public String reportReservationDateRangeSubmit(Model model, @ModelAttribute("reportInput") ReportReservation reportReservation) {
        //Call BO method to process the report
        ArrayList<Reservation> reservations = ReservationBO.processDateRangeReport(reportReservation.getDateStart(), reportReservation.getDateEnd());

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
//        try {
//            logger.info("Processing date range report: start={}, end={}", reportReservation.getDateStart(), reportReservation.getDateEnd());
//
//            // Parse dates to LocalDateTime for repository query
//            String startDate = String.valueOf(LocalDate.parse(reportReservation.getDateStart(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay());
//            LocalDateTime endDate = LocalDate.parse(reportReservation.getDateEnd(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).atTime(23, 59, 59);
//
//            List<Reservation> reservations = reservationBO.findReservationsByDateRange(LocalDateTime.parse(startDate), LocalDateTime.parse(String.valueOf(endDate)));
//
//            if (reservations == null || reservations.isEmpty()) {
//                model.addAttribute("message", "No reservations found for the selected date range.");
//                logger.info("No data found for the selected date range.");
//            } else {
//                reportReservation.setReservations(new ArrayList<>(reservations));
//                logger.info("Found {} reservations", reservations.size());
//            }

//            model.addAttribute("reportInput", reportReservation);
//        } catch (Exception e) {
//            logger.error("Error processing date range report", e);
//            model.addAttribute("message", "An error occurred while processing your request. Please try again later.");
//        }
//
//        return "report/reportReservationDateRange";
    }
//    @GetMapping("/daterange")
//    public String reportByDateRange(Model model) {
//        model.addAttribute("reportInput", new ReportReservation());
//        return "report/reportReservationDateRange";
//    }
//
//    @PostMapping("/daterange/submit")
//    public String reportByDateRangeSubmit(@ModelAttribute("reportInput") ReportReservation reportReservation, Model model) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        LocalDateTime startDate = LocalDateTime.parse(reportReservation.getDateStart(), formatter);
//        LocalDateTime endDate = LocalDateTime.parse(reportReservation.getDateEnd(), formatter);
//
//        model.addAttribute("reservations", reservationService.findReservationsByDateRange(startDate, endDate));
//        return "report/reportReservationDateRange";
//    }
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



