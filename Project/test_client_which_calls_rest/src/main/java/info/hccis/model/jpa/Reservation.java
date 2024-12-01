package info.hccis.model.jpa;

import info.hccis.student.util.CisUtility;
//
//import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Reservation {

    private Integer id;
    private String name;
    private String email;
    private LocalDateTime reservationDateTime;
    private Integer numberOfAdults;
    private Integer numberOfSeniors;
    private Integer numberOfChildren;
    private Double couponDiscount;
    private BigDecimal totalCost;

    /**
     * Gather information from the user for a new reservation.
     */
    public void getInformation() {
        id = 0;  // ID will be auto-generated in the database.
        name = CisUtility.getInputString("Enter Customer Name");
        email = CisUtility.getInputString("Enter Email Address");
        reservationDateTime = CisUtility.getInputDateTime("Enter Reservation Date and Time (yyyy-MM-dd HH:mm)", "yyyy-MM-dd HH:mm");
        numberOfAdults = CisUtility.getInputInt("Enter Number of Adults");
        numberOfSeniors = CisUtility.getInputInt("Enter Number of Seniors");
        numberOfChildren = CisUtility.getInputInt("Enter Number of Children");
        couponDiscount = CisUtility.getInputDouble("Enter Coupon Discount (0.0 - 1.0)");

        // Calculate total cost based on input values.
        BigDecimal adultCost = BigDecimal.valueOf(numberOfAdults * 20.0);
        BigDecimal seniorCost = BigDecimal.valueOf(numberOfSeniors * 15.0);
        BigDecimal childrenCost = BigDecimal.valueOf(numberOfChildren * 10.0);
        BigDecimal total = adultCost.add(seniorCost).add(childrenCost);

        // Apply coupon discount if applicable
        if (couponDiscount != null && couponDiscount > 0) {
            total = total.subtract(total.multiply(BigDecimal.valueOf(couponDiscount)));
        }

        totalCost = total;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }

    public void setReservationDateTime(LocalDateTime reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
    }

    public Integer getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(Integer numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public Integer getNumberOfSeniors() {
        return numberOfSeniors;
    }

    public void setNumberOfSeniors(Integer numberOfSeniors) {
        this.numberOfSeniors = numberOfSeniors;
    }

    public Integer getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(Integer numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public Double getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(Double couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "Reservation: " +
                "\nid=" + id +
                "\nName='" + name + '\'' +
                "\nEmail='" + email + '\'' +
                "\nReservation Date and Time=" + reservationDateTime +
                "\nNumber of Adults=" + numberOfAdults +
                "\nNumber of Seniors=" + numberOfSeniors +
                "\nNumber of Children=" + numberOfChildren +
                "\nCoupon Discount=" + couponDiscount +
                "\nTotal Cost=$" + totalCost +
                "\n";
    }
}
