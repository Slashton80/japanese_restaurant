//package ca.hccis.restaurant.reservation.adapter;
//
//import jakarta.xml.bind.annotation.adapters.XmlAdapter;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
/// **
// * JAXB adapter for LocalDateTime to convert it to and from a String during XML serialization/deserialization.
// * Added for the SOAP getReservation method for older Java versions
// * Source: https://docs.oracle.com/javase/tutorial/jaxb/intro/arch.html
// *        https://www.baeldung.com/jaxb
// */
//public class LocalDateTimeJaxbAdapter extends XmlAdapter<String, LocalDateTime> {
//
//  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
//
//  @Override
//  public LocalDateTime unmarshal(String value) {
//    return LocalDateTime.parse(value, formatter);
//  }
//
//  @Override
//  public String marshal(LocalDateTime dateTime) {
//    return dateTime.format(formatter);
//  }
//}
