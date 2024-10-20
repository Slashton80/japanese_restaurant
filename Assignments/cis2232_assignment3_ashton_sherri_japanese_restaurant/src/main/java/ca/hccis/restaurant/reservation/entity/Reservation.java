package ca.hccis.restaurant.reservation.entity;

import ca.hccis.restaurant.reservation.exception.ReservationException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * This class represents a reservation made at the restaurant. Includes discounts and calculation.
 *
 * @author sherri ashton
 * @since 2024-09-20
 */
public class Reservation {

    private static int idCounter = 1;
    private final int id;
    private String name;
    private int numberOfAdults;
    private int numberOfSeniors;
    private int numberOfChildren;
    //private int numberOfCustomers;
    private LocalDateTime dateTime;
    private String email;
    private double couponDiscount;
    private static final double COST_PER_CUSTOMER = 25.0;
    private static final double CHILDREN_DISCOUNT = 0.20;
    private static final double SENIOR_DISCOUNT = 0.15;
    final static String BUSINESS = "JAPANESE RESTAURANT";
    // Declares the DateTimeFormatter at the class level
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private double totalCost;

    public Reservation() {
        this.id = idCounter;
        idCounter++;

    }

    /**
     * This method allows a setting idCounter externally
     *
     * @author sherri ashton
     * @since 2024-09-20
     */
//    public static void setIdCounter(int newIdCounter) {
//        idCounter = newIdCounter;
//    }
//
//    private static synchronized int loadIdCounter() {
//        File file = new File(ID_COUNTER_FILE);
//        if (file.exists()) {
//            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//                int loadedId = Integer.parseInt(reader.readLine());
//                System.out.println("Loaded ID counter: " + loadedId); // Debug print
//                return loadedId;
//            } catch (IOException | NumberFormatException e) {
//                e.printStackTrace();
//                return 1; // Default to 1 if there's an error
//            }
//        }
//        System.out.println("No ID counter file found, starting from 1"); // Debug print
//        return 1; // Start from 1 if the file doesn't exist
//    }


//    public void getInformation() {
//        Scanner input = new Scanner(System.in);
//
//
//        System.out.println("\n--- Enter Reservation Details ---");
//
//        System.out.println("Name: ");
//        name = input.nextLine();
//
//        System.out.println("Email: ");
//        email = input.nextLine();
//
//        boolean validDate = false;
//        while (!validDate) {
//            System.out.println("Date and Time (YYYY-MM-DD HH:MM): ");
//            String dateInput = input.nextLine();
//            try {
//                dateTime = LocalDateTime.parse(dateInput, DATE_TIME_FORMATTER);
//                validDate = true;
//            } catch (DateTimeParseException e) {
//                System.out.println("Invalid format! Please use the format 'YYYY-MM-DD HH:MM'.");
//            }
//        }
//
//        System.out.println("Number of Adults: ");
//        numberOfAdults = input.nextInt();
//        input.nextLine();
//
//        System.out.println("Number of Seniors: ");
//        numberOfSeniors = input.nextInt();
//        input.nextLine();
//
//        System.out.println("Number of Children: ");
//        numberOfChildren = input.nextInt();
//        input.nextLine();
//
//        System.out.println("Coupon Discount (as a percentage, e.g., 30 for 30% off): ");
//        couponDiscount = input.nextDouble() / 100;
//    }
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getCouponDiscount() {
        return couponDiscount;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    public int getNumberOfSeniors() {
        return numberOfSeniors;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfSeniors(int numberOfSeniors) {
        this.numberOfSeniors = numberOfSeniors;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public void setCouponDiscount(double couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public static void setIdCounter(int newIdCounter) {
        idCounter = newIdCounter;
    }

    /**
     * This method calculates the total cost of the reservation based on the number of customers,
     * applying discounts for seniors, children, and any provided coupon discount.
     *
     * @return total cost
     * @author sherri ashton
     * @since 2024-09-27 (Made changes Oct 3)
     */
    public double calculateTotalCost() {
        if (numberOfAdults < 0 || numberOfChildren < 0 || numberOfSeniors < 0) {
            throw new ReservationException("Number of customers cannot be negative.");
        }

        // Calculate the cost for adults (no discount)
        double totalCost = numberOfAdults * COST_PER_CUSTOMER;

        // Calculate the cost for seniors (15% off)
        double seniorCost = numberOfSeniors * COST_PER_CUSTOMER * (1 - SENIOR_DISCOUNT);

        // Calculate the cost for children (20% off)
        double childrenCost = numberOfChildren * COST_PER_CUSTOMER * (1 - CHILDREN_DISCOUNT);

        // Add the costs for seniors and children to the total
        totalCost += seniorCost + childrenCost;

        // Apply rounding to two decimal places
        BigDecimal roundedTotalCost = new BigDecimal(totalCost).setScale(2, RoundingMode.HALF_UP);
        this.totalCost = roundedTotalCost.doubleValue();

        this.totalCost = totalCost;
        return totalCost;
    }

    @Override
    public String toString() {
        return "\nReservation ID: " + id + System.lineSeparator() +
                "Name: " + name + System.lineSeparator() +
                "Email: " + email + System.lineSeparator() +
                "Date and Time: " + dateTime.format(DATE_TIME_FORMATTER) + System.lineSeparator() +
                "Number of Adults: " + numberOfAdults + System.lineSeparator() +
                "Number of Seniors: " + numberOfSeniors + System.lineSeparator() +
                "Number of Children: " + numberOfChildren + System.lineSeparator() +
                "Coupon Discount: " + (couponDiscount * 100) + "%" + System.lineSeparator() +
                "Total Cost: " + totalCost + System.lineSeparator();
    }

    /**
     * Reloads reservations from the JSON file, clearing the current list and loading
     * the most up-to-date data to ensure both GUI and console threads have access to the same
     * reservation information.
     *
     * @sources: https://www.geeksforgeeks.org/overriding-equals-method-in-java/,
     * https://www.codejava.net/java-core/collections/understanding-equals-and-hashcode-in-java#:~:text=This%20method%20returns%20an%20integer%20number%20based%20on
     * @author: Sherri Ashton
     * @since: 2024-10-03
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Reservation)) return false;
        Reservation other = (Reservation) obj;
        return this.id == other.id; // Compares based on the unique ID
    }

    /**
     * * Initializes the total reservation count by reading the number of reservations
     * from the JSON file. If the file exists, it sets the count based on the lines in the file,
     * otherwise, it starts with a fresh count of zero.
     *
     * @sources: https://www.geeksforgeeks.org/overriding-equals-method-in-java/,
     * https://www.codejava.net/java-core/collections/understanding-equals-and-hashcode-in-java#:~:text=This%20method%20returns%20an%20integer%20number%20based%20on
     * @author: Sherri Ashton
     * @since: 2024-10-03
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id); // Uses the ID for hash code
    }

//    public class ReportReservation {
//        private String dataStart;
//        private String dataEnd;
//        private ArrayList<Reservation> reservations;
//
//        public String getDataStart() {
//            return dataStart;
//        }
//
//        public void setDataStart(String dataStart) {
//            this.dataStart = dataStart;
//        }
//
//        public String getDataEnd() {
//            return dataEnd;
//        }
//
//        public void setDataEnd(String dataEnd) {
//            this.dataEnd = dataEnd;
//        }
//
//        public ArrayList<Reservation> getReservations() {
//            return reservations;
//        }
//
//        public void setReservations(ArrayList<Reservation> reservations) {
//            this.reservations = reservations;
//        }
//    }

}
