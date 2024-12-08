package ca.hccis.restaurant.reservation.jpa.entity;

//import jakarta.persistence.*;
//import jakarta.validation.constraints.*;
//import org.springframework.data.annotation.Transient;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import org.hibernate.annotations.Table;
//import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity class representing a reservation in the restaurant reservation system.
 * This class is mapped to the "reservation" table in the database and contains all the
 * attributes required to define a reservation, such as name, email, reservation date and time,
 * number of adults, seniors, and children, coupon discount, and total cost.
 * Validation annotations are applied to ensure data integrity, such as constraints on
 * minimum and maximum values, mandatory fields, and proper email format.
 * The entity is used in various operations, including creating, reading, updating,
 * and deleting reservations in the database.
 *
 * @author Sherri Ashton
 * @since 2024-11-16
 */
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

    @NotNull(message = "Reservation date and time cannot be null.")
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

    public void setDateTime(LocalDateTime parseDateTime) {
        this.reservationDateTime = parseDateTime;
    }

    public String getDateTime() {
        return null;
    }


    @Transient
    private String formattedReservationDateTime;

    public String getFormattedReservationDateTime() {
        return formattedReservationDateTime;
    }

    public void setFormattedReservationDateTime(String formattedReservationDateTime) {
        this.formattedReservationDateTime = formattedReservationDateTime;
    }


}

