//package ca.hccis.restaurant.reservation.thread;
//
//import ca.hccis.restaurant.reservation.adapter.LocalDateTimeAdapter;
//import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
//import ca.hccis.restaurant.reservation.settings.Config;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import javax.swing.*;
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
/// **
// * This class allows reservations to be handled in two separate threads: one using a console-based
// * user interface and the other using a GUI (JOptionPane) interface. Reservations are synchronized and
// * stored in a JSON file, shared between both threads.
// * The class also manages the total reservation count and allows adding, viewing, and saving
// * reservations from both the console and GUI threads. A synchronized list ensures thread safety
// * while handling reservations across threads.
// *
// * CisUtility is not used here!
// *
// * @author Sherri Ashton
// * @since 2024-10-02
// */
//public class ThreadReservation extends Thread implements Config {
//    private boolean isGUI = false;
//    private static int totalReservationCount = 0; // Static accumulator for total reservations
//    private final List<Reservation> reservations = Collections.synchronizedList(new ArrayList<>()); // Thread-safe list
//    private final Gson gson = new GsonBuilder()
//            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
//            .create(); // Ensures the custom adapter is registered
//
//    public void setIsGUI(boolean isGUI) {
//        this.isGUI = isGUI;
//    }
//    /**
//     * The main run method of the thread. It determines whether to run the GUI or console-based
//     * interface based on the isGUI flag. It also loads the existing reservations from the JSON
//     * file when the thread starts.
//     *
//     * @author: Sherri Ashton
//     * @since: 2024-10-02
//     */
//    @Override
//    public void run() {
//        initializeTotalReservationCount();  // Initializes total reservation count on startup
//        loadReservationsFromFile(); // Loads existing reservations on startup
//        if (isGUI) {
//            runGUIMode(); // Runs GUI menu
//        } else {
//            runConsoleMode(); // Runs console menu
//        }
//    }
//    /**
//     * Loads the reservations from the JSON file and initializes the reservation list with them.
//     * The method ensures that no duplicate reservations are added.
//     * @author: Sherri Ashton
//     * @since: 2024-10-02
//     */
//    private void loadReservationsFromFile() {
//        Path filePath = Paths.get(Config.FOLDER_NAME + Config.FILE_NAME_CUSTOM_JSON);
//        if (Files.exists(filePath)) {
//            try {
//                List<String> lines = Files.readAllLines(filePath);
//                int maxId = 0; // Variable to track the highest ID
//                for (String line : lines) {
//                    // Skips empty lines
//                    if (line.trim().isEmpty()) {
//                        continue; // Skips empty lines
//                    }
//                    try {
//                        Reservation reservation = gson.fromJson(line, Reservation.class);
//                        if (reservation != null) {
//                            // Checks for duplicates before adding
//                            boolean exists = reservations.stream().anyMatch(r -> r.getId() == reservation.getId());
//                            if (!exists) {
//                                reservations.add(reservation);
//                            }
//                            // Updates maxId to the highest ID found
//                            if (reservation.getId() > maxId) {
//                                maxId = reservation.getId();
//                            }
//                        } else {
//                            System.out.println("Found a null reservation from line: " + line);
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Error parsing line: " + line);
//                        e.printStackTrace();
//                    }
//                }
//                // Sets idCounter to the highest ID found plus one
//                Reservation.setIdCounter(maxId + 1);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("No existing reservations found; starting fresh.");
//        }
//    }
//
//    /**
//     * Initializes the total reservation count based on the existing reservations in the JSON file.
//     * This method reads the JSON file containing the reservations and sets the total reservation
//     * count to the number of lines (reservations) in the file. If the file does not exist, the
//     * total reservation count is initialized to zero, indicating that no prior reservations exist.
//     *
//     * @author Sherri Ashton
//     * @since 2024-10-03
//     */
//    private synchronized void initializeTotalReservationCount() {
//        Path filePath = Paths.get(Config.FOLDER_NAME + Config.FILE_NAME_CUSTOM_JSON);
//        if (Files.exists(filePath)) {
//            try {
//                List<String> lines = Files.readAllLines(filePath);
//                totalReservationCount = lines.size(); // Sets the total count to the number of existing reservations
//                System.out.println("Total reservations loaded from file: " + totalReservationCount);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("No existing reservations found; starting fresh.");
//            totalReservationCount = 0; // Starts fresh if no file exists
//        }
//    }
//    /**
//     * Updates the total reservation count when a new reservation is added.
//     * The method is synchronized to ensure thread safety.
//     * @author: Sherri Ashton
//     * @since: 2024-10-02
//     */
//
//    private synchronized void updateTotalReservationCount() {
//        totalReservationCount++; // Increments the count
//        System.out.println("Total reservations: " + totalReservationCount);
//    }
//    /**
//     * Runs the console-based menu interface for the user. Allows adding reservations and
//     * viewing all reservations through the console.
//     * @author: Sherri Ashton
//     * @since: 2024-10-02
//     */
//    private void runConsoleMode() {
//        System.out.println("Welcome to the Restaurant Reservation System");
//        while (true) {
//            System.out.println("\nMenu:\nA. Add Reservation\nV. View Reservations\nQ. Quit");
//            String choice = getInputString("\nEnter your choice: ");
//            switch (choice.toUpperCase()) {
//                case "A":
//                    addReservationConsole();
//                    break;
//                case "V":
//                    viewReservationsConsole();
//                    break;
//                case "Q":
//                    saveReservationsToFile(); // Saves before quitting
//                    System.exit(0);
//                    break;
//                default:
//                    System.out.println("\nInvalid option. Try again.\n");
//            }
//        }
//    }
//
//    /**
//     * Runs the GUI-based menu interface for the user using JOptionPane dialogs.
//     * Allows adding and viewing reservations through a graphical interface.
//     * @author: Sherri Ashton
//     * @since: 2024-10-02
//     */
//    private void runGUIMode() {
//        while (true) {
//            String menu = "\nMenu:\nA. Add Reservation\nV. View Reservations\nQ. Quit";
//            String choice = JOptionPane.showInputDialog(menu).toUpperCase();
//            switch (choice) {
//                case "A":
//                    addReservationGUI();
//                    break;
//                case "V":
//                    viewReservationsGUI();
//                    break;
//                case "Q":
//                    saveReservationsToFile(); // Saves before quitting
//                    System.exit(0);
//                    break;
//                default:
//                    JOptionPane.showMessageDialog(null, "Invalid option. Try again.");
//            }
//        }
//    }
//    /**
//     * Gathers the reservation information from the console, creates a new Reservation object,
//     * and adds it to the synchronized reservation list. Updates the total reservation count afterward.
//     * @author: Sherri Ashton
//     * @since: 2024-10-02
//     */
//    private void addReservationConsole() {
//        Reservation reservation = gatherReservationInfoConsole();
//        if (reservation != null) {
//            synchronized (reservations) {
//                boolean exists = reservations.stream().anyMatch(r -> r.getId() == reservation.getId());
//                if (!exists) {
//                    reservations.add(reservation);
//                    System.out.println("\nReservation added: " + reservation);
//                    updateTotalReservationCount(); // Updates the accumulator
//                    saveReservationToFile(reservation); // Saves this single reservation to the file
//                } else {
//                    System.out.println("This reservation already exists!");
//                }
//            }
//        }
//    }
//    /**
//     * Gathers the reservation information from the GUI (JOptionPane), creates a new Reservation object,
//     * and adds it to the synchronized reservation list. Updates the total reservation count afterward.
//     * @author: Sherri Ashton
//     * @since: 2024-10-02
//     */
//    private void addReservationGUI() {
//        Reservation reservation = gatherReservationInfoGUI();
//        if (reservation != null) {
//            synchronized (reservations) {
//                boolean exists = reservations.stream().anyMatch(r -> r.getId() == reservation.getId());
//                if (!exists) {
//                    reservations.add(reservation);
//                    JOptionPane.showMessageDialog(null, "Reservation added: " + reservation);
//                    updateTotalReservationCount(); // Updates the accumulator
//                    saveReservationToFile(reservation); // Saves this single reservation to the file
//                } else {
//                    JOptionPane.showMessageDialog(null, "This reservation already exists!");
//                }
//            }
//        }
//    }
//
//    /**
//     * Gathers the necessary reservation details from the user through the console input.
//     * @author: Sherri Ashton
//     * @since: 2024-10-02
//     * @return A Reservation object containing the entered information.
//     */
//    private Reservation gatherReservationInfoConsole() {
//        try {
//            Reservation reservation = new Reservation();
//           // Prompt for name until a valid non-null value is entered
//            while (true) {
//                String name = getInputString("Enter Customer Name: ");
//                if (name != null && !name.trim().isEmpty()) {
//                    reservation.setName(name);
//                    break;
//                } else {
//                    System.out.println("Name cannot be empty. Please enter a valid name.");
//                }
//            }
//            // Prompt for email until a valid non-null value is entered
//            while (true) {
//                String email = getInputString("Enter Customer Email: ");
//                if (email != null && !email.trim().isEmpty()) {
//                    reservation.setEmail(email);
//                    break;
//                } else {
//                    System.out.println("Email cannot be empty. Please enter a valid email.");
//                }
//            }
//            boolean validDate = false;
//            while (!validDate) {
//                try {
//                    String dateInput = getInputString("Enter Date and Time (YYYY-MM-DD HH:MM): ");
//                    reservation.setDateTime(LocalDateTime.parse(dateInput, LocalDateTimeAdapter.getFormatterWithoutT()));
//                    validDate = true;
//                } catch (Exception e) {
//                    System.out.println("Invalid date format. Please use 'YYYY-MM-DD HH:MM'.");
//                }
//            }
//            // Number of Adults with validation
//            while (true) {
//                try {
//                    reservation.setNumberOfAdults(Integer.parseInt(getInputString("Enter Number of Adults: ")));
//                    break;
//                } catch (NumberFormatException e) {
//                    System.out.println("Invalid input. Please enter a valid number for adults.");
//                }
//            }
//
//            // Number of Seniors with validation
//            while (true) {
//                try {
//                    reservation.setNumberOfSeniors(Integer.parseInt(getInputString("Enter Number of Seniors (Over the age of 65: 15% discount):")));
//                    break;
//                } catch (NumberFormatException e) {
//                    System.out.println("Invalid input. Please enter a valid number for seniors.");
//                }
//            }
//
//            // Number of Children with validation
//            while (true) {
//                try {
//                    reservation.setNumberOfChildren(Integer.parseInt(getInputString("Enter Number of Children (Under the age of 10: 20% discount): ")));
//                    break;
//                } catch (NumberFormatException e) {
//                    System.out.println("Invalid input. Please enter a valid number for children.");
//                }
//            }
//
//            // Coupon Discount with validation
//            while (true) {
//                try {
//                    reservation.setCouponDiscount(Double.parseDouble(getInputString("Enter Coupon Discount (as a percentage, e.g., 30 for 30% off): ")) / 100);
//                    break;
//                } catch (NumberFormatException e) {
//                    System.out.println("Invalid input. Please enter a valid discount percentage.");
//                }
//            }
//
//            // Calculates the total cost after all details have been set
//            reservation.calculateTotalCost();
//
//            // Returns the reservation object
//            return reservation;
//
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid input. Please enter valid numbers for adults, seniors, and children.");
//            return null;
//        }
//    }
//
//    /**
//     * Gathers the necessary reservation details from the user through the GUI (JOptionPane).
//     * @author: Sherri Ashton
//     * @since: 2024-10-02
//     * @return A Reservation object containing the entered information.
//     */
//    private Reservation gatherReservationInfoGUI() {
//        try {
//            Reservation reservation = new Reservation();
//            // Prompt for name until a valid non-null value is entered
//            while (true) {
//                String name = JOptionPane.showInputDialog("Enter Customer Name: ");
//                if (name != null && !name.trim().isEmpty()) {
//                    reservation.setName(name);
//                    break;
//                } else {
//                    JOptionPane.showMessageDialog(null, "Name cannot be empty. Please enter a valid name.");
//                }
//            }
//            // Prompt for email until a valid non-null value is entered
//            while (true) {
//                String email = JOptionPane.showInputDialog("Enter Customer Email: ");
//                if (email != null && !email.trim().isEmpty()) {
//                    reservation.setEmail(email);
//                    break;
//                } else {
//                    JOptionPane.showMessageDialog(null, "Email cannot be empty. Please enter a valid email.");
//                }
//            }
//            boolean validDate = false;
//            while (!validDate) {
//                try {
//                    String dateInput = JOptionPane.showInputDialog("Enter Date and Time (YYYY-MM-DD HH:MM):");
//                    reservation.setDateTime(LocalDateTime.parse(dateInput, LocalDateTimeAdapter.getFormatterWithoutT()));
//                    validDate = true;
//                } catch (Exception e) {
//                    JOptionPane.showMessageDialog(null, "Invalid date format. Please use 'YYYY-MM-DD HH:MM'.");
//                }
//            }
//            // Number of Adults with validation
//            while (true) {
//                try {
//                    reservation.setNumberOfAdults(Integer.parseInt(JOptionPane.showInputDialog("Enter Number of Adults: ")));
//                    break;
//                } catch (NumberFormatException e) {
//                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number for adults.");
//                }
//            }
//
//            // Number of Seniors with validation
//            while (true) {
//                try {
//                    reservation.setNumberOfSeniors(Integer.parseInt(JOptionPane.showInputDialog("Enter Number of Seniors (Over the age of 65: 15% discount): ")));
//                    break;
//                } catch (NumberFormatException e) {
//                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number for seniors.");
//                }
//            }
//
//            // Number of Children with validation
//            while (true) {
//                try {
//                    reservation.setNumberOfChildren(Integer.parseInt(JOptionPane.showInputDialog("Enter Number of Children (Under the age of 10: 20% discount): ")));
//                    break;
//                } catch (NumberFormatException e) {
//                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number for children.");
//                }
//            }
//
//            // Coupon Discount with validation
//            while (true) {
//                try {
//                    reservation.setCouponDiscount(Double.parseDouble(JOptionPane.showInputDialog("Enter Coupon Discount (as a percentage, e.g., 30 for 30% of): ")) / 100);
//                    break;
//                } catch (NumberFormatException e) {
//                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid discount percentage. ");
//                }
//            }
//            // Calculates the total cost after all details have been set
//            reservation.calculateTotalCost();
//
//            // Returns the reservation object
//            return reservation;
//
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid input. Please enter valid numbers for adults, seniors, and children.");
//            return null;
//        }
//    }
//
//    /**
//     * Reloads the reservation data from the JSON file to ensure that both threads
//     *  have access to the same up-to-date data.
//     * This method first clears the current list of reservations to prevent duplicates,
//     * then reloads the reservation data from the file. It is synchronized to ensure that
//     * no other thread can modify the list of reservations while it is being reloaded.
//     *
//     * @author Sherri Ashton
//     * @since 2024-10-03
//     */
//    private synchronized void reloadReservationsFromFile() {
//        reservations.clear(); // Clears the current list
//        loadReservationsFromFile(); // Reloads from the file
//    }
//
//    /**
//     * Displays all reservations in the console. Once all reservations are displayed, it shows
//     * the total number of reservations made.
//     * @author: Sherri Ashton
//     * @since: 2024-10-02
//     */
//    private void viewReservationsConsole() {
//        reloadReservationsFromFile(); // Reloads the latest reservations from the file
//        synchronized (reservations) {
//            if (reservations.isEmpty()) {
//                System.out.println("No reservations available.");
//            } else {
//                for (Reservation reservation : reservations) {
//                    System.out.println(reservation);
//                }
//                // After viewing all reservations, displays the total reservation count
//                System.out.println("You have reached the end of the reservations.");
//                System.out.println("Total reservations: " + totalReservationCount); // Displays the total count
//
//            }
//        }
//    }
//    /**
//     * Displays all reservations in the GUI (JOptionPane). Once all reservations are displayed,
//     * it shows the total number of reservations made in a message box.
//     * @author: Sherri Ashton
//     * @since: 2024-10-02
//     */
//    private void viewReservationsGUI() {
//        reloadReservationsFromFile(); // Reloads the latest reservations from the file
//        synchronized (reservations) {
//            if (reservations.isEmpty()) {
//                JOptionPane.showMessageDialog(null, "No reservations available.",
//                        "Information",
//                        JOptionPane.INFORMATION_MESSAGE);
//                return; // Exits early if there are no reservations
//            }
//
//            // Creates a local variable to track the current reservation index
//            int currentIndex = 0;
//
//            while (currentIndex < reservations.size()) {
//                Reservation reservation = reservations.get(currentIndex);
//                if (reservation != null) { // Ensures the reservation is not null
//                    JOptionPane.showMessageDialog(null, reservation.toString(),
//                            "Reservation Details",
//                            JOptionPane.INFORMATION_MESSAGE);
//                }
//
//                // Checks if we are at the last reservation
//                if (currentIndex == reservations.size() - 1) {
//                    JOptionPane.showMessageDialog(null, "You have reached the end of the reservations.",
//                            "End of Reservations",
//                            JOptionPane.INFORMATION_MESSAGE);
//
//                    // After the last reservation, displays total reservation count in its own message box
//                    JOptionPane.showMessageDialog(null, "Total reservations: " + totalReservationCount,
//                            "Total Reservation Count",
//                            JOptionPane.INFORMATION_MESSAGE);
//                    break; // Exits the loop after the last reservation
//                }
//
//                // Prompts user to continue to the next reservation
//                int response = JOptionPane.showConfirmDialog(null, "Would you like to see the next reservation?",
//                        "Next Reservation",
//                        JOptionPane.YES_NO_OPTION);
//
//                // If the user clicked 'No', break out of the loop
//                if (response != JOptionPane.YES_OPTION) {
//                    break; // Exits the loop if the user chooses not to continue
//                }
//
//                currentIndex++; // Moves to the next reservation
//            }
//        }
//    }
//
//    /**
//     * Saves the entire list of reservations to a JSON file.
//     * The method is synchronized to ensure thread safety while writing to the file.
//     * @author: Sherri Ashton
//     * @since: 2024-10-02
//     */
//    private synchronized void saveReservationToFile(Reservation reservation) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Config.FOLDER_NAME + Config.FILE_NAME_CUSTOM_JSON, true))) {
//            writer.write(gson.toJson(reservation));
//            writer.newLine();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    // Save all reservations to a file
//    private synchronized void saveReservationsToFile() {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Config.FOLDER_NAME + Config.FILE_NAME_CUSTOM_JSON))) {
//            for (Reservation reservation : reservations) {
//                writer.write(gson.toJson(reservation));
//                writer.newLine();
//            }
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    /**
//     * Utility method to get input from the console.
//     * @author: Sherri Ashton
//     * @since: 2024-10-02
//     * @return The input string entered by the user.
//     */
//    private String getInputString() {
//        return new java.util.Scanner(System.in).nextLine();
//    }
//
//    /**
//     * Overloaded utility method to get input from the console with a custom message.
//     * @author: Sherri Ashton
//     * @since: 2024-10-02
//     * @return The input string entered by the user.
//     */
//    private String getInputString(String message) {
//        System.out.println(message);
//        return getInputString();
//    }
//}