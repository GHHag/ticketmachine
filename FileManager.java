import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;

public class FileManager {
    /**
     * A class containing functionality to manage files required to run the program.
     *
     * Author: Gustav Hagenblad
     * 2021-10
     */

    /**
     * Reads a file with a list of stations and returns it as an ArrayList<String>.
     * If an IOException is raised while trying to read the file, null will be returned.
     * @return ArrayList<String>/null
     */
    public static ArrayList<String> readStationsFileToArrayList(){
        ArrayList<String> stations = new ArrayList<String>();
        // Tries to read a file at the hard coded path
        try(InputStream is = Files.newInputStream(Paths.get("src/stationer.txt"),
             StandardOpenOption.READ)){
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader lineReader = new BufferedReader(reader);
            String line;
            while((line = lineReader.readLine()) != null){
                stations.add(line);
            }

            return stations;
        }
        catch(java.io.IOException ioe){
            System.out.println(ioe.getMessage());

            return null;
        }
    }

    /**
     * Reads a file with a list of routes and returns it as an ArrayList<ArrayList<String>>.
     * If an IOException is raised while trying to read the file, null will be returned.
     * @return ArrayList<ArrayList<String>>
     */
    public static ArrayList<ArrayList<String>> readRoutesFileToArrayList(){
        ArrayList<ArrayList<String>> routes = new ArrayList<ArrayList<String>>();
        // Tries to read a file at the hard coded path
        try(InputStream is = Files.newInputStream(Paths.get("src/avgang.txt"),
                StandardOpenOption.READ)){
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader lineReader = new BufferedReader(reader);

            // Reads lines of the file in a while loop
            String line;
            while((line = lineReader.readLine()) != null){
                // Splits the lines on " - "
                String[] splitLine = line.split(" - ");
                // Creates an ArrayList of the elements that was split up and
                // adds that ArrayList to the list with routes.
                ArrayList<String> lineParts = new ArrayList<String>();
                Collections.addAll(lineParts, splitLine);
                routes.add(lineParts);
            }

            return routes;
        }
        catch(java.io.IOException ioe){
            System.out.println(ioe.getMessage());

            return null;
        }
    }

    /**
     * Serializes an ArrayList containing Ticket objects.
     * @param tickets, ArrayList<Ticket>, a list with Ticket objects.
     * @return boolean, true/false depending on if the serialization was successful
     * or not.
     */
    public static boolean serializeTickets(ArrayList<Ticket> tickets){
        try{
            // Hard coded path to where the list will be saved
            Path path = Paths.get("src/serialized_tickets.ser");
            ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path));
            out.writeObject(tickets);
            out.close();

            return true;
        }
        catch(IOException ioe){
            ioe.printStackTrace();

            return false;
        }
    }

    /**
     * Deserializes a binary file where an ArrayList containing Ticket objects has been saved.
     * If any of the exceptions FileNotFoundException, IOException, ClassNotFoundException are
     * cought while trying to deserialize the file, null will be returned.
     * @return ArrayList<Ticket>
     */
    public static ArrayList<Ticket> deserializeTickets(){
        try{
            FileInputStream fileIn = new FileInputStream("src/serialized_tickets.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            // Creates an ArrayList<Ticket> by type casting the object that was read from
            // the file.
            ArrayList<Ticket> tickets = (ArrayList<Ticket>) in.readObject();
            in.close();
            fileIn.close();

            return tickets;
        }
        catch(FileNotFoundException fne){
            return null;
        }
        catch(IOException ioe) {
            ioe.printStackTrace();

            return null;
        }
        catch(ClassNotFoundException cnfe) {
            System.out.println("Ticket class not found");
            cnfe.printStackTrace();

            return null;
        }
    }
}
