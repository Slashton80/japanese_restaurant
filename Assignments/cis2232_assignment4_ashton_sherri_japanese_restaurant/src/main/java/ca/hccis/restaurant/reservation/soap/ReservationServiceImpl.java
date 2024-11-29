package ca.hccis.restaurant.reservation.soap;

import ca.hccis.restaurant.reservation.dao.ReservationDAO;
import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
import ca.hccis.restaurant.reservation.rest.ReservationService;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "ca.hccis.restaurant.reservation.soap.ReservationService")
public class ReservationServiceImpl implements ReservationService {

    public Reservation getReservation(int id) {

        ReservationDAO reservationDAO = new ReservationDAO();
        Reservation reservation = null;
        for(Reservation current: getReservations()){
            if(current.getId().equals(id)){
                reservation = current;
            }
        }
        return reservation;

    }

    @Override
    public int getCount() {
        return getReservations().size();
    }

    @Override
    public List<Reservation> getReservations() {

        ReservationDAO reservationDAO = new ReservationDAO();
        ArrayList<Reservation> reservations = reservationDAO.selectAll();
        return reservations;


    }

}
