package ca.hccis.restaurant.reservation.entity;

import ca.hccis.restaurant.reservation.exception.ReservationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * This class represents a reservation made at the restaurant.
 *
 * @author sherri ashton
 * @since 2024-09-20
 */
public class Reservation {
    private static int idCounter = 1;
    private final int id;
    private String name;
    private int numberOfAdults;
    private int numberOfSeniors;
    private int numberOfChildren;
    //private int numberOfCustomers;
    private LocalDateTime dateTime;
    private String email;
    private double couponDiscount;
    private static final double COST_PER_CUSTOMER = 25.0;
    private static final double CHILDREN_DISCOUNT = 0.20;
    private static final double SENIOR_DISCOUNT = 0.15;

    // Declares the DateTimeFormatter at the class level
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Reservation() {
        this.id = idCounter;
        idCounter++;
    }

    /**
     * This method allows a setting idCounter externally
     *
     * @author sherri ashton
     * @since 2024-09-20
     */
    public static void setIdCounter(int newIdCounter) {
        idCounter = newIdCounter;
    }


    public void getInformation() {
        Scanner input = new Scanner(System.in);


        System.out.println("\n--- Enter Reservation Details ---");

        System.out.println("Name: ");
        name = input.nextLine();

        System.out.println("Email: ");
        email = input.nextLine();

        boolean validDate = false;
        while (!validDate) {
            System.out.println("Date and Time (YYYY-MM-DD HH:MM): ");
            String dateInput = input.nextLine();
            try {
                dateTime = LocalDateTime.parse(dateInput, DATE_TIME_FORMATTER);
                validDate = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format! Please use the format 'YYYY-MM-DD HH:MM'.");
            }
        }

        System.out.println("Number of Adults: ");
        numberOfAdults = input.nextInt();
        input.nextLine();

        System.out.println("Number of Seniors: ");
        numberOfSeniors = input.nextInt();
        input.nextLine();

        System.out.println("Number of Children: ");
        numberOfChildren = input.nextInt();
        input.nextLine();

        System.out.println("Coupon Discount (as a percentage, e.g., 30 for 30% off): ");
        //stores as a decimal
        couponDiscount = input.nextDouble() / 100;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getEmail() {
        return email;
    }

    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    public int getNumberOfSeniors() {
        return numberOfSeniors;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfSeniors(int numberOfSeniors) {
        this.numberOfSeniors = numberOfSeniors;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public void setCouponDiscount(double couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    /**
     * This method calculate the total cost of the reservation based on the number of customers,
     * applying discounts for seniors, children, and any provided coupon discount.
     *
     * @author sherri ashton
     * @since 2024-09-25
     */
    public double calculateTotalCost() {
        if (numberOfAdults < 0 || numberOfSeniors < 0 || numberOfChildren < 0) {
            throw new ReservationException("Number of customers cannot be negative.");
        }

        // Calculate the cost for adults (no discount)
        double totalCost = numberOfAdults * COST_PER_CUSTOMER;

        // Calculate the cost for seniors (15% off)
        double seniorCost = numberOfSeniors * COST_PER_CUSTOMER * (1 - SENIOR_DISCOUNT);

        // Calculate the cost for children (20% off)
        double childrenCost = numberOfChildren * COST_PER_CUSTOMER * (1 - CHILDREN_DISCOUNT);

        // Add the costs for seniors and children to the total
        totalCost += seniorCost + childrenCost;

        // Apply coupon discount if present
        if (couponDiscount > 0) {
            totalCost = totalCost * (1 - couponDiscount);
        }
        return totalCost;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + id + System.lineSeparator() +
                "Name: " + name + System.lineSeparator() +
                "Email: " + email + System.lineSeparator() +
                "Date and Time: " + dateTime.format(DATE_TIME_FORMATTER) + System.lineSeparator() +
                "Number of Adults (Age 11-64): " + numberOfAdults + System.lineSeparator() +
                "Number of Seniors (Over 65 - 15% off): " + numberOfSeniors + System.lineSeparator() +
                "Number of Children (Under 10 - 20% off): " + numberOfChildren + System.lineSeparator() +
                "Coupon Discount: " + (couponDiscount * 100) + "%" + System.lineSeparator() +
                "Total Cost: $" + String.format("%.2f", calculateTotalCost()) + System.lineSeparator();
    }


}
