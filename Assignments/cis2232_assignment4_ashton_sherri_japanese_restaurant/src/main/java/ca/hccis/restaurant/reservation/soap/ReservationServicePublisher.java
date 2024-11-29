package ca.hccis.restaurant.reservation.soap;

import javax.xml.ws.Endpoint;

public class ReservationServicePublisher {
    public static void main(String[] args) {
        Endpoint.publish(
          "http://localhost:8083/reservationservice",
           new ReservationServiceImpl());

    }
}