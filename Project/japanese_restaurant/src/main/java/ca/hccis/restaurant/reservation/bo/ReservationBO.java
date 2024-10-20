package ca.hccis.restaurant.reservation.bo;


import ca.hccis.restaurant.reservation.dao.ReservationDAO;
import ca.hccis.restaurant.reservation.entity.Reservation;
import ca.hccis.restaurant.reservation.util.CisUtilityFile;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationBO {

    // Process the reservation report for a given date range
    public static ArrayList<Reservation> processDateRangeReport(String start, String end) {

        //**********************************************************************
        // This could be done using the repository but there will be times when
        // jdbc will be useful.  For the reports, the requirements state that you
        // are to use jdbc to obtain the data for the report.
        //**********************************************************************
        // Get the reservations from the DAO
        ReservationDAO reservationDAO = new ReservationDAO();
        ArrayList<Reservation> reservations = reservationDAO.selectAllByDateRange(start, end);

        // Write the reservations to a file using the utility
        CisUtilityFile.writeReportToFile("reservation_dateRangeReport", reservations);

        // Return the fetched reservations
        return reservations;
    }
}

//    public static ArrayList<Reservation> processMinLengthReport(int minLength) throws SQLException {
//
//        //**********************************************************************
//        // This could be done using the repository but there will be times when
//        // jdbc will be useful.  For the reports, the requirements state that you
//        // are to use jdbc to obtain the data for the report.
//        //**********************************************************************
//        ReservationDAO reservationDAO = new ReservationDAO();
//        ArrayList<Reservation> reservations = null;
//
//        try {
//            reservations = ReservationDAO.selectAllWithMinLength(minLength);
//        } catch (SQLException e) {
//            throw e;
//        }
//
//        //Also write the report to a file
//        CisUtilityFile.writeReportToFile("minLengthReport", reservations);
//
//        return reservations;
//    }



