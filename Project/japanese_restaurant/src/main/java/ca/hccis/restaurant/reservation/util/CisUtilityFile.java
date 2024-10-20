package ca.hccis.restaurant.reservation.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CisUtilityFile {

    public static final String FOLDER_NAME = "C:\\CIS2232\\";

    public static void writeReportToFile(String fileName, ArrayList theObjects) {

        String fullName = FOLDER_NAME + fileName + CisUtility.getCurrentDate("_yyyyMMddhhmmss") + ".txt";
        Path path = Paths.get(FOLDER_NAME);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            System.out.println("BJM-CisUtilityFile-Error creating directories");
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fullName, true));
            System.out.println("Writing to file: " + fullName);  // Debug message

            for (Object current : theObjects) {
                writer.write(current.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("CisUtilityFile exception with file: " + fullName);
        } finally {
            try {
                if (writer != null) writer.close();
                System.out.println("File writing complete: " + fullName);  // Debug message
            } catch (IOException e) {
                System.out.println("CisUtilityFile exception with file: " + fullName);
            }
        }

    }
}