package info.hccis.main;

import com.google.gson.Gson;
import info.hccis.model.jpa.Reservation;
import info.hccis.student.util.UtilityRest;
import java.util.Scanner;
import org.json.JSONArray;

public class Controller {

    final public static String MENU = "\nMain Menu \nA) Add\n"
            + "U) Update (FUTURE)\n"
            + "V) View\n"
            + "D) Delete\n"
            + "X) eXit";
    final static Scanner input = new Scanner(System.in);
    private static final String URL_STRING = "http://localhost:8080/api/ReservationService/reservation";

    public static void main(String[] args) {
        boolean endProgram = false;
        do {
            System.out.println(MENU);
            String choice = input.nextLine();
            Reservation reservation;
            String url;
            switch (choice.toUpperCase()) {
                case "A":
                    // Create and add new reservation
                    reservation = create();
                    url = URL_STRING;
                    System.out.println("Url=" + url);
                    Reservation reservationReturned = (Reservation) UtilityRest.addUsingRest(url, reservation);
                    if (reservationReturned != null) {
                        System.out.println("Added new entity:" + reservationReturned.toString());
                    }
                    break;

                // Delete reservation
                case "D":
                    System.out.println("Enter id to delete");
                    int id = input.nextInt();
                    input.nextLine();  // burn the newline
                    UtilityRest.deleteUsingRest(URL_STRING, id);
                    break;

                // View all reservations
                case "V":
                    String jsonReturned = UtilityRest.getJsonFromRest(URL_STRING);
                    JSONArray jsonArray = new JSONArray(jsonReturned);
                    System.out.println("Here are the rows");
                    Gson gson = new Gson();
                    for (int currentIndex = 0; currentIndex < jsonArray.length(); currentIndex++) {
                        Reservation current = gson.fromJson(jsonArray.getJSONObject(currentIndex).toString(), Reservation.class);
                        System.out.println(current.toString());
                    }
                    break;

                // Exit the program
                case "X":
                    endProgram = true;
                    break;

                default:
                    System.out.println("INVALID OPTION");
            }
        } while (!endProgram);
    }

    /**
     * Method to create a Reservation object.
     *
     * @return Reservation object
     */
    public static Reservation create() {
        Reservation reservation = new Reservation();
        reservation.getInformation();
        return reservation;
    }
}
