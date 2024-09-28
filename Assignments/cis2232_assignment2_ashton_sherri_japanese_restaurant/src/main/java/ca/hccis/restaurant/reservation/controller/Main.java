package ca.hccis.restaurant.reservation.controller;

import ca.hccis.restaurant.reservation.entity.Reservation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ca.hccis.restaurant.reservation.adapter.LocalDateTimeAdapter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.io.File;

/**
 * This class is the entry point of the application. It runs a simple console-based menu to add or view reservations
 * for a Japanese restaurant, using JSON to store reservation data.
 *
 * @author sherri ashton
 * @since 2024-09-20
 */
public class Main {

    public static final String LAST_NAME = "Ashton";
    public static final String FIRST_NAME = "Sherri";
    public static final String FILE_NAME_CUSTOM_JSON = "data_" + LAST_NAME.toLowerCase() + "_" + FIRST_NAME.toLowerCase() + ".json";
    public static final String FOLDER_NAME = "C:\\CIS2232\\";

    public static void main(String[] args) {

        // Registers the LocalDateTimeAdapter with Gson to handle LocalDateTime serialization
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        // Creates directory if it doesn't exist
        File directory = new File(FOLDER_NAME);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created: " + FOLDER_NAME);
            } else {
                System.out.println("Failed to create directory: " + FOLDER_NAME);
                return;
            }
        }
        // Load and set the highest ID on startup
        loadAndSetHighestId(gson);

        // Loops the menu
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            // Displays menu options
            System.out.println("------------------------------\n");
            System.out.println("Japanese Restaurant Reservation System");
            System.out.println("\nA) Add a reservation");
            System.out.println("V) View all reservations");
            System.out.println("X) Quit");
            System.out.print("\nEnter your choice:\n ");
            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "A":
                    addReservation(gson);
                    break;

                case "V":
                    viewReservationsFromJSON(gson);
                    break;

                case "X":
                    running = false;
                    System.out.println("Exiting the system.");
                    break;

                default:
                    System.out.println("Invalid choice! Please enter A, V, or X.");
            }
        }
    }

    /**
     * This method reads all the existing reservations from a JSON file and identifies the highest reservation ID.
     * It ensures that the ID counter (idCounter) for new reservations starts from the highest existing ID + 1.
     *
     * @author sherri ashton
     * @since 2024-09-20
     */
    private static void loadAndSetHighestId(Gson gson) {
        Path filePath = Paths.get(FOLDER_NAME + FILE_NAME_CUSTOM_JSON);
        int highestId = 0;

        try {
            if (Files.exists(filePath)) {
                List<String> lines = Files.readAllLines(filePath);
                if (!lines.isEmpty()) {
                    for (String line : lines) {
                        Reservation current = gson.fromJson(line, Reservation.class);

                        // Checks if current is not null and has a valid ID
                        if (current != null && current.getId() > highestId) {
                            highestId = current.getId();
                        }
                    }
                }
            }
            // Set the idCounter to the highest found ID + 1
            Reservation.setIdCounter(highestId + 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method creates a new Reservation and collects user input, then saves it as JSON.
     *
     * @author sherri ashton
     * @since 2024-09-20
     */
    private static void addReservation(Gson gson) {
        Reservation reservation = new Reservation();
        reservation.getInformation();

        // Calculates and display total cost
        double totalCost = reservation.calculateTotalCost();
        System.out.println("Total cost for this reservation: $" + totalCost);

        // Saves the reservation as JSON
        saveAsJSON(gson, reservation);
    }

    /**
     * Writes a reservation object to a JSON file.
     *
     * @author sherri ashton
     * @since 2024-09-20
     */
    private static void saveAsJSON(Gson gson, Reservation reservation) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FOLDER_NAME + FILE_NAME_CUSTOM_JSON, true))) {
            // Serializes the reservation as JSON
            writer.write(gson.toJson(reservation));
            // Ensures each JSON object is on a new line
            writer.newLine();
            System.out.println("Reservation saved.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Reads all reservations from the JSON file and displays them. Updates the idCounter to prevent duplicate IDs.
     *
     * @author sherri ashton
     * @since 2024-09-20
     * @param gson
     */
    private static void viewReservationsFromJSON(Gson gson) {
        Path filePath = Paths.get(FOLDER_NAME + FILE_NAME_CUSTOM_JSON);
        int highestId = 0;

        try {
            if (Files.exists(filePath)) {
                List<String> lines = Files.readAllLines(filePath);
                if (lines.isEmpty()) {
                    System.out.println("No reservations found.");
                } else {
                    System.out.println("\n--- Existing Reservations ---");
                    for (String line : lines) {
                        Reservation current = gson.fromJson(line, Reservation.class);

                        // Checks if current is not null before calling toString()
                        if (current != null) {
                            System.out.println(current.toString());

                            // Updates the highestId to be the maximum ID found so far
                            if (current.getId() > highestId) {
                                highestId = current.getId();
                            }
                        } else {
                            System.out.println("Error: Encountered invalid data in the reservations file.");
                        }
                    }
                    System.out.println("------------------------------\n");

                    // After loading all reservations, sets the idCounter to the highest ID + 1
                    Reservation.setIdCounter(highestId + 1);
                }
            } else {
                System.out.println("No reservations file found: " + FILE_NAME_CUSTOM_JSON);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
