package ca.hccis.restaurant.reservation.rest;

import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
import ca.hccis.restaurant.reservation.repositories.ReservationRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
 *
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
     *
     * @return response with all reservations
     * @author Sherri
     * @since 2024-11-28
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
     *
     * @param id Reservation ID
     * @return response with reservation data
     * @author Sherri
     * @since 2024-11-28
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
     *
     * @return response with today's reservations
     * @author Sherri
     * @since 2024-11-28
     */
    @GET
    @Path("/today")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodayReservation() {
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
    /**
     * Handles the creation of a new reservation.
     *
     * This method receives a JSON payload representing a {@link Reservation}, validates its contents,
     * and saves it to the database using the {@link ReservationRepository}. If the reservation
     * is successfully created, a 201 (Created) response is returned with the created reservation as the payload.
     * If validation fails or an error occurs during processing, a 400 (Bad Request) response is returned
     * with the error message.
     *
     * @param json The JSON representation of the {@link Reservation} to be created.
     * @return A {@link Response} object containing the created reservation and a 201 status if successful,
     *         or a 400 status with an error message if the input is invalid or processing fails.
     *
     * @author Sherri
     * @since 2024-11-28
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReservation(String json) {
        try {
            Reservation reservation = new Gson().fromJson(json, Reservation.class);

            // Validates the reservation
            validateReservation(reservation);

            // Saves the reservation
            reservation = reservationRepository.save(reservation);

            return Response.status(Response.Status.CREATED).entity(reservation).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    /**
     * Validates the given {@link Reservation} object to ensure that all required fields are provided.
     *
     * This method checks that the reservation's name and email fields are not null. If either field is missing,
     * an {@link Exception} is thrown with an appropriate error message.
     *
     * @param reservation The {@link Reservation} object to be validated.
     * @throws Exception If the reservation's name or email is null.
     */
    private void validateReservation(Reservation reservation) throws Exception {
        if (reservation.getName() == null || reservation.getEmail() == null) {
            throw new Exception("Name and email are required.");
        }
    }
}
