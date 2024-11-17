package ca.hccis.restaurant.reservation.controller;

import ca.hccis.restaurant.reservation.bo.ReservationBO;
import ca.hccis.restaurant.reservation.repositories.CodeValueRepository;
import ca.hccis.restaurant.reservation.util.CisUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    private final CodeValueRepository _cvr;
//    private final ReservationBO reservationBO;

    @Autowired
    public MainController(CodeValueRepository _cvr) {
        this._cvr = _cvr;

    }

    @RequestMapping("/")
    public String home( HttpSession session) {
        String currentDate = String.valueOf(CisUtility.getCurrentDate(-30, "yyyy-MM-dd"));
        session.setAttribute("currentDate", currentDate);

        ReservationBO.setReservationTypes(_cvr, session);
        return "index";
    }
    @RequestMapping("/about")
    public String about() {
        return "other/about";
    }
}
