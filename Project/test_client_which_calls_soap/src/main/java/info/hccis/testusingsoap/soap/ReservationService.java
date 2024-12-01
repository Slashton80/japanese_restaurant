
package info.hccis.testusingsoap.soap;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * Interface for ReservationService providing SOAP web service operations.
 * This interface provides operations for getting reservations by ID, getting
 * a count of reservations, and listing all reservations.
 *
 * @author Sherri
 * @since 2024-11-28
 */
@WebService(name = "ReservationService", targetNamespace = "http://soap.reservation.hccis.info/")
@XmlSeeAlso({
        ObjectFactory.class
})
public interface ReservationService {

    /**
     * Method to get a reservation by ID.
     *
     * @param id Reservation ID
     * @return returns info.hccis.testusingsoap.soap.Reservation
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getReservation", targetNamespace = "http://soap.reservation.hccis.info/", className = "info.hccis.testusingsoap.soap.GetReservation")
    @ResponseWrapper(localName = "getReservationResponse", targetNamespace = "http://soap.reservation.hccis.info/", className = "info.hccis.testusingsoap.soap.GetReservationResponse")
    @Action(input = "http://soap.reservation.hccis.info/ReservationService/getReservationRequest", output = "http://soap.reservation.hccis.info/ReservationService/getReservationResponse")
    public Reservation getReservation(
            @WebParam(name = "id", targetNamespace = "")
            int id);

    /**
     * Method to get the total count of reservations.
     *
     * @return returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCount", targetNamespace = "http://soap.reservation.hccis.info/", className = "info.hccis.testusingsoap.soap.GetCount")
    @ResponseWrapper(localName = "getCountResponse", targetNamespace = "http://soap.reservation.hccis.info/", className = "info.hccis.testusingsoap.soap.GetCountResponse")
    @Action(input = "http://soap.reservation.hccis.info/ReservationService/getCountRequest", output = "http://soap.reservation.hccis.info/ReservationService/getCountResponse")
    public int getCount();

    /**
     * Method to get all reservations.
     *
     * @return returns java.util.List<info.hccis.testusingsoap.soap.Reservation>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getReservations", targetNamespace = "http://soap.reservation.hccis.info/", className = "info.hccis.testusingsoap.soap.GetReservations")
    @ResponseWrapper(localName = "getReservationsResponse", targetNamespace = "http://soap.reservation.hccis.info/", className = "info.hccis.testusingsoap.soap.GetReservationsResponse")
    @Action(input = "http://soap.reservation.hccis.info/ReservationService/getReservationsRequest", output = "http://soap.reservation.hccis.info/ReservationService/getReservationsResponse")
    public List<Reservation> getReservations();

}
