/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file choose Tools | Templates
 * and open the template in the editor.
 */
package ca.hccis.restaurant.reservation;
import ca.hccis.restaurant.reservation.rest.ReservationRestService;


import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

/**
 *
 * @author Sherri Ashton
 * @since 2024-11-28
 */
@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

    @PostConstruct
    private void init() {
//        registerClasses(TicketOrderService.class);
        registerClasses(ReservationRestService.class);
    }
}
