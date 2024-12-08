package ca.hccis.restaurant.reservation.repositories;

import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for accessing and managing {@link Reservation} entities in the database.
 * This interface extends the {@link CrudRepository}, providing advanced CRUD operations and
 * additional query support for the `Reservation` entity. Custom queries can be added as needed.
 *
 * @author Sherri Ashton
 * @since 2024-11-16
 */
@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

    /**
     * Find all reservations with a name containing the given substring.
     *
     * @param name The substring to search for in reservation names.
     * @return A list of reservations with names containing the substring.
     */
    List<Reservation> findByNameContaining(String name);

    /**
     * Find reservations made within a specific date and time range.
     *
     * @param startDate The start of the range.
     * @param endDate   The end of the range.
     * @return A list of reservations within the given date range.
     */
    List<Reservation> findByReservationDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find reservations made for today.
     *
     * @return A list of reservations made for today.
     */
    @Query("SELECT r FROM Reservation r WHERE DATE(r.reservationDateTime) = CURRENT_DATE")
    List<Reservation> findReservationsForToday();


}
