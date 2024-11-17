package ca.hccis.restaurant.reservation.controller;

import ca.hccis.restaurant.reservation.bo.ReservationBO;
import ca.hccis.restaurant.reservation.entity.ReportReservation;
import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
import ca.hccis.restaurant.reservation.util.CisUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    private final ReservationBO reservationBO;

    @Autowired
    public ReportController(ReservationBO reservationBO) {
        this.reservationBO = reservationBO;
    }

    @RequestMapping("")
    public String home(Model model, HttpSession session) {
        logger.info("Running the reports controller base method");
        return "report/list";
    }

    @GetMapping("/reservation/daterange")
    public String reportReservationDateRange(Model model, HttpSession session) {
        String currentDate = String.valueOf(CisUtility.getCurrentDate("yyyy-MM-dd"));
        session.setAttribute("currentDate", currentDate);

        String start = CisUtility.getCurrentDate(-30, "yyyy-MM-dd");
        String end = CisUtility.getCurrentDate(+30, "yyyy-MM-dd");

        ReportReservation reportReservation = new ReportReservation();
        reportReservation.setDateStart(start);
        reportReservation.setDateEnd(end);

        model.addAttribute("reportInput", reportReservation);
        return "report/reportReservationDateRange";
    }

    @PostMapping("/reservation/daterange/submit")
    public String reportReservationDateRangeSubmit(Model model, @ModelAttribute("reportInput") ReportReservation reportReservation) {
        try {
            logger.info("Processing date range report: start={}, end={}", reportReservation.getDateStart(), reportReservation.getDateEnd());

            // Parse dates to LocalDateTime for repository query
            String startDate = String.valueOf(LocalDate.parse(reportReservation.getDateStart(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay());
            LocalDateTime endDate = LocalDate.parse(reportReservation.getDateEnd(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).atTime(23, 59, 59);

            List<Reservation> reservations = reservationBO.findReservationsByDateRange(LocalDateTime.parse(startDate), LocalDateTime.parse(String.valueOf(endDate)));

            if (reservations == null || reservations.isEmpty()) {
                model.addAttribute("message", "No reservations found for the selected date range.");
                logger.info("No data found for the selected date range.");
            } else {
                reportReservation.setReservations(new ArrayList<>(reservations));
                logger.info("Found {} reservations", reservations.size());
            }

            model.addAttribute("reportInput", reportReservation);
        } catch (Exception e) {
            logger.error("Error processing date range report", e);
            model.addAttribute("message", "An error occurred while processing your request. Please try again later.");
        }

        return "report/reportReservationDateRange";
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
}



