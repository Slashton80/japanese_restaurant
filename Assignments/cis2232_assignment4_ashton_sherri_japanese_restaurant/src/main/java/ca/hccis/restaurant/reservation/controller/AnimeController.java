package ca.hccis.restaurant.reservation.controller;

import ca.hccis.restaurant.reservation.jpa.entity.Anime;
import ca.hccis.restaurant.reservation.rest.AnimeRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller class for handling anime requests.
 *
 * Provides endpoints to fetch and display anime data from Jikan API.
 * Displays the fetched anime data in the Thymeleaf view.
 *
 * @author Sherri
 * @since 2024-12-01
 */
@Controller
@RequestMapping("/anime")
public class AnimeController {

    @Autowired
    private AnimeRestService animeRestService;

    /**
     * Displays the list of all anime available.
     *
     * @param model The {@link Model} object to pass data to the view.
     * @return The view name for displaying anime list.
     */
    @GetMapping("/list")
    public String listAnime(Model model) {
        // Fetches anime data from the service
        List<Anime> animeList = animeRestService.getAllAnime();

        // Adds the list to the model
        model.addAttribute("animeList", animeList);

        // Returns the view
        return "anime/anime";
    }

    /**
     * Fetches the latest anime data from the API and saves it.
     *
     * @return Redirects to the anime list page after fetching data.
     */
    @GetMapping("/fetch")
    public String fetchAnimeData() {
        animeRestService.fetchAndSaveAnime();
        return "redirect:/anime/list";
    }
}
