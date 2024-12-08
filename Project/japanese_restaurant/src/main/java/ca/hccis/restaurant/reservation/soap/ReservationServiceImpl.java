package ca.hccis.restaurant.reservation.soap;

import ca.hccis.restaurant.reservation.dao.ReservationDAO;
import ca.hccis.restaurant.reservation.jpa.entity.Reservation;

import javax.jws.WebService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link ReservationService} interface for the restaurant reservation SOAP web service.
 * <p>
 * This class provides the actual business logic for SOAP operations such as retrieving a reservation by ID,
 * counting the total reservations, retrieving all reservations, and fetching today's reservations.
 * <p>
 * It uses the {@link ReservationDAO} for database operations and processes the reservation data
 * as required by the methods.
 *
 * @author Sherri
 * @since 2024-12-07
 */
@WebService(endpointInterface = "ca.hccis.restaurant.reservation.soap.ReservationService")
public class ReservationServiceImpl implements ReservationService {

    /**
     * Retrieves a reservation by its unique ID.
     * This method filters today's reservations to find the one matching the given ID.
     *
     * @param id The unique identifier of the reservation.
     * @return The {@link Reservation} object if found; otherwise, {@code null}.
     * *See LocalDateTimeJaxAdapter
     */
    public Reservation getReservation(int id) {
        ReservationDAO reservationDAO = new ReservationDAO();
        Reservation reservation = null;
        // Correcting the method to call the right interface method name getReservationsForToday()
        List<Reservation> allReservations = getReservationsForToday();
        for (Reservation current : allReservations) {
            if (current.getId() == id) {
                reservation = current;
                break;
            }
        }
        return reservation;
    }

    /**
     * Retrieves the total count of reservations in the system.
     *
     * @return The total number of reservations as an integer (eg: 10 reservations)
     */
    @Override
    public int getCount() {
        return getReservations().size();
    }

    /**
     * Retrieves all reservations in the system.
     * This method calls the DAO layer to fetch all reservation data from the database.
     *
     * @return A {@link List} of {@link Reservation} objects. If no reservations are found, an empty list is returned.
     */
    @Override
    public List<Reservation> getReservations() {
        ReservationDAO reservationDAO = new ReservationDAO();
        ArrayList<Reservation> reservations = reservationDAO.selectAll();
        return reservations;
    }

    /**
     * Retrieves all reservations scheduled for the current date.
     * This method filters the reservations from the DAO based on today's date.
     *
     * @return A {@link List} of {@link Reservation} objects for today's date. If no reservations are found, an empty list is returned.
     */
    @Override
    public List<Reservation> getReservationsForToday() {
        LocalDate today = LocalDate.now();
        ReservationDAO reservationDAO = new ReservationDAO();
        List<Reservation> allReservations = reservationDAO.selectAll();
        List<Reservation> todayReservations = new ArrayList<>();

        for (Reservation reservation : allReservations) {
            if (reservation.getReservationDateTime().toLocalDate().isEqual(today)) {
                todayReservations.add(reservation);
            }
        }

        return todayReservations;
    }
}
