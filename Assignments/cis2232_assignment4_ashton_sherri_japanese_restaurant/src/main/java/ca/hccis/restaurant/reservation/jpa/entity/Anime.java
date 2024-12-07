package ca.hccis.restaurant.reservation.jpa.entity;
/**
 * Entity class representing an Anime object.
 *
 * This class contains basic information about an anime, including its title and score.
 * It is used to store and represent the data obtained from the Jikan API.
 *
 * @author Sherri Ashton
 * @since 2024-12-01
 */
public class Anime {
    private String title;
    private double score;

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
