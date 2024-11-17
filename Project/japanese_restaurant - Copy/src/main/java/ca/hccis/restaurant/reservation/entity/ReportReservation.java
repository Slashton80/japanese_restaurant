package ca.hccis.restaurant.reservation.entity;

import ca.hccis.restaurant.reservation.jpa.entity.Reservation;

import java.util.ArrayList;

/**
 * Entity class to hold the attributes of the reservation related reports.
 *
 * @author Sherri Ashton
 * @since 2024-10-20
 */
public class ReportReservation {
    private String dateStart;
    private String dateEnd;
    private int minLength;
    private ArrayList<Reservation> reservations;

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }
}

