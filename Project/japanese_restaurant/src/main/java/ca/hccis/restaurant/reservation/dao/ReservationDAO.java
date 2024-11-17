package ca.hccis.restaurant.reservation.dao;

import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationDAO {
    private static ResultSet rs;
    private static Connection conn = null;
    private static final Logger logger = LoggerFactory.getLogger(ReservationDAO.class);

    public ReservationDAO() {

        String propFileName = "application";
        ResourceBundle rb = ResourceBundle.getBundle(propFileName);
        String connectionString = rb.getString("spring.datasource.url");
        String userName = rb.getString("spring.datasource.username");
        String password = rb.getString("spring.datasource.password");

        try {
            conn = DriverManager.getConnection(connectionString, userName, password);
        } catch (SQLException e) {
            logger.error(e.toString());
        }

    }

    /**
     * Select all
     *
     * @since 20210924
     * @author BJM
     */
    public ArrayList<Reservation> selectAll() {
        ArrayList<Reservation> passes = null;
        Statement stmt = null;

        //******************************************************************
        //Use the DriverManager to get a connection to our MySql database.  Note
        //that in the dependencies, we added the Java connector to MySql which
        //will allow us to connect to a MySql database.
        //******************************************************************
        //******************************************************************
        //Create a statement object using our connection to the database.  This
        //statement object will allow us to run sql commands against the database.
        //******************************************************************
        try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from reservation;");

            //******************************************************************
            //Loop through the result set using the next method.
            //******************************************************************
            passes = new ArrayList();

            while (rs.next()) {

                Reservation reservation = new Reservation();
                Reservation.setIdCounter(rs.getInt("id"));
                reservation.setName(rs.getString("name"));
                reservation.setEmail(rs.getString("email"));
                reservation.setDateTime(rs.getTimestamp("dateTime").toLocalDateTime());
                reservation.setNumberOfAdults(rs.getInt("numberOfAdults"));
                reservation.setNumberOfSeniors(rs.getInt("numberOfSeniors"));
                reservation.setNumberOfChildren(rs.getInt("numberOfChildren"));
                reservation.setCouponDiscount(rs.getDouble("couponDiscount"));

                passes.add(reservation);
            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            try {
                stmt.close();
            } catch (SQLException ex) {
                System.out.println("There was an error closing");
            }
        }
        return passes;
    }

    /**
     * Select all by date range
     *
     * @since 20241010
     * @author BJM
     */
    public ArrayList<Reservation> selectAllByDateRange(String start, String end) {
        ArrayList<Reservation> passes = null;
        Statement stmt = null;

        //******************************************************************
        //Use the DriverManager to get a connection to our MySql database.  Note
        //that in the dependencies, we added the Java connector to MySql which
        //will allow us to connect to a MySql database.
        //******************************************************************
        //******************************************************************
        //Create a statement object using our connection to the database.  This
        //statement object will allow us to run sql commands against the database.
        //******************************************************************
        try {
            stmt = conn.createStatement();
            String sqlStatement = "SELECT * FROM reservation " +
                    "WHERE reservationDateTime >= '" + start +
                    "' AND reservationDateTime <= '" + end + "';";

            rs = stmt.executeQuery(sqlStatement);

            passes = new ArrayList<>();
            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(rs.getInt("id")); // Maps the ID column
                reservation.setName(rs.getString("name")); // Maps the Name column
                reservation.setEmail(rs.getString("email")); // Maps the Email column
                reservation.setDateTime(rs.getTimestamp("reservationDateTime").toLocalDateTime()); // Maps DateTime
                reservation.setNumberOfAdults(rs.getInt("numberOfAdults")); // Maps Number of Adults
                reservation.setNumberOfSeniors(rs.getInt("numberOfSeniors")); // Maps Number of Seniors
                reservation.setNumberOfChildren(rs.getInt("numberOfChildren")); // Maps Number of Children
                reservation.setCouponDiscount(rs.getDouble("couponDiscount")); // Maps Coupon Discount
                reservation.setTotalCost(rs.getBigDecimal("totalCost")); // Maps Total Cost

                passes.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                System.out.println("There was an error closing");
            }
        }
        return passes;
    }
    /**
     * Select all for min length report
     *
     * @since 20241011
     * @author BJM
     */
    public ArrayList<Reservation> selectAllWithMinLength(int minLength) {
        ArrayList<Reservation> passes = new ArrayList<>();
        String sql = "SELECT * FROM reservation WHERE LENGTH(name) >= ?";

        //******************************************************************
        //Use the DriverManager to get a connection to our MySql database.  Note
        //that in the dependencies, we added the Java connector to MySql which
        //will allow us to connect to a MySql database.
        //******************************************************************
        //******************************************************************
        //Create a statement object using our connection to the database.  This
        //statement object will allow us to run sql commands against the database.
        //******************************************************************
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, minLength);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(rs.getInt("id")); // Maps the ID column
                reservation.setName(rs.getString("name")); // Maps the Name column
                reservation.setEmail(rs.getString("email")); // Maps the Email column
                reservation.setDateTime(rs.getTimestamp("reservationDateTime").toLocalDateTime()); // Maps DateTime
                reservation.setNumberOfAdults(rs.getInt("numberOfAdults")); // Maps Number of Adults
                reservation.setNumberOfSeniors(rs.getInt("numberOfSeniors")); // Maps Number of Seniors
                reservation.setNumberOfChildren(rs.getInt("numberOfChildren")); // Maps Number of Children
                reservation.setCouponDiscount(rs.getDouble("couponDiscount")); // Maps Coupon Discount
                reservation.setTotalCost(rs.getBigDecimal("totalCost")); // Maps Total Cost

                passes.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passes;
    }

