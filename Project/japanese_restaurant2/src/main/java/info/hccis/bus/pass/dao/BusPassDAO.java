package info.hccis.bus.pass.dao;

import info.hccis.bus.pass.entity.BusPass;
import info.hccis.bus.pass.jpa.entity.TicketOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DAO class to access ticket orders.
 *
 * @author bjmaclean
 * @since 20220621
 */
public class BusPassDAO {

    private static ResultSet rs;
    private static Connection conn = null;
    private static final Logger logger = LoggerFactory.getLogger(BusPassDAO.class);

    public BusPassDAO() {

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

//    /**
//     * Select ticket orders in a given date range.
//     *
//     * @since 20220621
//     * @author BJM
//     */
//    public ArrayList<TicketOrder> selectTicketOrders(String start, String end) {
//        PreparedStatement stmt;
//        ArrayList<TicketOrder> ticketOrders = new ArrayList();
//        try {
//            String query = "SELECT * FROM TicketOrder ticketorder "
//                    + "WHERE ticketorder.dateOfOrder > ? "
//                    + "and ticketorder.dateOfOrder < ?";
//            stmt = conn.prepareStatement(query);
//            stmt.setString(1, start);
//            stmt.setString(2, end);
//            rs = stmt.executeQuery();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            while (rs.next()) {
//                TicketOrder ticketOrder = new TicketOrder();
//                ticketOrder.setId(rs.getInt("id"));
//                ticketOrder.setCustomerName(rs.getString("customerName"));
//                ticketOrder.setDateOfOrder(rs.getString("dateOfOrder"));
//                ticketOrder.setDateOfPerformance(rs.getString("dateOfPerformance"));
//                ticketOrder.setTimeOfPerformance(rs.getString("timeOfPerformance"));
//                ticketOrder.setNumberOfTickets(rs.getInt("numberOfTickets"));
//                ticketOrder.setHollpassNumber(rs.getInt("hollpassNumber"));
//                ticketOrder.setCostOfTickets(rs.getBigDecimal("costOfTickets"));
//                ticketOrders.add(ticketOrder);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        logger.info("Found orders:  " + ticketOrders.size());
//        return ticketOrders;
//    }
//
//    /**
//     * Select ticket orders for a given customer by name
//     *
//     * @since 20220621
//     * @author BJM
//     */
//    public ArrayList<TicketOrder> selectTicketOrders(String customerName) {
//        PreparedStatement stmt;
//        ArrayList<TicketOrder> ticketOrders = new ArrayList();
//
//        //https://stackoverflow.com/questions/2857164/cannot-use-a-like-query-in-a-jdbc-preparedstatement
//        //Bitbucket Issue#5
//        String customerNameLike = "%"+customerName+"%";
//        try {
//            String query = "SELECT * FROM TicketOrder ticketorder "
//                    + "WHERE customerName LIKE ?";
//            stmt = conn.prepareStatement(query);
//            stmt.setString(1, customerNameLike);
//            rs = stmt.executeQuery();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            while (rs.next()) {
//                TicketOrder ticketOrder = new TicketOrder();
//
//                ticketOrder.setId(rs.getInt("id"));
//                ticketOrder.setCustomerName(rs.getString("customerName"));
//                ticketOrder.setDateOfOrder(rs.getString("dateOfOrder"));
//                ticketOrder.setDateOfPerformance(rs.getString("dateOfPerformance"));
//                ticketOrder.setTimeOfPerformance(rs.getString("timeOfPerformance"));
//                ticketOrder.setNumberOfTickets(rs.getInt("numberOfTickets"));
//                ticketOrder.setHollpassNumber(rs.getInt("hollpassNumber"));
//                ticketOrder.setCostOfTickets(rs.getBigDecimal("costOfTickets"));
//                ticketOrders.add(ticketOrder);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        logger.info("Found orders:  " + ticketOrders.size());
//        return ticketOrders;
//    }


    /**
     * Select all
     *
     * @since 20210924
     * @author BJM
     */
    public ArrayList<BusPass> selectAll() {
        ArrayList<BusPass> passes = null;
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
            rs = stmt.executeQuery("select * from BusPass;");

            //******************************************************************
            //Loop through the result set using the next method.  
            //******************************************************************
            passes = new ArrayList();

            while (rs.next()) {

                BusPass busPass = new BusPass();
                busPass.setId(rs.getInt(1));
                busPass.setName(rs.getString("name"));
                busPass.setAddress(rs.getString("address"));
                busPass.setCity(rs.getString("city"));
                busPass.setLengthOfPass(rs.getInt("lengthOfPass"));

                passes.add(busPass);
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

//    /**
//     * Select a student by name
//     *
//     * @since 20210924
//     * @author BJM
//     */
//    public Student selectByName(String name) {
//        Student student = null;
//        Statement stmt = null;
//        try {
//            stmt = conn.createStatement();
//            String query = "select * from Student where name='" + name + "';";
//            System.out.println(query);
//            rs = stmt.executeQuery(query);
//        } catch (SQLException ex) {
//            System.out.println("Error");
//            ex.printStackTrace();
//        }
//
//        try {
//            while (rs.next()) {
//                student = new Student(rs.getString("name"),
//                        rs.getInt("studentId"),
//                        rs.getString("program"));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return student;
//    }
//
    /**
     * Insert a student
     *
     * @since 20230921
     * @author BJM
     */
    public void insert(BusPass busPass) {
        PreparedStatement ps = null;
        try {
            //**************************************
            //Now can use the getters to get the info from the student object to be
            //passed into the executeUpdate method of the stmt object.
            //**************************************
            String sql = "INSERT INTO buspass (name, address, city, " +
                    "preferredRoute, passType, validForRuralRoute, " +
                    "lengthOfPass, startDate, cost) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
            ps = conn.prepareStatement(sql);
            ps.setString(1, busPass.getName());
            ps.setString(2, busPass.getAddress());
            ps.setString(3, busPass.getCity());
            ps.setString(4, busPass.getPreferredRoute());
            ps.setInt(5, busPass.getPassType());
            ps.setBoolean(6, busPass.isValidForRuralRoute());
            ps.setInt(7, busPass.getLengthOfPass());
            ps.setString(8, busPass.getStartDate());
            ps.setDouble(9, busPass.getCost());
            ps.executeUpdate();
            System.out.println(sql);

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error creating statement");
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                System.out.println("Couldn't close something.  ");
            }
        }

    }
////
////    /**
////     * Update a student program based on their id
////     *
////     * @since 20210924
////     * @author BJM
////     */
////    public void updateProgramById(int newId, String newProgram) {
////        try {
////            PreparedStatement ps = conn.prepareStatement("UPDATE `student` SET program=? WHERE studentId=?");
////            ps.setString(1, newProgram);
////            ps.setInt(2, newId);
////            ps.executeUpdate();
////        } catch (SQLException ex) {
////            System.out.println("Error updating");
////        }
////
////    }
////
////    /**
////     * Select student name by their id
////     *
////     * @since 20210924
////     * @author BJM
////     */
////    public String selectNameById(int idToLookUp) {
////        //**************************************
////        //Use a procedure
////        //Try a callable statement
////        //**************************************
////        String nameBack = null;
////        System.out.println("");
////        System.out.println("Try to use a callable statement to lookup a student");
////        CallableStatement cstmt = null;
////        try {
////            String SQL = "{call getName (?, ?)}";
////            cstmt = conn.prepareCall(SQL);
////            cstmt.setInt(1, idToLookUp);
////            cstmt.execute();
////            nameBack = cstmt.getString(2);
////
////        } catch (SQLException e) {
////
////            System.out.println("error");
////            e.printStackTrace();
////        } finally {
////            System.out.println("end");
////        }
////        return nameBack;
////
////    }

}
