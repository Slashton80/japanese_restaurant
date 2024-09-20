package ca.hccis.restaurant.reservation.adapter;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonSerializationContext;

import java.lang.reflect.Type;

/** This class provides custom serialization and deserialization for LocalDateTime using Gson.
 * sources:
 * https://www.geeksforgeeks.org/deserialize-java-8-localdatetime-with-jacksonmapper/
 * https://howtodoinjava.com/gson/custom-serialization-deserialization/
 * @author sherri ashton
 * @since 2024-09-20
 *
 */
public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    // Serialize LocalDateTime to a formatted string
    @Override
    public JsonElement serialize(LocalDateTime dateTime, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(dateTime.format(formatter));
    }

    // Deserialize string to LocalDateTime
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(), formatter);
    }
}
