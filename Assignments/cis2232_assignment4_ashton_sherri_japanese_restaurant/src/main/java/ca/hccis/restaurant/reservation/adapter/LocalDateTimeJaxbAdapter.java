//package ca.hccis.restaurant.reservation.adapter;
//
//import jakarta.xml.bind.annotation.adapters.XmlAdapter;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
///**
// * JAXB adapter for LocalDateTime to convert it to and from a String during XML serialization/deserialization.
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
