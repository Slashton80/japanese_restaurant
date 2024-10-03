package ca.hccis.restaurant.reservation.entity;

import ca.hccis.restaurant.reservation.exception.ReservationException;
import org.junit.jupiter.api.Assertions;



/**
 * Unit tests for the Reservation class, focusing on calculating the total cost,
 * applying discounts, and verifying various reservation properties.
 *
 * @author Sherri Ashton
 * @since 2024-09-27
 */
class ReservationTest {

    @org.junit.jupiter.api.Test
    void testCalculateTotalCost() {
        Reservation reservation = new Reservation();
        reservation.setName("John Doe");
        reservation.setNumberOfAdults(3);
        double expectedTotalCost = 3 * 25.0;
        Assertions.assertEquals(75.0, reservation.calculateTotalCost(), 0.01, "Total cost should be 75.0 for 3 customers.");
    }

    @org.junit.jupiter.api.Test
    void testCalculateTotalCostWithZeroCustomers() {
        Reservation reservation = new Reservation();
        reservation.setNumberOfAdults(0);
        reservation.setNumberOfChildren(0);
        reservation.setNumberOfSeniors(0);
        Assertions.assertEquals(0.0, reservation.calculateTotalCost(), 0.01);
    }

    @org.junit.jupiter.api.Test
    void testNegativeCustomers() {
        Reservation reservation = new Reservation();
        reservation.setNumberOfAdults(-1);
        Assertions.assertThrows(ReservationException.class, reservation::calculateTotalCost,
                "Negative number of customers should throw a ReservationException.");
    }


    @org.junit.jupiter.api.Test
    void testCouponDiscountApplied() {
        Reservation reservation = new Reservation();
        reservation.setNumberOfAdults(4);
        reservation.setCouponDiscount(0.30);
        double totalCost = reservation.calculateTotalCost();
        double expectedCost = 4 * 25.0 * (1 - 0.30);
        Assertions.assertEquals(expectedCost, totalCost, 0.01, "The total cost should apply the coupon discount.");
    }

    @org.junit.jupiter.api.Test
    void testEmailNotSet() {
        Reservation reservation = new Reservation();
        Assertions.assertNull(reservation.getEmail(), "Email should be null if not set.");
    }

    @org.junit.jupiter.api.Test
    void testIdsAreUnique() {
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        Assertions.assertNotEquals(reservation1.getId(), reservation2.getId(), "Reservation IDs should be unique.");
    }

    @org.junit.jupiter.api.Test
    void testReservationFields() {
        Reservation reservation = new Reservation();
        reservation.setNumberOfAdults(2);
        reservation.setNumberOfChildren(1);
        reservation.setNumberOfSeniors(1);

        int[] expectedCustomers = {2, 1, 1};
        int[] actualCustomers = {reservation.getNumberOfAdults(), reservation.getNumberOfChildren(), reservation.getNumberOfSeniors()};

        Assertions.assertArrayEquals(expectedCustomers, actualCustomers, "The number of adults, children, and seniors should match.");
    }

    @org.junit.jupiter.api.Test
    void testNoDiscountsApplied() {
        Reservation reservation = new Reservation();
        reservation.setNumberOfAdults(5);
        reservation.setCouponDiscount(0.0);
        double totalCost = reservation.calculateTotalCost();
        double expectedCost = 5 * 25.0;
        Assertions.assertEquals(expectedCost, totalCost, 0.01, "Total cost should be correct without any discounts.");
    }

    @org.junit.jupiter.api.Test
    void testCombinationOfDiscounts() {
        Reservation reservation = new Reservation();
        reservation.setNumberOfAdults(3);
        reservation.setNumberOfSeniors(2);
        reservation.setNumberOfChildren(1);
        reservation.setCouponDiscount(0.20);

        double adultCost = 3 * 25.0;
        double seniorCost = 2 * 25.0 * (1 - 0.15);
        double childCost = 1 * 25.0 * (1 - 0.20);
        double expectedCost = (adultCost + seniorCost + childCost) * (1 - 0.20);

        Assertions.assertEquals(expectedCost, reservation.calculateTotalCost(), 0.01,
                "Total cost should correctly apply senior, children, and coupon discounts.");
    }

    @org.junit.jupiter.api.Test
    void testSeniorDiscount() {
        Reservation reservation = new Reservation();
        reservation.setNumberOfSeniors(2);
        double expectedCost = 2 * 25.0 * (1 - 0.15);
        Assertions.assertEquals(expectedCost, reservation.calculateTotalCost(), 0.01, "Senior discount should be applied.");
    }

    @org.junit.jupiter.api.Test
    void testAdultCost() {
        Reservation reservation = new Reservation();
        reservation.setNumberOfAdults(3);
        double expectedCost = 3 * 25.0;
        Assertions.assertEquals(expectedCost, reservation.calculateTotalCost(), 0.01, "Adult cost should be calculated correctly.");
    }
    @org.junit.jupiter.api.Test
    void testInvalidReservationThrowsException() {
        Reservation reservation = new Reservation();
        reservation.setNumberOfAdults(-1); // Invalid number of adults
        Assertions.assertThrows(ReservationException.class, reservation::calculateTotalCost,
                "Invalid reservation data should throw a ReservationException.");
    }

}