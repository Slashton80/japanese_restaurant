package info.hccis.testusingsoap.soap;

import java.util.Scanner;
/**
 * Test soap
 *
 * @author Sherri Ashton
 * @since 2024-11-30
 *
 */
public class Controller {

    public static void main(String[] args) {

        ReservationServiceImplService reservationServiceImplService = new ReservationServiceImplService();
        ReservationService reservationService = reservationServiceImplService.getReservationServiceImplPort();

        System.out.println("There are " + reservationService.getCount() + " reservations in the database");

        String option = "";
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("Enter id of reservation to view, 0 to view all, -1 to exit");

            option = input.nextLine();
            if (!option.equals("-1")) {
                try {
                    int optionNumeric = Integer.parseInt(option);
                    if (optionNumeric == 0) {
                        for (Reservation current : reservationService.getReservations()) {
                            System.out.println(current.getName() + " - " + current.getReservationDateTime());
                        }
                    } else {
                        Reservation reservation = reservationService.getReservation(optionNumeric);
                        System.out.println(reservation.getName() + " - " + reservation.getReservationDateTime());
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }

        } while (!option.equalsIgnoreCase("-1"));
    }

}