//    private static final Logger logger = LoggerFactory.getLogger(ReservationDAO.class);
//
//    public ReservationDAO() {
//        // The connection could be managed by a connection pool, e.g., using DataSource
//    }
//
//    /**
//     * Select reservations by date range.
//     */
//    public List<Reservation> selectAllByDateRange(String startDate, String endDate) {
//        String query = "SELECT * FROM Reservation WHERE dateTime >= ? AND dateTime <= ?";
//        List<Reservation> reservations = new ArrayList<>();
//        try (Connection conn = DriverManager.getConnection(getConnectionString(), getUserName(), getPassword());
//             PreparedStatement ps = conn.prepareStatement(query)) {
//            ps.setString(1, startDate);
//            ps.setString(2, endDate);
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    reservations.add(mapResultSetToReservation(rs));
//                }
//            }
//        } catch (SQLException e) {
//            logger.error("Error while selecting reservations by date range", e);
//        }
//        return reservations;
//    }
//
//    /**
//     * Insert a new reservation.
//     */
//    public void insert(Reservation reservation) {
//        String sql = "INSERT INTO Reservation (name, email, dateTime, numberOfAdults, numberOfSeniors, numberOfChildren, couponDiscount) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?)";
//        try (Connection conn = DriverManager.getConnection(getConnectionString(), getUserName(), getPassword());
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, reservation.getName());
//            ps.setString(2, reservation.getEmail());
//            ps.setTimestamp(3, Timestamp.valueOf(reservation.getDateTime()));
//            ps.setInt(4, reservation.getNumberOfAdults());
//            ps.setInt(5, reservation.getNumberOfSeniors());
//            ps.setInt(6, reservation.getNumberOfChildren());
//            ps.setDouble(7, reservation.getCouponDiscount());
//            ps.executeUpdate();
//            logger.info("Reservation inserted successfully!");
//        } catch (SQLException e) {
//            logger.log("Error while inserting reservation", e);
//        }
//    }
//
//    /**
//     * Helper method to map ResultSet to Reservation.
//     */
//    private Reservation mapResultSetToReservation(ResultSet rs) throws SQLException {
//        Reservation reservation = new Reservation();
//        Reservation.setIdCounter(rs.getInt("id"));
//        reservation.setName(rs.getString("name"));
//        reservation.setEmail(rs.getString("email"));
//        reservation.setDateTime(rs.getTimestamp("dateTime").toLocalDateTime());
//        reservation.setNumberOfAdults(rs.getInt("numberOfAdults"));
//        reservation.setNumberOfSeniors(rs.getInt("numberOfSeniors"));
//        reservation.setNumberOfChildren(rs.getInt("numberOfChildren"));
//        reservation.setCouponDiscount(rs.getDouble("couponDiscount"));
//        return reservation;
//    }
//
//    // Assuming you have methods to fetch these values
//    private String getConnectionString() {
//        ResourceBundle rb = ResourceBundle.getBundle("application");
//        return rb.getString("spring.datasource.url");
//    }
//
//    private String getUserName() {
//        ResourceBundle rb = ResourceBundle.getBundle("application");
//        return rb.getString("spring.datasource.username");
//    }
//
//    private String getPassword() {
//        ResourceBundle rb = ResourceBundle.getBundle("application");
//        return rb.getString("spring.datasource.password");
//    }
}
