package ca.hccis.restaurant.reservation.dao;

import ca.hccis.restaurant.reservation.jpa.entity.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Data Access Object (DAO) for the Reservation entity.
 * This class handles database operations such as selecting, updating, and inserting
 * reservations using JDBC.
 *
 * @author BJM
 * @modifiedBy Sherri Ashton
 * @since 2024-10-10
 */
public class ReservationDAO {
    private static ResultSet rs;
    private static Connection conn = null;
    private static final Logger logger = LoggerFactory.getLogger(ReservationDAO.class);

    /**
     * Constructor to establish the database connection using credentials
     * and connection string from the application properties file.
     *
     * @author Sherri Ashton
     * @since 2024-10-25
     */
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
     * Retrieves all reservations from the database.
     *
     * @return List of all reservations.
     * @author Sherri Ashton
     * @since 2024-10-25
     */
    public ArrayList<Reservation> selectAll() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation";

        //******************************************************************
        //Use the DriverManager to get a connection to our MySql database.  Note
        //that in the dependencies, we added the Java connector to MySql which
        //will allow us to connect to a MySql database.
        //******************************************************************
        //******************************************************************
        //Create a statement object using our connection to the database.  This
        //statement object will allow us to run sql commands against the database.
        //******************************************************************
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            //******************************************************************
            //Loop through the result set using the next method.
            //******************************************************************
            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(rs.getInt("id"));
                reservation.setName(rs.getString("name"));
                reservation.setEmail(rs.getString("email"));
                reservation.setDateTime(rs.getTimestamp("reservationDateTime").toLocalDateTime());
                reservation.setNumberOfAdults(rs.getInt("numberOfAdults"));
                reservation.setNumberOfSeniors(rs.getInt("numberOfSeniors"));
                reservation.setNumberOfChildren(rs.getInt("numberOfChildren"));
                reservation.setCouponDiscount(rs.getDouble("couponDiscount"));
                reservation.setTotalCost(rs.getBigDecimal("totalCost"));
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }


    /**
     * Select all by date range
     *
     * @author BJM
     * @since 20241010. Updated for reservation by Sherri Ashton, 2024-10-25.
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
                reservation.setId(rs.getInt("id"));
                reservation.setName(rs.getString("name"));
                reservation.setEmail(rs.getString("email"));
                reservation.setDateTime(rs.getTimestamp("reservationDateTime").toLocalDateTime());
                reservation.setNumberOfAdults(rs.getInt("numberOfAdults"));
                reservation.setNumberOfSeniors(rs.getInt("numberOfSeniors"));
                reservation.setNumberOfChildren(rs.getInt("numberOfChildren"));
                reservation.setCouponDiscount(rs.getDouble("couponDiscount"));
                reservation.setTotalCost(rs.getBigDecimal("totalCost"));

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
     * Updates reservations from the database.
     *
     * @author Sherri Ashton
     * @since 2024-10-25
     */
    public void saveOrUpdate(Reservation reservation) {
        if (reservation.getId() != null) {
            // Updates existing reservation
            String sql = "UPDATE reservation SET name = ?, email = ?, reservationDateTime = ?, " +
                    "numberOfAdults = ?, numberOfSeniors = ?, numberOfChildren = ?, " +
                    "couponDiscount = ?, totalCost = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, reservation.getName());
                pstmt.setString(2, reservation.getEmail());
                pstmt.setTimestamp(3, Timestamp.valueOf(reservation.getReservationDateTime()));
                pstmt.setInt(4, reservation.getNumberOfAdults());
                pstmt.setInt(5, reservation.getNumberOfSeniors());
                pstmt.setInt(6, reservation.getNumberOfChildren());
                pstmt.setDouble(7, reservation.getCouponDiscount());
                pstmt.setBigDecimal(8, reservation.getTotalCost());
                pstmt.setInt(9, reservation.getId());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Inserts new reservation
            String sql = "INSERT INTO reservation (name, email, reservationDateTime, numberOfAdults, " +
                    "numberOfSeniors, numberOfChildren, couponDiscount, totalCost) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, reservation.getName());
                pstmt.setString(2, reservation.getEmail());
                pstmt.setTimestamp(3, Timestamp.valueOf(reservation.getReservationDateTime()));
                pstmt.setInt(4, reservation.getNumberOfAdults());
                pstmt.setInt(5, reservation.getNumberOfSeniors());
                pstmt.setInt(6, reservation.getNumberOfChildren());
                pstmt.setDouble(7, reservation.getCouponDiscount());
                pstmt.setBigDecimal(8, reservation.getTotalCost());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Selects reservations from the database by name.
     *
     * @return name.
     * @author Sherri Ashton
     * @since 2024-10-25
     */
    public List<Reservation> selectByName(String name) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation WHERE name LIKE ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation();
                    reservation.setId(rs.getInt("id"));
                    reservation.setName(rs.getString("name"));
                    reservation.setEmail(rs.getString("email"));
                    reservation.setDateTime(rs.getTimestamp("reservationDateTime").toLocalDateTime());
                    reservation.setNumberOfAdults(rs.getInt("numberOfAdults"));
                    reservation.setNumberOfSeniors(rs.getInt("numberOfSeniors"));
                    reservation.setNumberOfChildren(rs.getInt("numberOfChildren"));
                    reservation.setCouponDiscount(rs.getDouble("couponDiscount"));
                    reservation.setTotalCost(rs.getBigDecimal("totalCost"));
                    reservations.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    /**
     * Retrieves reservations from the database by id.
     *
     * @return id.
     * @author Sherri Ashton
     * @since 2024-10-25
     */
    public Reservation selectById(int id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Reservation reservation = new Reservation();
                    reservation.setId(rs.getInt("id"));
                    reservation.setName(rs.getString("name"));
                    reservation.setEmail(rs.getString("email"));
                    reservation.setDateTime(rs.getTimestamp("reservationDateTime").toLocalDateTime());
                    reservation.setNumberOfAdults(rs.getInt("numberOfAdults"));
                    reservation.setNumberOfSeniors(rs.getInt("numberOfSeniors"));
                    reservation.setNumberOfChildren(rs.getInt("numberOfChildren"));
                    reservation.setCouponDiscount(rs.getDouble("couponDiscount"));
                    reservation.setTotalCost(rs.getBigDecimal("totalCost"));
                    return reservation;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
