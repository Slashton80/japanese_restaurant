package ca.hccis.restaurant.reservation.settings;

/**
 * Interface to hold file location information
 *
 * @author sherri ashton
 * @since 2024-10-02
 */
public interface Config {
    String LAST_NAME = "Ashton";
    String FIRST_NAME = "Sherri";
    String FILE_NAME_CUSTOM_JSON = "data_" + LAST_NAME.toLowerCase() + "_" + FIRST_NAME.toLowerCase() + ".json";
    String FOLDER_NAME = "C:\\CIS2232\\";
}

