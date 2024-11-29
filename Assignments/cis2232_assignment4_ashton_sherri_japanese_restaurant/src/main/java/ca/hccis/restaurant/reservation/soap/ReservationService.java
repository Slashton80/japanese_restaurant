package ca.hccis.restaurant.reservation.soap;

import ca.hccis.restaurant.reservation.jpa.entity.Reservation;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface ReservationService {
    @WebMethod
    Reservation getReservation(int id);
    @WebMethod
    List<Reservation> getReservations();
    @WebMethod
    int getCount();
    
}