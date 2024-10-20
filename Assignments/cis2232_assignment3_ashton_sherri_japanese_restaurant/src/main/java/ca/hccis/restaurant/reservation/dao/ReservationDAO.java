package ca.hccis.restaurant.reservation.dao;

import ca.hccis.restaurant.reservation.entity.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * DAO class to access reservation data.
 */
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
     * Select reservations by date range.
     */
    public ArrayList<Reservation> selectAllByDateRange(String startDate, String endDate) {
        List<Reservation> reservations = new ArrayList<>();
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            String query = "SELECT * FROM Reservation WHERE dateTime >= '" + startDate + "' AND dateTime <= '" + endDate + "';";
            rs = stmt.executeQuery(query);

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

                reservations.add(reservation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                System.out.println("There was an error closing");
            }
        }
        return (ArrayList<Reservation>) reservations;
    }

    /**
     * Insert a new reservation.
     */
    public void insert(Reservation reservation) {
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO Reservation (name, email, dateTime, numberOfAdults, numberOfSeniors, numberOfChildren, couponDiscount) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?);";
            ps = conn.prepareStatement(sql);
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getEmail());
            ps.setTimestamp(3, Timestamp.valueOf(reservation.getDateTime()));
            ps.setInt(4, reservation.getNumberOfAdults());
            ps.setInt(5, reservation.getNumberOfSeniors());
            ps.setInt(6, reservation.getNumberOfChildren());
            ps.setDouble(7, reservation.getCouponDiscount());

            ps.executeUpdate();
            System.out.println("Reservation inserted successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error creating statement");
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Couldn't close something.");
            }
        }
    }

    /**
     * Select reservations with a minimum number of guests.
     */
    public List<Reservation> selectAllWithMinLength(int minGuests) {
        List<Reservation> reservations = new ArrayList<>();  // Use List<Reservation>
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            String query = "SELECT * FROM Reservation WHERE numberOfAdults + numberOfSeniors + numberOfChildren >= " + minGuests + ";";
            rs = stmt.executeQuery(query);

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

                reservations.add(reservation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                System.out.println("There was an error closing");
            }
        }
        return reservations;  // Return List<Reservation>
    }

}
