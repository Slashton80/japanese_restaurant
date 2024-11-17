package ca.hccis.restaurant.reservation.repositories;

import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    List<Reservation> findByReservationDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
}
