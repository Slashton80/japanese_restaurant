package ca.hccis.restaurant.reservation.bo;

import ca.hccis.restaurant.reservation.jpa.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationValidationBO {

    /**
     * Validates if the reservation date is not in the past.
     *
     * @param reservation The reservation to be validated.
     * @return True if the reservation date is valid (not in the past), false otherwise.
     */
    public static boolean validateReservationDate(Reservation reservation) {
        LocalDateTime reservationDateTime = reservation.getReservationDateTime();
        return reservationDateTime != null && !reservationDateTime.toLocalDate().isBefore(LocalDateTime.now().toLocalDate());
    }

    /**
     * Validates if there is at least one adult or senior in the reservation.
     *
     * @param reservation The reservation to be validated.
     * @return True if there is at least one adult or senior, false otherwise.
     */
    public static boolean validateNumberOfAdultsOrSeniors(Reservation reservation) {
        return (reservation.getNumberOfAdults() != null && reservation.getNumberOfAdults() > 0) ||
                (reservation.getNumberOfSeniors() != null && reservation.getNumberOfSeniors() > 0);
    }

    /**
     * Validates that there is at least one adult if there are children in the reservation.
     *
     * @param reservation The reservation to be validated.
     * @return True if there is at least one adult for every child in the reservation, false otherwise.
     */
    public static boolean validateChildrenSupervision(Reservation reservation) {
        return reservation.getNumberOfChildren() == null || reservation.getNumberOfChildren() == 0 ||
                (reservation.getNumberOfAdults() != null && reservation.getNumberOfAdults() > 0);
    }

}
