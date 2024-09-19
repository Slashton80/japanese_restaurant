package ca.hccis.bus.pass;

import ca.hccis.bus.pass.entity.BusPass;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static final String FOLDER_NAME = "c:\\CIS2232\\";
    public static final String FILE_NAME = "buspasses.txt";
    public static final String FILE_NAME_CSV = "buspasses.csv";
    public static final String FILE_NAME_JSON = "buspasses.json";

    public static void main(String[] args) {

        Gson gson = new Gson();

        System.out.printf("Bus Pass Application\n\n");

        //https://www.javaguides.net/2019/07/java-read-file-with-filesreadalllines-api.html#google_vignette
        Path filePath = Paths.get(FOLDER_NAME+FILE_NAME_JSON);
        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                BusPass current = gson.fromJson(line, BusPass.class);
                System.out.println(current.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        //Name
        //Address
        //Length of bus pass

        BusPass busPass = new BusPass();
        busPass.getInformation();

//        Gson gson = new Gson();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FOLDER_NAME+FILE_NAME, true))){
            writer.write(busPass.toString());
            writer.newLine();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FOLDER_NAME+FILE_NAME_CSV, true))){
            writer.write(busPass.toCSV());
            writer.newLine();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FOLDER_NAME+FILE_NAME_JSON, true))){
            writer.write(gson.toJson(busPass));
            writer.newLine();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }



    }
}