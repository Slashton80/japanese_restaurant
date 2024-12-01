
package info.hccis.testusingsoap.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the info.hccis.testusingsoap.soap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetCountResponse_QNAME = new QName("http://soap.restaurant.reservation.hccis.info/", "getCountResponse");
    private final static QName _GetReservationsResponse_QNAME = new QName("http://soap.restaurant.reservation.hccis.info/", "getReservationsResponse");
    private final static QName _GetReservation_QNAME = new QName("http://soap.restaurant.reservation.hccis.info/", "getReservation");
    private final static QName _GetReservations_QNAME = new QName("http://soap.restaurant.reservation.hccis.info/", "getReservations");
    private final static QName _GetReservationResponse_QNAME = new QName("http://soap.restaurant.reservation.hccis.info/", "getReservationResponse");
    private final static QName _GetCount_QNAME = new QName("http://soap.restaurant.reservation.hccis.info/", "getCount");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: info.hccis.testusingsoap.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetCountResponse }
     * 
     */
    public GetCountResponse createGetCountResponse() {
        return new GetCountResponse();
    }

    /**
     * Create an instance of {@link GetReservationsResponse }
     * 
     */
    public GetReservationsResponse createGetReservationsResponse() {
        return new GetReservationsResponse();
    }

    /**
     * Create an instance of {@link GetReservation }
     * 
     */
    public GetReservation createGetReservation() {
        return new GetReservation();
    }

    /**
     * Create an instance of {@link GetCount }
     * 
     */
    public GetCount createGetCount() {
        return new GetCount();
    }

    /**
     * Create an instance of {@link GetReservationResponse }
     * 
     */
    public GetReservationResponse createGetReservationResponse() {
        return new GetReservationResponse();
    }

    /**
     * Create an instance of {@link GetReservations }
     * 
     */
    public GetReservations createGetReservations() {
        return new GetReservations();
    }

    /**
     * Create an instance of {@link Reservation }
     * 
     */
    public Reservation createBusPass() {
        return new Reservation();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.restaurant.reservation.hccis.info/", name = "getCountResponse")
    public JAXBElement<GetCountResponse> createGetCountResponse(GetCountResponse value) {
        return new JAXBElement<GetCountResponse>(_GetCountResponse_QNAME, GetCountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReservationsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.restaurant.reservation.hccis.info/", name = "getBusPassesResponse")
    public JAXBElement<GetReservationsResponse> createGetReservationsResponse(GetReservationsResponse value) {
        return new JAXBElement<GetReservationsResponse>(_GetReservationsResponse_QNAME, GetReservationsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.restaurant.reservation.hccis.info/", name = "getReservation")
    public JAXBElement<GetReservation> createGetReservation(GetReservation value) {
        return new JAXBElement<GetReservation>(_GetReservation_QNAME, GetReservation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReservations }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.restaurant.reservation.hccis.info/", name = "getReservations")
    public JAXBElement<GetReservations> createGetReservations(GetReservations value) {
        return new JAXBElement<GetReservations>(_GetReservations_QNAME, GetReservations.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReservationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.restaurant.reservation.hccis.info/", name = "getReservationResponse")
    public JAXBElement<GetReservationResponse> createGetReservationResponse(GetReservationResponse value) {
        return new JAXBElement<GetReservationResponse>(_GetReservationResponse_QNAME, GetReservationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.restaurant.reservation.hccis.info/", name = "getCount")
    public JAXBElement<GetCount> createGetCount(GetCount value) {
        return new JAXBElement<GetCount>(_GetCount_QNAME, GetCount.class, null, value);
    }

}
