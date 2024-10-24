package ca.hccis.restaurant.reservation.controller;

import ca.hccis.restaurant.reservation.thread.ThreadReservation;

/**
 * Main class for the restaurant reservation system
 *
 * @author Sherri Ashton
 * @since Originally 2024-09-20. 2024-09-30 (changes - created threads for both console and gui, moved info from assignment 2 to ThreadReservation Class)
 */
public class MainController {

    // Main method
    public static void main(String[] args) {
        // Creates and starts the GUI thread
        ThreadReservation guiThread = new ThreadReservation();
        guiThread.setIsGUI(true);
        guiThread.start(); // Start GUI thread

        // Creates and starts the console thread
        ThreadReservation consoleThread = new ThreadReservation();
        consoleThread.setIsGUI(false);
        consoleThread.start(); // Start console thread
    }
}
