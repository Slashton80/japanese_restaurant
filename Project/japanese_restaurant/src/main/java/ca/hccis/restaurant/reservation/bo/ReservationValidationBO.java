package ca.hccis.restaurant.reservation.bo;

import ca.hccis.restaurant.reservation.jpa.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ReservationValidationBO is a business object class used to perform custom business rule validations
 * for reservation objects in the restaurant reservation system.
 * This class contains methods for validating various aspects of a reservation, including:
 * * Ensuring the reservation date is not in the past.
 * * Ensuring there is at least one adult or one senior in the reservation.
 * * Ensuring children are properly supervised by at least one adult or one senior.
 *
 * @author: Sherri Ashton
 * @since 2024-12-06
 */
public class ReservationValidationBO {

    /**
     * Validates if the reservation date is not in the past.
     *
     * @param reservation The reservation to be validated.
     * @return True if the reservation date is valid (not in the past), false otherwise.
     */
    public static boolean validateReservationDate(Reservation reservation) {
        LocalDateTime reservationDateTime = reservation.getReservationDateTime();
        return reservationDateTime != null && !reservationDateTime.toLocalDate().isBefore(LocalDate.now());
    }

    /**
     * Validates if there is at least one adult or senior in the reservation.
     *
     * @param reservation The reservation to be validated.
     * @return True if there is at least one adult or senior, false otherwise.
     */
    public static boolean validateNumberOfAdultsOrSeniors(Reservation reservation) {
        Integer numberOfAdults = reservation.getNumberOfAdults() != null ? reservation.getNumberOfAdults() : 0;
        Integer numberOfSeniors = reservation.getNumberOfSeniors() != null ? reservation.getNumberOfSeniors() : 0;

        return (numberOfAdults > 0) || (numberOfSeniors > 0);
    }

    /**
     * Validates that there is at least one adult or senior if there are children in the reservation.
     *
     * @param reservation The reservation to be validated.
     * @return True if there is at least one adult or one senior supervising the children, false otherwise.
     */
    public static boolean validateChildrenSupervision(Reservation reservation) {
        Integer numberOfChildren = reservation.getNumberOfChildren() != null ? reservation.getNumberOfChildren() : 0;
        Integer numberOfAdults = reservation.getNumberOfAdults() != null ? reservation.getNumberOfAdults() : 0;
        Integer numberOfSeniors = reservation.getNumberOfSeniors() != null ? reservation.getNumberOfSeniors() : 0;

        return numberOfChildren == 0 || (numberOfAdults > 0 || numberOfSeniors > 0);
    }

}
