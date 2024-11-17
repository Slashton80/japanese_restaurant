package ca.hccis.restaurant.reservation.jpa.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Email(message = "Please provide a valid email")
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "reservationDateTime", nullable = false)
    private LocalDateTime reservationDateTime;

    @NotNull
    @Min(value = 0, message = "Number of adults cannot be negative")
    @Column(name = "numberOfAdults", nullable = false)
    private Integer numberOfAdults;

    @Min(value = 0, message = "Number of seniors cannot be negative")
    @Column(name = "numberOfSeniors")
    private Integer numberOfSeniors;

    @Min(value = 0, message = "Number of children cannot be negative")
    @Column(name = "numberOfChildren")
    private Integer numberOfChildren;

    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "1.0", inclusive = true)
    @Column(name = "couponDiscount", precision = 4, scale = 2)
    private Double couponDiscount;

    @Column(name = "totalCost", precision = 10, scale = 2)
    private BigDecimal totalCost;

    public static void setIdCounter(int idCounter) {

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

    // Method to calculate the total cost
    public BigDecimal
    calculateTotalCost() {
        final double COST_PER_ADULT = 25.0;
        final double SENIOR_DISCOUNT = 0.15;
        final double CHILD_DISCOUNT = 0.20;

        double cost = numberOfAdults * COST_PER_ADULT;
        if (numberOfSeniors != null) {
            cost += numberOfSeniors * COST_PER_ADULT * (1 - SENIOR_DISCOUNT);
        }
        if (numberOfChildren != null) {
            cost += numberOfChildren * COST_PER_ADULT * (1 - CHILD_DISCOUNT);
        }
        if (couponDiscount != null) {
            cost *= (1 - couponDiscount);
        }

        return BigDecimal.valueOf(cost).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setDateTime(LocalDateTime parseDateTime) {
        this.reservationDateTime = parseDateTime;
    }
}
