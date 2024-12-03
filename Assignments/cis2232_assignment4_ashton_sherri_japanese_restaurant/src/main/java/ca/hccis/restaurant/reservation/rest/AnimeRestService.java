package ca.hccis.restaurant.reservation.rest;

import ca.hccis.restaurant.reservation.jpa.entity.Anime;
import ca.hccis.restaurant.reservation.jpa.entity.JikanResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Service class for fetching anime data from Jikan API.
 *
 * This class provides methods to fetch data from the Jikan API and pass it to the controller.
 *
 * @author Sherri Ashton
 * @since 2024-12-01
 */
@Service
public class AnimeRestService {

    private final String JIKAN_API_URL = "https://api.jikan.moe/v4/anime";

    /**
     * Method to get the list of anime from the Jikan API.
     *
     * @return list of anime.
     */
    public List<Anime> getAllAnime() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            JikanResponse jikanResponse = restTemplate.getForObject(JIKAN_API_URL, JikanResponse.class);

            if (jikanResponse != null && jikanResponse.getData() != null) {
                return jikanResponse.getData();
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            // Log the error and return an empty list if there is an error
            System.err.println("Error fetching anime data: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Fetches and saves the anime data. For now, it only fetches the data.
     * In a real scenario, you might save this data in a repository.
     */
    public void fetchAndSaveAnime() {
        List<Anime> animeList = getAllAnime();
        // You can save this data to your database if needed using a repository.
    }
}
