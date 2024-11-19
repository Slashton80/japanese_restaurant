package ca.hccis.restaurant.reservation.bo;

import ca.hccis.restaurant.reservation.dao.ReservationDAO;
import ca.hccis.restaurant.reservation.jpa.entity.CodeValue;
import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
import ca.hccis.restaurant.reservation.repositories.CodeValueRepository;
import ca.hccis.restaurant.reservation.util.CisUtilityFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Business Object class for managing operations related to Reservations.
 * This class includes methods for processing reports, calculating reservation costs,
 * setting default values, and managing reservation types.
 *
 * <p>
 * The class interacts with DAOs, repositories, and utility classes to perform
 * business-specific logic for the restaurant reservation system.
 * </p>
 *
 * <p>
 * Key functionalities include:
 * <ul>
 *     <li>Processing date range and minimum length reports</li>
 *     <li>Calculating reservation costs based on customer types and discounts</li>
 *     <li>Setting default values for reservations</li>
 *     <li>Loading reservation types into session attributes</li>
 * </ul>
 * </p>
 *
 * @author Sherri Ashton
 * @since 2024-11-16
 */
@Component
public class ReservationBO {

    /**
     * Processes reservations within a specific date range and writes the report to a file.
     *
     * <p>
     * This method uses the {@link ReservationDAO} to fetch reservations that fall within
     * the specified start and end dates. It also writes the resulting report to a file for further reference.
     * </p>
     *
     * @param start The start date of the range (format: "yyyy-MM-dd").
     * @param end   The end date of the range (format: "yyyy-MM-dd").
     * @return A list of {@link Reservation} objects within the specified date range.
     * @author Sherri Ashton
     * @since 2024-11-16
     */
    public static ArrayList<Reservation> processDateRangeReport(String start, String end) {

        //**********************************************************************
        // This could be done using the repository but there will be times when
        // jdbc will be useful.  For the reports, the requirements state that you
        // are to use jdbc to obtain the data for the report.
        //**********************************************************************
        ReservationDAO reservationDAO = new ReservationDAO();
        ArrayList<Reservation> reservations = reservationDAO.selectAllByDateRange(start, end);

        //writes the report to a file
        CisUtilityFile.writeReportToFile("dateRangeReport", reservations);

        return reservations;
    }

    /**
     * Processes reservations based on a minimum length and writes the report to a file.
     *
     * <p>
     * This method uses the {@link ReservationDAO} to fetch reservations that meet the
     * specified minimum length requirement. It also writes the resulting report to a file for further reference.
     * </p>
     *
     * @param minLength The minimum length of the reservation.
     * @return A list of {@link Reservation} objects meeting the minimum length requirement.
     * @throws SQLException If an SQL error occurs while fetching data.
     * @author Sherri Ashton
     * @since 2024-11-16
     */
    public static ArrayList<Reservation> processMinLengthReport(int minLength) throws SQLException {

        //**********************************************************************
        // This could be done using the repository but there will be times when
        // jdbc will be useful.  For the reports, the requirements state that you
        // are to use jdbc to obtain the data for the report.
        //**********************************************************************
        ReservationDAO reservationDAO = new ReservationDAO();
        ArrayList<Reservation> reservations = null;

        reservations = reservationDAO.selectAllWithMinLength(minLength);

        // writes the report to a file
        CisUtilityFile.writeReportToFile("minLengthReport", reservations);

        return reservations;
    }

    /**
     * Calculates the total cost of a reservation based on customer counts and discounts.
     *
     *
     * @param reservation The reservation object whose cost is to be calculated.
     * @author Sherri Ashton
     * @since 2024-11-16
     */

    public static void calculateReservationCost(Reservation reservation) {
        // Constants for pricing and discounts
        final double COST_PER_ADULT = 25.0;
        final double SENIOR_DISCOUNT = 0.15;
        final double CHILDREN_DISCOUNT = 0.20;

        // Validate inputs
        if (reservation.getNumberOfAdults() < 0 || reservation.getNumberOfSeniors() < 0 || reservation.getNumberOfChildren() < 0) {
            throw new IllegalArgumentException("Number of customers cannot be negative.");
        }

        // Calculate the total cost
        double totalCost = 0.0;

        // Cost for adults
        totalCost += reservation.getNumberOfAdults() * COST_PER_ADULT;

        // Cost for seniors (apply discount)
        totalCost += reservation.getNumberOfSeniors() * COST_PER_ADULT * (1 - SENIOR_DISCOUNT);

        // Cost for children (apply discount)
        totalCost += reservation.getNumberOfChildren() * COST_PER_ADULT * (1 - CHILDREN_DISCOUNT);

        // Apply coupon discount if available
        if (reservation.getCouponDiscount() != null && reservation.getCouponDiscount() > 0.0) {
            totalCost *= (1 - reservation.getCouponDiscount());
        }

        // Round to two decimal places
        BigDecimal roundedTotalCost = BigDecimal.valueOf(totalCost).setScale(2, RoundingMode.HALF_UP);
        reservation.setTotalCost(roundedTotalCost);
    }


    /**
     * Sets default values for a reservation object.
     *
     * <p>
     * This method initializes a reservation with default values, including:
     * <ul>
     *     <li>Number of adults: 1</li>
     *     <li>Number of seniors: 0</li>
     *     <li>Number of children: 0</li>
     *     <li>Coupon discount: 0.0</li>
     *     <li>Total cost: 25.00</li>
     * </ul>
     * </p>
     *
     * @param reservation The {@link Reservation} object to be initialized with default values.
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
    /**
     * Loads reservation types into the HTTP session if they are not already loaded.
     *
     * <p>
     * The method checks if the "reservationTypes" attribute is present in the session.
     * If not, it fetches the reservation types from the {@link CodeValueRepository} and
     * stores them in the session.
     * </p>
     *
     * @param _cvr The {@link CodeValueRepository} used to fetch reservation types.
     * @param session The current HTTP session where reservation types will be stored.
     * @author Sherri Ashton
     * @since 2024-11-16
     */
    public static void setReservationTypes(CodeValueRepository _cvr, HttpSession session) {
        List<CodeValue> reservationTypes = (List) session.getAttribute("reservationTypes");
        if (reservationTypes == null) {
            reservationTypes = _cvr.findByCodeTypeId(2);
            session.setAttribute("reservationTypes", reservationTypes);
        }
    }


}

