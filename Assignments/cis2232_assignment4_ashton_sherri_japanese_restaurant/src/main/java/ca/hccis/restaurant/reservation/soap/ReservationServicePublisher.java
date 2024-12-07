package ca.hccis.restaurant.reservation.soap;

import javax.xml.ws.Endpoint;
/**
 * Publishes the Reservation SOAP Web Service.
 * This class is responsible for publishing the ReservationService at the specified URL,
 * making the SOAP service available for client use.
 *
 * It uses the JAX-WS API to publish the ReservationServiceImpl instance as a web service.
 * The service will be accessible at the specified local endpoint.
 *
 * To run the service, execute this main method.
 *
 * @author Sherri Ashton
 * @since 2024-11-28
 */
public class ReservationServicePublisher {
    public static void main(String[] args) {
        System.out.println("Publishing SOAP Web Service...");
        Endpoint.publish("http://localhost:8083/reservationservice", new ReservationServiceImpl());
//        System.out.println("SOAP Web Service running at http://localhost:8083/reservationservice");
    }
}
