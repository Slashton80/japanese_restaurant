package ca.hccis.restaurant.reservation.controller;

import ca.hccis.restaurant.reservation.bo.ReservationBO;
import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
import ca.hccis.restaurant.reservation.repositories.CodeValueRepository;
import ca.hccis.restaurant.reservation.repositories.ReservationRepository;
import ca.hccis.restaurant.reservation.util.CisUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MainController {

    private final CodeValueRepository _cvr;

    @Autowired
    public MainController(CodeValueRepository cvr) {

    }

    @RequestMapping Home("/")
    public String home(Model model) {
        String currentDate = CisUtility.getCurrentDate("yyyy-MM-dd");
        session.setAttribute("currentDate", currentDate);

        ReservationBO.setReservationTypes(_cvr, session);

        return "index";
    }

}
