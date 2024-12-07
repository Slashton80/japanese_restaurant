package ca.hccis.restaurant.reservation.soap;

import ca.hccis.restaurant.reservation.jpa.entity.Reservation;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;
/**
 * Interface for the Reservation SOAP Web Service.
 * This interface provides methods to manage restaurant reservations, including retrieving a reservation by ID,
 * retrieving all reservations for today, counting the total number of reservations, and getting a list of all reservations.
 * It defines methods to be used by the SOAP service for creating, retrieving, and managing reservations.
 *
 * @author Sherri Ashton
 * @since 2024-11-28
 */
@WebService
public interface ReservationService {
    @WebMethod
    Reservation getReservation(int id);
    @WebMethod
    List<Reservation> getReservationsForToday();
    @WebMethod
    int getCount();
    @WebMethod
    List<Reservation> getReservations();
}