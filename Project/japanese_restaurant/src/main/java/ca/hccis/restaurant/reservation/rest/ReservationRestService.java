package ca.hccis.restaurant.reservation.rest;

import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
import ca.hccis.restaurant.reservation.repositories.ReservationRepository;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST providing endpoints for managing restaurant reservations.
 * This class provides a RESTful API to handle CRUD operations related to reservations,
 * including retrieving all reservations, retrieving a specific reservation by ID,
 * and getting today's reservations. It uses Jersey for creating the RESTful services.
 *
 * @author Sherri
 * @since 2024-11-28
 */

/**
 * Retrieves all reservations
 * @author Sherri
 * @since 2024-11-28
 */
@Path("/reservation")
public class ReservationRestService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationRestService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * Method to get all reservations.
     * @author Sherri
     * @since 2024-11-28
     * @return response with all reservations
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Reservation> reservations = (List<Reservation>) reservationRepository.findAll();

        if (reservations.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.OK).entity(reservations).build();
        }
    }

    /**
     * Method to get a reservation by ID using REST.
     * @author Sherri
     * @since 2024-11-28
     * @param id Reservation ID
     * @return response with reservation data
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservationById(@PathParam("id") Integer id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (!reservation.isPresent()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.OK).entity(reservation.get()).build();
        }
    }

    /**
     * Method to get today's reservations.
     * @author Sherri
     * @since 2024-11-28
     * @return response with today's reservations
     */
    @GET
    @Path("/today")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodayReservation() {
        // Assuming Reservation entity has a field named reservationDateTime
        LocalDate today = LocalDate.now();
        List<Reservation> allReservations = (List<Reservation>) reservationRepository.findAll();
        List<Reservation> todayReservations = new ArrayList<>();

        for (Reservation reservation : allReservations) {
            if (reservation.getReservationDateTime().toLocalDate().isEqual(today)) {
                todayReservations.add(reservation);
            }
        }

        if (todayReservations.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.OK).entity(todayReservations).build();
        }
    }
}
