package ca.hccis.restaurant.reservation.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationCalculatorTest {

    @Test
    public void testCalculateTotalCost() {
        Reservation reservation = new Reservation();
        reservation.setName("John Doe");
        reservation.setNumberOfAdults(3);
        double expectedTotalCost = 3 * 25.0;
        assertEquals(75.0, reservation.calculateTotalCost(), 0.01, "Total cost should be 75.0 for 3 customers.");
    }

    @Test
    public void testCalculateTotalCostWithZeroCustomers() {
        Reservation reservation = new Reservation();
        reservation.setNumberOfAdults(0);
        reservation.setNumberOfChildren(0);
        reservation.setNumberOfSeniors(0);
        assertEquals(0.0, reservation.calculateTotalCost(), 0.01);
    }

    @Test
    public void testNegativeCustomers() {
        Reservation reservation = new Reservation();
        reservation.setNumberOfAdults(-1); // Setting a negative number of customers
        assertThrows(IllegalArgumentException.class, reservation::calculateTotalCost,
                "Negative number of customers should throw an exception.");
    }
    @Test
    public void testCouponDiscountApplied() {
        Reservation reservation = new Reservation();
        reservation.setNumberOfAdults(4);
        reservation.setCouponDiscount(0.30);
        double totalCost = reservation.calculateTotalCost();
        double expectedCost = 4 * 25.0 * (1 - 0.30); // 30% off
        assertEquals(expectedCost, totalCost, 0.01, "The total cost should apply the coupon discount.");
    }
    @Test
    public void testEmailNotSet() {
        Reservation reservation = new Reservation();
        assertNull(reservation.getEmail(), "Email should be null if not set.");
    }
    @Test
    public void testIdsAreUnique() {
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        assertNotEquals(reservation1.getId(), reservation2.getId(), "Reservation IDs should be unique.");
    }
    @Test
    public void testMultipleCustomersWithCouponDiscount() {
        Reservation reservation = new Reservation();
        reservation.setNumberOfAdults(2);
        reservation.setNumberOfChildren(1);
        reservation.setCouponDiscount(0.30);
        double totalCost = reservation.calculateTotalCost();

        double expectedCost = 50.0 + (25.0 * (1 - 0.20)); // 2 adults + 1 child (20% off)
        expectedCost = expectedCost * (1 - 0.30);

        assertEquals(expectedCost, totalCost, 0.01, "Total cost should be correctly calculated after discounts and coupon.");
    }
    @Test
    public void testReservationFields() {
        Reservation reservation = new Reservation();
        reservation.setNumberOfAdults(2);
        reservation.setNumberOfChildren(1);
        reservation.setNumberOfSeniors(1);

        int[] expectedCustomers = {2, 1, 1};
        int[] actualCustomers = {reservation.getNumberOfAdults(), reservation.getNumberOfChildren(), reservation.getNumberOfSeniors()};

        assertArrayEquals(expectedCustomers, actualCustomers, "The number of adults, children, and seniors should match.");
    }
    @Test
    public void testSeniorDiscountApplied() {
        Reservation reservation = new Reservation();
        reservation.setNumberOfSeniors(2); // 2 seniors
        double totalCost = reservation.calculateTotalCost();
        double expectedCost = 2 * 25.0 * (1 - 0.15); // 2 seniors, 15% discount
        assertEquals(expectedCost, totalCost, 0.01, "Total cost should apply senior discount.");
    }

    @Test
    public void testChildrenDiscountApplied() {
        Reservation reservation = new Reservation();
        reservation.setNumberOfChildren(3); // 3 children
        double totalCost = reservation.calculateTotalCost();
        double expectedCost = 3 * 25.0 * (1 - 0.20); // 3 children, 20% discount
        assertEquals(expectedCost, totalCost, 0.01, "Total cost should apply children discount.");
    }
    @Test
    public void testNoDiscountsApplied() {
        Reservation reservation = new Reservation();
        reservation.setNumberOfAdults(5); // 5 adults, no discount
        reservation.setCouponDiscount(0.0); // No coupon discount
        double totalCost = reservation.calculateTotalCost();
        double expectedCost = 5 * 25.0; // 5 adults, no discount
        assertEquals(expectedCost, totalCost, 0.01, "Total cost should be correct without any discounts.");
    }
    @Test
    public void testCombinationOfDiscounts() {
        Reservation reservation = new Reservation();
        reservation.setNumberOfAdults(3); // 3 adults
        reservation.setNumberOfSeniors(2); // 2 seniors, 15% discount
        reservation.setNumberOfChildren(1); // 1 child, 20% discount
        reservation.setCouponDiscount(0.20); // 20% coupon discount
        double totalCost = reservation.calculateTotalCost();

        double expectedCost = (3 * 25.0) // Adults full price
                + (2 * 25.0 * (1 - 0.15)) // Seniors with 15% discount
                + (1 * 25.0 * (1 - 0.20)); // Child with 20% discount
        expectedCost = expectedCost * (1 - 0.20); // Apply coupon discount

        assertEquals(expectedCost, totalCost, 0.01, "Total cost should correctly apply senior, children, and coupon discounts.");
    }

}

