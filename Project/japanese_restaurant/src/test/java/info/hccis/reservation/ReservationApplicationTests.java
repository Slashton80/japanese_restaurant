package info.hccis.reservation;

import ca.hccis.restaurant.reservation.bo.ReservationValidationBO;
import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
import ca.hccis.restaurant.reservation.service.ReservationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static ca.hccis.restaurant.reservation.bo.ReservationValidationBO.validateNumberOfAdultsOrSeniors;
import static ca.hccis.restaurant.reservation.bo.ReservationValidationBO.validateReservationDate;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class ReservationApplicationTests {
    @Autowired
    private ReservationService reservationService;
    @Test
    public void testValidateReservationDate() {
        Reservation reservation = new Reservation();
        reservation.setReservationDateTime(LocalDateTime.now().plusDays(1));
        assertTrue(validateReservationDate(reservation), "Future date should be valid");

        reservation.setReservationDateTime(LocalDateTime.now().minusDays(1));
        assertFalse(validateReservationDate(reservation), "Past date should be invalid");
    }

    @Test
    public void testValidateNumberOfAdultsOrSeniors() {
        Reservation reservation = new Reservation();
        reservation.setNumberOfAdults(1);
        reservation.setNumberOfSeniors(0);
        assertTrue(validateNumberOfAdultsOrSeniors(reservation), "At least one adult should be valid");

        reservation.setNumberOfAdults(0);
        reservation.setNumberOfSeniors(1);
        assertTrue(validateNumberOfAdultsOrSeniors(reservation), "At least one senior should be valid");

        reservation.setNumberOfAdults(0);
        reservation.setNumberOfSeniors(0);
        assertFalse(validateNumberOfAdultsOrSeniors(reservation), "No adults or seniors should be invalid");
    }
    @Test
    public void testValidateDateRange() {
        LocalDate startDate = LocalDate.of(2024, 12, 1);
        LocalDate endDateBeforeStartDate = LocalDate.of(2024, 11, 30);
        LocalDate endDateAfterStartDate = LocalDate.of(2024, 12, 2);

        // Tests when end date is before start date
        boolean isValid = reservationService.validateDateRange(startDate, endDateBeforeStartDate);
        Assertions.assertFalse(isValid, "End date should not be before start date.");

        // Tests when end date is after start date
        isValid = reservationService.validateDateRange(startDate, endDateAfterStartDate);
        Assertions.assertTrue(isValid, "End date should be after start date.");
    }

}