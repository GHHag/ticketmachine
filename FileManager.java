import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;

public class FileManager {

    public static ArrayList<Station> readStationsFileToArrayList(){
        ArrayList<Station> stations = new ArrayList<Station>();
        try(InputStream is = Files.newInputStream(Paths.get("src/stationer.txt"),
             StandardOpenOption.READ)){
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader lineReader = new BufferedReader(reader);
            String line;
            while((line = lineReader.readLine()) != null){
                stations.add(new Station(line));
            }

            return stations;
        }
        catch(java.io.IOException ioe){
            System.out.println(ioe.getMessage());

            return null;
        }
    }

    public static ArrayList<ArrayList<String>> readRoutesFileToArrayList(){
        ArrayList<ArrayList<String>> routes = new ArrayList<ArrayList<String>>();
        try(InputStream is = Files.newInputStream(Paths.get("src/avgang.txt"),
                StandardOpenOption.READ)){
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader lineReader = new BufferedReader(reader);

            String line;
            while((line = lineReader.readLine()) != null){
                String[] splitLine = line.split(" - ");
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

    public static boolean serializeTickets(ArrayList<Ticket> tickets){
        try{
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

    public static ArrayList<Ticket> deserializeTickets(){
        try{
            FileInputStream fileIn = new FileInputStream("src/serialized_tickets.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
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
