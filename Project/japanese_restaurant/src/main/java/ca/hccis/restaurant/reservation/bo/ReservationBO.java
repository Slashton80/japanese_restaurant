package ca.hccis.restaurant.reservation.bo;

import ca.hccis.restaurant.reservation.dao.ReservationDAO;
import ca.hccis.restaurant.reservation.jpa.entity.CodeValue;
import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
import ca.hccis.restaurant.reservation.repositories.CodeValueRepository;

import ca.hccis.restaurant.reservation.util.CisUtilityFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReservationBO {

//    private final ReservationService reservationService;
//    private final CodeValueRepository codeValueRepository;
//
//    @Autowired
//    public ReservationBO(ReservationService reservationServices) {
//        this.reservationService = reservationServices;
//    }
public static ArrayList<Reservation> processDateRangeReport(String start, String end){

    //**********************************************************************
    // This could be done using the repository but there will be times when
    // jdbc will be useful.  For the reports, the requirements state that you
    // are to use jdbc to obtain the data for the report.
    //**********************************************************************
    ReservationDAO reservationDAO = new ReservationDAO();
    ArrayList<Reservation>reservations = reservationDAO.selectAllByDateRange(start, end);

    //Also write the report to a file
    CisUtilityFile.writeReportToFile("dateRangeReport", reservations);

    return reservations;
}

    public static ArrayList<Reservation> processMinLengthReport(int minLength) throws SQLException {

        //**********************************************************************
        // This could be done using the repository but there will be times when
        // jdbc will be useful.  For the reports, the requirements state that you
        // are to use jdbc to obtain the data for the report.
        //**********************************************************************
        ReservationDAO reservationDAO = new ReservationDAO();
        ArrayList<Reservation> reservations = null;

        reservations = (ArrayList<Reservation>) reservationDAO.selectAllWithMinLength(minLength);

        //Also write the report to a file
        CisUtilityFile.writeReportToFile("minLengthReport", reservations);

        return reservations;
    }
    /**
     * Calculate the total cost of a reservation.
     * The cost is based on the number of adults, seniors, and children.
     * Applies discounts for seniors (15%) and children (20%).
     *
     * @param reservation The reservation to calculate the cost for.
     * @since 2024-11-08
     */
//    public void calculateReservationCost(Reservation reservation) {
//        if (reservation.getNumberOfAdults() < 0 || reservation.getNumberOfSeniors() < 0 || reservation.getNumberOfChildren() < 0) {
//            throw new IllegalArgumentException("Number of customers cannot be negative.");
//        }
//
//        // Constants for pricing and discounts
//        final double COST_PER_ADULT = 25.0;
//        final double SENIOR_DISCOUNT = 0.15;
//        final double CHILDREN_DISCOUNT = 0.20;
//
//        // Calculate the cost for adults (no discount)
//        double totalCost = reservation.getNumberOfAdults() * COST_PER_ADULT;
//
//        // Calculate the cost for seniors (15% discount)
//        double seniorCost = reservation.getNumberOfSeniors() * COST_PER_ADULT * (1 - SENIOR_DISCOUNT);
//
//        // Calculate the cost for children (20% discount)
//        double childrenCost = reservation.getNumberOfChildren() * COST_PER_ADULT * (1 - CHILDREN_DISCOUNT);
//
//        // Add all costs together
//        totalCost += seniorCost + childrenCost;
//
//        // Apply coupon discount if available
//        if (reservation.getCouponDiscount() != null) {
//            totalCost *= (1 - reservation.getCouponDiscount());
//        }
//
//        // Round the total cost to 2 decimal places
//        BigDecimal roundedTotalCost = new BigDecimal(totalCost).setScale(2, RoundingMode.HALF_UP);
//        reservation.setTotalCost(roundedTotalCost);
//
//    }
    public static double calculateReservationCost(Reservation reservation){

        reservation.setTotalCost(new BigDecimal(-1));
        return -1;

    }
    /**
     * Find reservations by a date range.
     * Uses ReservationService to get data from the database.
     *
     * @param dateStart Start date as a String in 'yyyy-MM-dd HH:mm' format.
     * @param dateEnd   End date as a String in 'yyyy-MM-dd HH:mm' format.
     * @return List of reservations within the date range.
     */
//    public List<Reservation> findReservationsByDateRange(LocalDateTime dateStart, LocalDateTime dateEnd) {
//        List<Reservation> reservations = reservationService.findReservationsByDateRange(dateStart, dateEnd);
//        // Apply additional processing if needed, for example, calculate the cost for each reservation
//        reservations.forEach(this::calculateReservationCost);
//        return reservations;
//    }

//    /**
//     * Process date range report.
//     * Uses ReservationService to get data from the database.
//     *
//     * @param dateStart Start date as LocalDateTime.
//     * @param dateEnd   End date as LocalDateTime.
//     * @return List of reservations within the date range.
//     */
//    public List<Reservation> processDateRangeReport(LocalDateTime dateStart, LocalDateTime dateEnd) {
//        return findReservationsByDateRange(dateStart, dateEnd);
//    }

//    /**
//     * Save or Update a reservation
//     * @param reservation The reservation object to save or update
//     */
//    public void saveOrUpdateReservation(Reservation reservation) {
//        calculateReservationCost(reservation); // Calculate cost before saving
//        reservationService.processAndSaveReservation(reservation);
//    }
//
//    /**
//     * Delete a reservation by ID
//     * @param id ID of the reservation to delete
//     */
//    public void deleteReservationById(Integer id) {
//        reservationService.deleteReservationById(id);
//    }

    /**
     * Set default values
     *
     * @param reservation
     * @author Sherri Ashton
     * @since 2024-11-14
     */
    public static void setReservationDefaults(Reservation reservation) {
        reservation.setNumberOfAdults(1);
        reservation.setNumberOfSeniors(0);
        reservation.setNumberOfChildren(0);
        reservation.setCouponDiscount(0.0);
        reservation.setTotalCost(new BigDecimal(25.00));
//        reservation.setReservationDateTime(CisUtility.getCurrentDate("yyyy-MM-dd"));


    }

    public static void setReservationTypes(CodeValueRepository _cvr, HttpSession session) {
        List<CodeValue> reservationTypes = (List) session.getAttribute("reservationTypes");
        if (reservationTypes == null) {
            reservationTypes = _cvr.findByCodeTypeId(2);
            session.setAttribute("reservationTypes", reservationTypes);
        }
    }

}

