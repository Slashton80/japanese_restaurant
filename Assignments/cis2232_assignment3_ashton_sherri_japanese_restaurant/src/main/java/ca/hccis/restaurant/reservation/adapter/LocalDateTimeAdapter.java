package ca.hccis.restaurant.reservation.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * This class provides custom serialization and deserialization for LocalDateTime using Gson.
 * sources:
 * https://www.geeksforgeeks.org/deserialize-java-8-localdatetime-with-jacksonmapper/
 * https://howtodoinjava.com/gson/custom-serialization-deserialization/
 *
 * @author sherri ashton
 * @since 2024-09-20
 */
public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter formatterWithT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private static final DateTimeFormatter formatterWithoutT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Getter method for formatterWithoutT
    public static DateTimeFormatter getFormatterWithoutT() {
        return formatterWithoutT;
    }

    @Override
    public JsonElement serialize(LocalDateTime dateTime, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(dateTime.format(formatterWithT));
    }

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String dateString = json.getAsString();
        try {
            return LocalDateTime.parse(dateString, formatterWithT);
        } catch (DateTimeParseException e) {
            return LocalDateTime.parse(dateString, formatterWithoutT);
        }
    }
}
