package ca.hccis.restaurant.reservation.repositories;

import ca.hccis.restaurant.reservation.jpa.entity.Reservation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
/**
 * Repository interface for accessing and managing {@link Reservation} entities in the database.
 *
 * <p>
 * This interface extends the {@link CrudRepository}, providing basic CRUD operations for
 * the `Reservation` entity. Additional custom query methods can be added as needed, using
 * Spring Data JPA's query method conventions.
 * </p>
 *
 * <p>
 * Custom query methods:
 * <ul>
 *     <li>{@code findByNameContaining(String name)}: Retrieves a list of reservations where
 *     the name contains the given substring.</li>
 * </ul>
 * </p>
 *
 * @author Sherri Ashton
 * @since 2024-11-16
 */
@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
//    List<Reservation> findByReservationDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
//}
//https://www.baeldung.com/spring-jpa-like-queries
List<Reservation> findByNameContaining(String name);
//    List<Reservation> findByReservationDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
}