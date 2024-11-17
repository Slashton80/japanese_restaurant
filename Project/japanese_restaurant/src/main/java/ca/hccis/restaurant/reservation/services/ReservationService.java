//package ca.hccis.restaurant.reservation.services;
//
//import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
//import ca.hccis.restaurant.reservation.repositories.ReservationRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//public class ReservationService {
//
//    private final ReservationRepository reservationRepository;
//
//    @Autowired
//    public ReservationService(ReservationRepository reservationRepository) {
//        this.reservationRepository = reservationRepository;
//    }
//
//    public List<Reservation> getAllReservations() {
//        return (List<Reservation>) reservationRepository.findAll();
//    }
//
//    public Reservation getReservationById(Integer id) {
//        return reservationRepository.findById(id).orElse(null);
//    }
//
//    public void processAndSaveReservation(Reservation reservation) {
//        reservationRepository.save(reservation);
//    }
//
//    public void deleteReservationById(Integer id) {
//        reservationRepository.deleteById(id);
//    }
//
//    public List<Reservation> findByNameContaining(String name) {
//        return reservationRepository.findByNameContaining(name);
//    }
//
//    public List<Reservation> findReservationsByDateRange(LocalDateTime dateStart, LocalDateTime dateEnd) {
//        return reservationRepository.findByReservationDateTimeBetween(dateStart, dateEnd);
//    }
//}
