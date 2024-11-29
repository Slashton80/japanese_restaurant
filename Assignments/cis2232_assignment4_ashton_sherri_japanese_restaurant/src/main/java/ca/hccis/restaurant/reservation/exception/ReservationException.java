package ca.hccis.restaurant.reservation.exception;

/**
 * Custom exception class for handling reservation-related errors.
 *
 * @author Sherri Ashton
 * @since 2024-09-27
 */
public class ReservationException extends RuntimeException {

    public ReservationException(String message) {
        super(message);
    }

    public ReservationException() {
        super();
    }
}
