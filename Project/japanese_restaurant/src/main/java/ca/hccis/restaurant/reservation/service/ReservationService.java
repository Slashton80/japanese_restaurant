package ca.hccis.restaurant.reservation.service;

import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
import ca.hccis.restaurant.reservation.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class for managing reservation-related operations.
 *
 * <p>
 * This class provides business logic for handling reservations, such as fetching
 * today's reservations or performing other reservation-related operations.
 * </p>
 *
 * @author Sherri Ashton
 * @since 2024-11-19
 */
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * Fetches all reservations for today's date.
     *
     * @return A list of reservations made for today.
     */
    public List<Reservation> getReservationsForToday() {
        List<Reservation> reservations = reservationRepository.findReservationsForToday();
        System.out.println("Today's Reservations: " + reservations.size());
        return reservations;
    }

    /**
     * Validates that the end date is not before the start date.
     *
     * @param startDate The start date.
     * @param endDate The end date.
     * @return True if the end date is not before the start date, false otherwise.
     */
    public boolean validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null");
        }
        return !endDate.isBefore(startDate);
    }
}
