package ca.hccis.restaurant.reservation.controller;

import ca.hccis.restaurant.reservation.bo.ReservationBO;
import ca.hccis.restaurant.reservation.repositories.CodeValueRepository;
import ca.hccis.restaurant.reservation.util.CisUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Controller class for handling the main navigation and entry points of the restaurant reservation system.
 *
 * <p>
 * This controller is responsible for:
 * <ul>
 *     <li>Setting up the home page (`/` endpoint) with session attributes.</li>
 *     <li>Routing to the "About" page (`/about` endpoint).</li>
 * </ul>
 * </p>
 *
 * <p>
 * The controller interacts with utility classes and business objects to manage
 * session data and provide required setup for the views.
 * </p>
 *
 * @author Sherri Ashton
 * @since 2024-11-16
 */
@Controller
public class MainController {

    private final CodeValueRepository _cvr;
//    private final ReservationBO reservationBO;

    /**
     * Constructor to initialize the {@link CodeValueRepository}.
     *
     * <p>
     * The {@link CodeValueRepository} is used to retrieve reservation types
     * and other code values required for the application.
     * </p>
     *
     * @param _cvr The {@link CodeValueRepository} instance to be injected by Spring.
     * @author Sherri Ashton
     * @since 2024-11-14
     */
    @Autowired
    public MainController(CodeValueRepository _cvr) {
        this._cvr = _cvr;

    }

    /**
     * Handles the root URL ("/") of the application and sets up the session.
     *
     * <p>
     * This method performs the following tasks:
     * <ul>
     *     <li>Sets the `currentDate` attribute in the HTTP session to a date 30 days in the past.</li>
     *     <li>Loads reservation types into the session using {@link ReservationBO#setReservationTypes}.</li>
     *     <li>Returns the "index" view to render the home page.</li>
     * </ul>
     * </p>
     *
     * @param session The current {@link HttpSession} where attributes will be stored.
     * @return The name of the view ("index") for rendering the home page.
     * author Sherri Ashton
     * since 2024-11-14
     */
    @RequestMapping("/")
    public String home(HttpSession session) {
        String currentDate = String.valueOf(CisUtility.getCurrentDate(-30, "yyyy-MM-dd"));
        session.setAttribute("currentDate", currentDate);

        ReservationBO.setReservationTypes(_cvr, session);
        return "index";
    }

    /**
     * Handles the "/about" endpoint to display the "About" page.
     *
     * <p>
     * This method simply returns the name of the view for the "About" page,
     * which is typically a static page containing information about the application.
     * </p>
     *
     * @return The name of the view ("other/about") for rendering the "About" page.
     * @author Sherri Ashton
     * @since 2024-11-14
     */
    @RequestMapping("/about")
    public String about() {
        return "other/about";
    }
}
