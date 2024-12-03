package ca.hccis.restaurant.reservation.jpa.entity;

import java.util.List;
/**
 * Wrapper class for the response obtained from the Jikan API.
 *
 * This class represents the structure of the response returned by the Jikan API.
 * It contains a list of Anime objects as the main data element. It is used to correctly deserialize
 * the JSON response from the API into Java objects.
 *
 * @author Sherri Ashton
 * @since 2024-12-01
 */
public class JikanResponse {
    private List<Anime> data;

    // Getter and Setter
    public List<Anime> getData() {
        return data;
    }

    public void setData(List<Anime> data) {
        this.data = data;
    }
}
