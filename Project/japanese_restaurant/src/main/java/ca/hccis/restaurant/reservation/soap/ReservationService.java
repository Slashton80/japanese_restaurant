package ca.hccis.restaurant.reservation.soap;

import ca.hccis.restaurant.reservation.jpa.entity.Reservation;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;
/**
 * SOAP Web Service Interface for managing restaurant reservations.
 *
 * This interface defines the operations provided by the Reservation SOAP service,
 * such as retrieving a specific reservation, fetching today's reservations,
 * counting the total number of reservations, and listing all reservations.
 *
 * The implementing class should provide the actual logic for these operations.
 *
 * @author Sherri
 * @since 2024-12-07
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