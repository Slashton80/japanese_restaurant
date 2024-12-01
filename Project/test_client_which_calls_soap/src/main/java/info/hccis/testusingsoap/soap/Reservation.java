
package info.hccis.testusingsoap.soap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Java class for Reservation.
 * This is used in the SOAP service to represent reservation data.
 * It contains fields like name, email, reservation date and time, etc.
 *
 * @author Sherri Ashton
 * @since 2024-11-28
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reservation", propOrder = {
        "id",
        "name",
        "email",
        "reservationDateTime",
        "numberOfAdults",
        "numberOfSeniors",
        "numberOfChildren",
        "couponDiscount",
        "totalCost"
})
public class Reservation {

    protected Integer id;
    protected String name;
    protected String email;
    protected LocalDateTime reservationDateTime;
    protected Integer numberOfAdults;
    protected Integer numberOfSeniors;
    protected Integer numberOfChildren;
    protected Double couponDiscount;
    protected BigDecimal totalCost;

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

}
