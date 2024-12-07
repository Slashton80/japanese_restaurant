//package ca.hccis.restaurant.reservation.repositories;
//
//import ca.hccis.restaurant.reservation.jpa.entity.TicketOrder;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface TicketOrderRepository extends CrudRepository<TicketOrder, Integer> {
//
//    /**
//     * Use Spring Data JPA functionality to find a list of customers containing the
//     * string passed in as a paramter.
//     *
//     * @param name The name to find
//     * @return The list of customers
//     * @since 20220624
//     * @author BJM
//     */
//    //https://www.baeldung.com/spring-jpa-like-queries
//    List<TicketOrder> findByCustomerNameContaining(String name);
//    List<TicketOrder> findByCustomerName(String name);
//    List<TicketOrder> findByDateOfOrder(String dateOfOrder);
//}