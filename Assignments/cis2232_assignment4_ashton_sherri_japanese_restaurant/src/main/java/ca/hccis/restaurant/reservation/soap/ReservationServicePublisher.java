package ca.hccis.restaurant.reservation.soap;

import javax.xml.ws.Endpoint;

public class ReservationServicePublisher {
    public static void main(String[] args) {
        System.out.println("Publishing SOAP Web Service...");
        Endpoint.publish("http://localhost:8083/reservationservice", new ReservationServiceImpl());
//        System.out.println("SOAP Web Service running at http://localhost:8083/reservationservice");
    }
}
