package ca.hccis.photography.session.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Reservation {
    private static int idCounter = 1;
    private final int id;
    private String name;
    private int numberOfCustomers;
    private LocalDateTime dateTime;
    private String email;

    // Declares the DateTimeFormatter at the class level
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Reservation() {
        this.id = idCounter;
        idCounter++;
    }
    // This method allows a setting idCounter externally
    public static void setIdCounter(int newIdCounter) {
        idCounter = newIdCounter;
    }


    public void getInformation() {
        Scanner input = new Scanner(System.in);


        System.out.println("\n--- Enter Reservation Details ---");

        System.out.println("Name: ");
        name = input.nextLine();

        System.out.println("Number of Customers: ");
        numberOfCustomers = input.nextInt();
        input.nextLine();

        boolean validDate = false;
        while (!validDate) {
            System.out.println("Date and Time (YYYY-MM-DD HH:MM): ");
            String dateInput = input.nextLine();
            try {
                dateTime = LocalDateTime.parse(dateInput, DATE_TIME_FORMATTER);
                validDate = true;  // Date is valid
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format! Please use the format 'YYYY-MM-DD HH:MM'.");
            }
        }

        System.out.println("Email: ");
        email = input.nextLine();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + id + System.lineSeparator() +
                "Name: " + name + System.lineSeparator() +
                "Number of Customers: " + numberOfCustomers + System.lineSeparator() +
                "Date and Time: " + dateTime.format(DATE_TIME_FORMATTER) + System.lineSeparator() +
                "Email: " + email + System.lineSeparator();
    }

}
