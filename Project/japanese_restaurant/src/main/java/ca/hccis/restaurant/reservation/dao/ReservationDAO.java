package ca.hccis.restaurant.reservation.dao;

import ca.hccis.restaurant.reservation.entity.Reservation;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReservationDAO {

    private static ResultSet rs;
    private static Connection conn = null;
    private static final Logger logger = LoggerFactory.getLogger(ReservationDAO.class);

    // Constructor to establish a connection
    public ReservationDAO() {
        String propFileName = "application";
        ResourceBundle rb = ResourceBundle.getBundle(propFileName);
        String connectionString = rb.getString("spring.datasource.url");
        String userName = rb.getString("spring.datasource.username");
        String password = rb.getString("spring.datasource.password");

        try {
            conn = DriverManager.getConnection(connectionString, userName, password);
        } catch (SQLException e) {
            logger.error("Error establishing connection: " + e.getMessage());
        }
    }

    // Selects all reservations
    public ArrayList<Reservation> selectAll() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation";

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setIdCounter(rs.getInt("id"));
                reservation.setName(rs.getString("name"));
                reservation.setEmail(rs.getString("email"));
                reservation.setDateTime(rs.getTimestamp("dateTime").toLocalDateTime());
                reservation.setNumberOfAdults(rs.getInt("numberOfAdults"));
                reservation.setNumberOfSeniors(rs.getInt("numberOfSeniors"));
                reservation.setNumberOfChildren(rs.getInt("numberOfChildren"));
                reservation.setCouponDiscount(rs.getDouble("couponDiscount"));
                reservation.setTotalCost(rs.getDouble("totalCost"));
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            logger.error("Error fetching reservations: " + e.getMessage());
        }
        return reservations;
    }

    // Selects reservations by date range
    public ArrayList<Reservation> selectAllByDateRange(String start, String end) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation WHERE dateTime >= ? AND dateTime <= ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, start);
            stmt.setString(2, end);
            ResultSet rs = stmt.executeQuery();
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
                reservation.setTotalCost(rs.getDouble("finalBill"));
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            logger.error("Error fetching reservations by date range: " + e.getMessage());
        }
        return reservations;
    }

    // Insert new reservation
    public void insert(Reservation reservation) {
        String query = "INSERT INTO reservation (name, email, numberOfAdults, numberOfSeniors, numberOfChildren, dateTime, couponDiscount) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getEmail());
            ps.setInt(3, reservation.getNumberOfAdults());
            ps.setInt(4, reservation.getNumberOfSeniors());
            ps.setInt(5, reservation.getNumberOfChildren());
            ps.setTimestamp(6, Timestamp.valueOf(reservation.getDateTime()));
            ps.setDouble(7, reservation.getCouponDiscount());
            ps.setDouble(8, reservation.getTotalCost());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error inserting reservation: " + e.getMessage());
        }
    }
    public void delete(int id) {
        String query = "DELETE FROM reservation WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting reservation: " + e.getMessage());
        }
    }

}
