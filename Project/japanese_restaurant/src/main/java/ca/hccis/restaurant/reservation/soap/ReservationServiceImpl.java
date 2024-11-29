//package ca.hccis.restaurant.reservation.soap;
//
//import ca.hccis.restaurant.reservation.adapter.LocalDateTimeAdapter;
//import ca.hccis.restaurant.reservation.dao.ReservationDAO;
//import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
//import org.springframework.stereotype.Service;
//
//import javax.jws.WebService;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//@Service
//@WebService(endpointInterface = "ca.hccis.restaurant.reservation.soap.ReservationService")
//public class ReservationServiceImpl implements ReservationService {
//
//
//    public Reservation getReservation(int id) {
//        ReservationDAO reservationDAO = new ReservationDAO();
//        Reservation reservation = null;
//        // Correcting the method to call the right interface method name getReservationsForToday()
//        List<Reservation> allReservations = getReservationsForToday();
//        for (Reservation current : allReservations) {
//            if (current.getId() == id) {
//                reservation = current;
//                break;
//            }
//        }
//        return reservation;
//    }
//
//
//    @Override
//    public int getCount() {
//        return getReservations().size();
//    }
//
//    @Override
//    public List<Reservation> getReservations() {
//        ReservationDAO reservationDAO = new ReservationDAO();
//        ArrayList<Reservation> reservations = reservationDAO.selectAll();
//        return reservations;
//    }
//
//    @Override
//    public List<Reservation> getReservationsForToday() {
//        LocalDate today = LocalDate.now();
//        ReservationDAO reservationDAO = new ReservationDAO();
//        List<Reservation> allReservations = reservationDAO.selectAll();
//        List<Reservation> todayReservations = new ArrayList<>();
//
//        for (Reservation reservation : allReservations) {
//            if (reservation.getReservationDateTime().toLocalDate().isEqual(today)) {
//                todayReservations.add(reservation);
//            }
//        }
//
//        return todayReservations;
//    }
//}
