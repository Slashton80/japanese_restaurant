package ca.hccis.restaurant.reservation.soap;

import javax.xml.ws.Endpoint;

/**
 * Publisher class for the SOAP Web Service.
 * This class is responsible for publishing the Reservation SOAP Web Service
 * at a specified endpoint using the {@link Endpoint#publish(String, Object)} method.
 * URL: http://localhost:8083/reservationservice
 * Usage:
 * 1. Runs the main method to start the SOAP Web Service.
 * 2. The service will be accessible at the specified URL.
 *
 * @author Sherri
 * @since 2024-12-07
 */
public class ReservationServicePublisher {
    public static void main(String[] args) {
        System.out.println("Publishing SOAP Web Service...");
        Endpoint.publish("http://localhost:8083/reservationservice", new ReservationServiceImpl());
    }
}
