//package ca.hccis.restaurant.reservation.rest;
//
//import com.google.gson.Gson;
//import ca.hccis.restaurant.reservation.exception.AllAttributesNeededException;
//import ca.hccis.restaurant.reservation.jpa.entity.TicketOrder;
//import ca.hccis.restaurant.reservation.repositories.TicketOrderRepository;
//import ca.hccis.restaurant.reservation.util.CisUtility;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.net.HttpURLConnection;
//import java.net.URISyntaxException;
//import java.util.ArrayList;
//import java.util.Optional;
//
///**
// * Ticket Order Service class for accessing using REST.
// *
// * @author 2250
// * @since 20220201
// */
//@Path("/TicketOrderService/ticketOrders")
//public class TicketOrderService
//{
//    private final TicketOrderRepository tor;
//
//    @Autowired
//    public TicketOrderService(TicketOrderRepository tor){
//        this.tor = tor;
//    }
//
//    /**
//     * Method to get all.
//     *
//     * @author 2250
//     * @since 20201116
//     * @return customers
//     */
//    @GET
//    @Produces("application/json")
//    public ArrayList<TicketOrder> getAll()
//    {
//        ArrayList<TicketOrder> orders = (ArrayList<TicketOrder>) tor.findAll();
//        return orders;
//    }
//
//    /**
//     * Method to get by their id using REST.
//     *
//     * @author 2250
//     * @since 20220201
//     * @return response
//     */
//    @GET
//    @Path("/{id}")
//    @Produces("application/json")
//    public Response getTicketOrderById(@PathParam("id") Integer id) throws URISyntaxException
//    {
//        Optional<TicketOrder> ticketOrder = tor.findById(id);
//         if (!ticketOrder.isPresent()) {
//            return Response.status(204).build();
//        } else {
//            return Response
//                    .status(200)
//                    .entity(ticketOrder).build();
//        }
//    }
//
//    /**
//     * Method to create using REST.
//     *
//     * @author 2250
//     * @since 20201116
//     * @return response
//     */
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response create(String ticketOrderJson)
//    {
//        try{
//            String temp = save(ticketOrderJson);
//            return Response.status(HttpURLConnection.HTTP_OK).entity(temp).header("Access-Control-Allow-Origin", "*")
//                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//        }catch(AllAttributesNeededException aane){
//            System.out.println("AANE Exception happened adding ticket order.");
//            //https://developer.mozilla.org/en-US/docs/Web/HTTP/Status#successful_responses
//            return Response.status(HttpURLConnection.HTTP_NOT_ACCEPTABLE).entity(aane.getMessage()).build();
//        }catch(Exception e){
//            System.out.println("Exception happened adding ticket order.");
//            //https://developer.mozilla.org/en-US/docs/Web/HTTP/Status#successful_responses
//
//            return Response.status(HttpURLConnection.HTTP_NOT_ACCEPTABLE).entity(e.getMessage()).build();
//        }
//    }
////
////    /**
////     * Method to update a customer using REST.
////     *
////     * @author PAAG
////     * @since 20201116
////     * @return response
////     */
//    @PUT
//    @Path("/{id}")
//    @Consumes("application/json")
//    @Produces("application/json")
//    public Response updateTicketOrder(@PathParam("id") int id, String ticketOrderJson) throws URISyntaxException
//    {
//
//        try{
//            String temp = save(ticketOrderJson);
//            return Response.status(201).entity(temp).header("Access-Control-Allow-Origin", "*")
//                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
//        }catch(AllAttributesNeededException aane){
//            return Response.status(400).entity(aane.getMessage()).build();
//        }
//
//    }
//
//    /**
//     * Method to make sure all required inputs are present.
//     *
//     * @author 2250
//     * @since 20220201
//     * @return string
//     */
//    public String save(String json) throws AllAttributesNeededException{
//
//        Gson gson = new Gson();
//        TicketOrder ticketOrder = gson.fromJson(json, TicketOrder.class);
//        ticketOrder.setDateOfOrder(CisUtility.getCurrentDate(-30, "yyyy-MM-dd"));
//        if(ticketOrder.getId() == null){
//            ticketOrder.setId(0);
//        }
//
//        ticketOrder = tor.save(ticketOrder);
//
//        String temp = "";
//        temp = gson.toJson(ticketOrder);
//
//        return temp;
//
//
//    }
//
//}
