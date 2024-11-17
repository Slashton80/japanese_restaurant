package ca.hccis.restaurant.reservation.bo;

import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
import ca.hccis.restaurant.reservation.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReservationBO {

    private final ReservationService reservationService;

    @Autowired
    public ReservationBO(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * Calculate the total cost of a reservation.
     * The cost is based on the number of adults, seniors, and children.
     * Applies discounts for seniors (15%) and children (20%).
     *
     * @param reservation The reservation to calculate the cost for.
     * @since 2024-11-08
     */
    public static double calculateReservationCost(Reservation reservation) {
        if (reservation.getNumberOfAdults() < 0 || reservation.getNumberOfSeniors() < 0 || reservation.getNumberOfChildren() < 0) {
            throw new IllegalArgumentException("Number of customers cannot be negative.");
        }

        // Constants for pricing and discounts
        final double COST_PER_ADULT = 25.0;
        final double SENIOR_DISCOUNT = 0.15;
        final double CHILDREN_DISCOUNT = 0.20;

        // Calculate the cost for adults (no discount)
        double totalCost = reservation.getNumberOfAdults() * COST_PER_ADULT;

        // Calculate the cost for seniors (15% discount)
        double seniorCost = reservation.getNumberOfSeniors() * COST_PER_ADULT * (1 - SENIOR_DISCOUNT);

        // Calculate the cost for children (20% discount)
        double childrenCost = reservation.getNumberOfChildren() * COST_PER_ADULT * (1 - CHILDREN_DISCOUNT);

        // Add all costs together
        totalCost += seniorCost + childrenCost;

        // Apply coupon discount if available
        if (reservation.getCouponDiscount() != null) {
            totalCost *= (1 - reservation.getCouponDiscount());
        }

        // Round the total cost to 2 decimal places
        BigDecimal roundedTotalCost = new BigDecimal(totalCost).setScale(2, RoundingMode.HALF_UP);
        reservation.setTotalCost(roundedTotalCost);

    }

    /**
     * Find reservations by a date range.
     * Uses ReservationService to get data from the database.
     *
     * @param dateStart Start date as a String in 'yyyy-MM-dd HH:mm' format.
     * @param dateEnd   End date as a String in 'yyyy-MM-dd HH:mm' format.
     * @return List of reservations within the date range.
     */
    public List<Reservation> findReservationsByDateRange(LocalDateTime dateStart, LocalDateTime dateEnd) {
        List<Reservation> reservations = reservationService.findReservationsByDateRange(dateStart, dateEnd);
        // Apply additional processing if needed, for example, calculate the cost for each reservation
        reservations.forEach(this::calculateReservationCost);
        return reservations;
    }

    /**
     * Process date range report.
     * Uses ReservationService to get data from the database.
     *
     * @param dateStart Start date as LocalDateTime.
     * @param dateEnd   End date as LocalDateTime.
     * @return List of reservations within the date range.
     */
    public List<Reservation> processDateRangeReport(LocalDateTime dateStart, LocalDateTime dateEnd) {
        return findReservationsByDateRange(dateStart, dateEnd);
    }
}
