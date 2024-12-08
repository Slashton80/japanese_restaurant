package info.hccis.reservation;

import ca.hccis.restaurant.reservation.bo.ReservationBO;
import ca.hccis.restaurant.reservation.bo.ReservationValidationBO;
import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
import ca.hccis.restaurant.reservation.service.ReservationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static ca.hccis.restaurant.reservation.bo.ReservationValidationBO.validateNumberOfAdultsOrSeniors;
import static ca.hccis.restaurant.reservation.bo.ReservationValidationBO.validateReservationDate;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for the Reservation Application.
 * These tests cover business rules, validation methods, and cost calculation logic for reservations.
 * <p>
 * Tests include:
 * - Validation of reservation dates.
 * - Validation of adults, seniors, and children booking constraints.
 * - Calculation of total reservation costs, including discounts.
 * - Handling of null and edge case values for validations.
 *
 * @author Sherri Ashton
 * @since 2024-12-8
 */
class ReservationApplicationTests {

    private ReservationService reservationService;

    /**
     * Tests the validation of reservation dates.
     * Ensures that future dates are valid, and past dates are invalid.
     */
    @Test
    public void testValidateReservationDate() {
        Reservation reservation = new Reservation();
        reservation.setReservationDateTime(LocalDateTime.now().plusDays(1));
        assertTrue(validateReservationDate(reservation), "Future date should be valid");

        reservation.setReservationDateTime(LocalDateTime.now().minusDays(1));
        assertFalse(validateReservationDate(reservation), "Past date should be invalid");
    }

    /**
     * Tests the validation of whether there is at least one adult or senior in the reservation.
     */
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

    /**
     * Tests the validation of children supervision by adults or seniors.
     */
    @Test
    public void testValidateChildrenSupervision() {
        Reservation reservation = new Reservation();

        reservation.setNumberOfChildren(0);
        Assertions.assertTrue(ReservationValidationBO.validateChildrenSupervision(reservation), "No children should always be valid");

        reservation.setNumberOfChildren(2);
        reservation.setNumberOfAdults(1);
        Assertions.assertTrue(ReservationValidationBO.validateChildrenSupervision(reservation), "Children supervised by an adult should be valid");

        reservation.setNumberOfChildren(2);
        reservation.setNumberOfAdults(0);
        reservation.setNumberOfSeniors(0);
        Assertions.assertFalse(ReservationValidationBO.validateChildrenSupervision(reservation), "Children without supervision should be invalid");
    }

    /**
     * Tests the validation of date ranges for reports or filtering.
     * Ensures the start date is before the end date.
     */
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

    /**
     * Tests validation of adults or seniors when null values are provided for counts.
     */
    @Test
    public void testValidateNumberOfAdultsOrSeniorsWithNullValues() {
        Reservation reservation = new Reservation();

        // Case 1: Null values for adults and seniors
        reservation.setNumberOfAdults(null);
        reservation.setNumberOfSeniors(null);
        Assertions.assertFalse(ReservationValidationBO.validateNumberOfAdultsOrSeniors(reservation),
                "Null values for adults and seniors should be invalid");

        // Case 2: Zero values for adults and seniors
        reservation.setNumberOfAdults(0);
        reservation.setNumberOfSeniors(0);
        Assertions.assertFalse(ReservationValidationBO.validateNumberOfAdultsOrSeniors(reservation),
                "Zero values for adults and seniors should be invalid");

        // Case 3: Null value for adults, valid value for seniors
        reservation.setNumberOfAdults(null);
        reservation.setNumberOfSeniors(1);
        Assertions.assertTrue(ReservationValidationBO.validateNumberOfAdultsOrSeniors(reservation),
                "Null value for adults but valid senior count should be valid");

        // Case 4: Null value for seniors, valid value for adults
        reservation.setNumberOfAdults(1);
        reservation.setNumberOfSeniors(null);
        Assertions.assertTrue(ReservationValidationBO.validateNumberOfAdultsOrSeniors(reservation),
                "Null value for seniors but valid adult count should be valid");
    }

    /**
     * Tests validation of children supervision with null values for adults or seniors.
     */
    @Test
    public void testValidateChildrenSupervisionWithNullValues() {
        Reservation reservation = new Reservation();

        // Case 1: Null values for all
        reservation.setNumberOfChildren(null);
        reservation.setNumberOfAdults(null);
        reservation.setNumberOfSeniors(null);
        Assertions.assertTrue(ReservationValidationBO.validateChildrenSupervision(reservation),
                "Null values for all should be valid (no children booked)");

        // Case 2: Null values for adults and seniors, valid value for children
        reservation.setNumberOfChildren(1);
        reservation.setNumberOfAdults(null);
        reservation.setNumberOfSeniors(null);
        Assertions.assertFalse(ReservationValidationBO.validateChildrenSupervision(reservation),
                "Children with null values for adults and seniors should be invalid");

        // Case 3: Null value for adults, valid seniors supervising children
        reservation.setNumberOfChildren(2);
        reservation.setNumberOfAdults(null);
        reservation.setNumberOfSeniors(1);
        Assertions.assertTrue(ReservationValidationBO.validateChildrenSupervision(reservation),
                "Children supervised by seniors should be valid");

        // Case 4: Null value for seniors, valid adults supervising children
        reservation.setNumberOfChildren(2);
        reservation.setNumberOfAdults(1);
        reservation.setNumberOfSeniors(null);
        Assertions.assertTrue(ReservationValidationBO.validateChildrenSupervision(reservation),
                "Children supervised by adults should be valid");

        // Case 5: Valid values for all
        reservation.setNumberOfChildren(2);
        reservation.setNumberOfAdults(1);
        reservation.setNumberOfSeniors(1);
        Assertions.assertTrue(ReservationValidationBO.validateChildrenSupervision(reservation),
                "Children supervised by both adults and seniors should be valid");
    }

    /**
     * Tests validation of reservation dates, including null, future, and past dates.
     * Validates that:
     * - A null reservation date is considered invalid.
     * - A reservation date in the future is considered valid.
     * - A reservation date in the past is considered invalid.
     */
    @Test
    public void testValidateReservationDateWithNullValues() {
        Reservation reservation = new Reservation();

        // Case 1: Null reservation date
        reservation.setReservationDateTime(null);
        Assertions.assertFalse(ReservationValidationBO.validateReservationDate(reservation),
                "Null reservation date should be invalid");

        // Case 2: Valid future reservation date
        reservation.setReservationDateTime(LocalDateTime.now().plusDays(1));
        Assertions.assertTrue(ReservationValidationBO.validateReservationDate(reservation),
                "Future reservation date should be valid");

        // Case 3: Past reservation date
        reservation.setReservationDateTime(LocalDateTime.now().minusDays(1));
        Assertions.assertFalse(ReservationValidationBO.validateReservationDate(reservation),
                "Past reservation date should be invalid");
    }

    /**
     * Tests calculation of the total cost for a reservation.
     * Validates cost calculation logic for adults, seniors, children, and coupon discounts.
     */
    @Test
    public void testCalculateReservationCost() {
        Reservation reservation = new Reservation();
        reservation.setNumberOfAdults(2);
        reservation.setNumberOfSeniors(1);
        reservation.setNumberOfChildren(3);
        reservation.setCouponDiscount(0.3); // 30% discount

        // Calls the calculateReservationCost method
        ReservationBO.calculateReservationCost(reservation);

        // Validates the calculated cost in the Reservation object
        BigDecimal expectedCost = new BigDecimal("91.88"); // Pre-calculated
        assertEquals(expectedCost, reservation.getTotalCost(), "Total cost calculation failed.");
    }

}