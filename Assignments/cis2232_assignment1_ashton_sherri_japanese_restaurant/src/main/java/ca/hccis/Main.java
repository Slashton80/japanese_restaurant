package ca.hccis;

import ca.hccis.entity.Reservation;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


import com.google.gson.GsonBuilder;
import ca.hccis.adapter.LocalDateTimeAdapter;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    public static final String LAST_NAME = "Ashton";
    public static final String FIRST_NAME = "Sherri";
    public static final String FILE_NAME_CUSTOM_JSON = "data_" + LAST_NAME.toLowerCase() + "_" + FIRST_NAME.toLowerCase() + ".json";
    public static final String FOLDER_NAME = "C:\\CIS2232\\";


    public static void main(String[] args) {

        // Registers the LocalDateTimeAdapter with Gson to handle LocalDateTime serialization
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())  // Register the adapter for LocalDateTime
                .create();

        // Creates directory if it doesn't exist
        File directory = new File(FOLDER_NAME);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created: " + FOLDER_NAME);
            } else {
                System.out.println("Failed to create directory: " + FOLDER_NAME);
                return; // Exit if directory creation fails
            }
        }

        //Loops the menu
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            // Display menu options
            System.out.println("------------------------------\n");
            System.out.println("Japanese Restaurant Reservation System");
            System.out.println("\nA) Add a reservation");
            System.out.println("V) View all reservations");
            System.out.println("Q) Quit");
            System.out.print("\nEnter your choice:\n ");
            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "A":
                    addReservation(gson);
                    break;

                case "V":
                    viewReservationsFromJSON(gson);
                    break;

                case "Q":
                    running = false;
                    System.out.println("Exiting the system.");
                    break;

                default:
                    System.out.println("Invalid choice! Please enter A, V, or Q.");
            }
        }

        scanner.close();
    }

    // This method adds a new reservation and saves it as JSON
    private static void addReservation(Gson gson) {
        Reservation reservation = new Reservation();
        reservation.getInformation();

        // Save the reservation as JSON
        saveAsJSON(gson, reservation);
    }

    // Saves reservation as JSON
    private static void saveAsJSON(Gson gson, Reservation reservation) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FOLDER_NAME + FILE_NAME_CUSTOM_JSON, true))) {
            // Serialize the reservation as JSON
            writer.write(gson.toJson(reservation));
            // Ensures each JSON object is on a new line
            writer.newLine();
            System.out.println("Reservation saved. ");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Sets the idCounter based on the highest ID in the file
    private static void setIdCounterFromFile(Gson gson) {
        Path filePath = Paths.get(FOLDER_NAME + FILE_NAME_CUSTOM_JSON);
        // This variable is to track the highest ID
        int highestId = 0;

        try {
            // Checks if the file exists
            if (Files.exists(filePath)) {
                // Reads all lines from the JSON file
                List<String> lines = Files.readAllLines(filePath);
                if (!lines.isEmpty()) {
                    for (String line : lines) {
                        // Deserialize each line to a Reservation object
                        Reservation current = gson.fromJson(line, Reservation.class);

                        // Updates highestId to the max ID found so far
                        if (current.getId() > highestId) {
                            highestId = current.getId();
                        }
                    }

                    // Sets idCounter to the highest ID + 1
                    Reservation.setIdCounter(highestId + 1);
                    System.out.println("Next reservation ID will be: " + (highestId + 1));
                }
            } else {
                System.out.println("No reservations found. Starting from ID 1.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // This method is to view all reservations from the JSON file
    private static void viewReservationsFromJSON(Gson gson) {
        Path filePath = Paths.get(FOLDER_NAME + FILE_NAME_CUSTOM_JSON);
        // Variable tracks the highest ID
        int highestId = 0;

        try {
            if (Files.exists(filePath)) {
                List<String> lines = Files.readAllLines(filePath);
                if (lines.isEmpty()) {
                    System.out.println("No reservations found.");
                } else {
                    System.out.println("\n--- Existing Reservations (JSON) ---");
                    for (String line : lines) {
                        Reservation current = gson.fromJson(line, Reservation.class);
                        System.out.println(current.toString());

                        // Updates the highestId to be the maximum ID found so far
                        if (current.getId() > highestId) {
                            highestId = current.getId();
                        }
                    }
                    System.out.println("------------------------------\n");

                    // After loading all reservations, set the idCounter to the highest ID + 1
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

