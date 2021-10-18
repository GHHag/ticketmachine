import java.util.ArrayList;
import java.lang.Math;
import java.time.LocalTime;

public class RailroadSystem {
    /**
     * A class containing logic and functionality for managing a railroad system.
     *
     * Author: Gustav Hagenblad
     * 2021-10
     */

    private static int ticketId;
    private ArrayList<String> stations;
    private ArrayList<ArrayList<String>> routes;
    private ArrayList<Ticket> tickets;

    /**
     * Constructor
     */
    public RailroadSystem(){
        ticketId = 100000;
        // Reads in stations using the FileManager class
        this.stations = FileManager.readStationsFileToArrayList();
        // Reads in routes using the FileManager class
        this.routes = FileManager.readRoutesFileToArrayList();
    }

    /**
     * Getter for the list of stations
     * @return ArrayList<String>
     */
    public ArrayList<String> getStations() {
        return this.stations;
    }

    /**
     * Creaetes a new Ticket object, passing it the given destination and departure
     * stations. Adds the ticket to the list containing Ticket objects, and returns
     * the id of the ticket.
     * @param destinationStation, String, the name of the destination station.
     * @param departureStation, String, the name of the departure station.
     * @return int, the id of the created Ticket.
     */
    public int createTicket(String destinationStation, String departureStation){
        Ticket ticket = new Ticket(destinationStation, departureStation, ticketId);
        this.tickets.add(ticket);
        ticketId++;

        return ticket.getTicketId();
    }

    /**
     * Iterates through the list of tickets. Finds the ticket with the highest value
     * of the id variable and assigns the static ticketId variable to be the highest
     * value found + 1. This method makes sure that no Ticket objects can have the
     * same value of its id variables.
     */
    public void checkTicketIdValues(){
        int maxTicketId = 0;
        for(Ticket ticket : this.tickets){
            if (ticket.getTicketId() >= maxTicketId){
                maxTicketId = ticket.getTicketId() + 1;
            }
        }
        ticketId = maxTicketId;
    }

    /**
     * Prints a list with the base price of a ticket and the discount rate of the different
     * traveler type categories.
     */
    public void printTicketPriceList(){
        System.out.println(
                "\nThe price of a ticket is " + Ticket.getTicketBasePrice() + ":-" +
                "\nDiscount is given to children at a rate of " + (Traveler.getChildDiscount() * 100) + "%" +
                "\nDiscount is given to seniors at a rate of " + (Traveler.getSeniorDiscount() * 100) + "%" +
                "\nDiscount is given to adults at a rate of " + (Traveler.getAdultDiscount() * 100) + "%"
        );
    }

    /**
     * Iterates through the list of tickets and prints them out using the
     * Ticket.toString() method.
     */
    public void printTickets(){
        if(this.tickets.size() == 0){
            System.out.println("No tickets found.");
        }
        else{
            for(Ticket ticket : this.tickets){
                System.out.println(ticket.toString());
            }
        }
    }

    /**
     * Gets the total price of the ticket with the given ticket id.
     * @param ticketIdToPay, int, the id of the ticket to be payed.
     * @return double, the amount of change given back after the payment has been made.
     */
    public double getTicketTotalPrice(int ticketIdToPay){
        for(Ticket ticket : this.tickets){
            if(ticket.getTicketId() == ticketIdToPay){

                return ticket.getTotalPrice();
            }
        }

        return 0;
    }

    /**
     * Attempts to add a traveler of the given traveler type to the ticket with the given ticket id.
     * @param ticketIdToAdd, int, the id of the ticket the traveler should be added to.
     * @param travelerType, TravelerType, the category of the traveler (child, senior, adult).
     * @return boolean, true/false depending on if the traveler was added successfully or not.
     */
    public boolean rrsAddTraveler(int ticketIdToAdd, Traveler.TravelerType travelerType){
        for(Ticket ticket : this.tickets){
            if(ticket.getTicketId() == ticketIdToAdd){
                ticket.addTraveler(travelerType);

                return true;
            }
        }

        return false;
    }

    /**
     * Attempts to verify a payment made by the user.
     * @param ticketIdToVerify, int, the id of the ticket the user is paying for.
     * @param amountPayed, double, the amount of money that the user is paying.
     * @return boolean, true/false depending on if the payment has been verified or not.
     */
    public boolean verifyPayment(int ticketIdToVerify, double amountPayed){
        for(Ticket ticket: this.tickets){
            if(ticket.getTicketId() == ticketIdToVerify){
                if(amountPayed >= ticket.getTotalPrice()){
                    System.out.println(
                            "Payment verified.\n" +
                            "Change back: " + (amountPayed - ticket.getTotalPrice())
                    );

                    return true;
                }
                else{
                    return false;
                }
            }
        }

        return false;
    }

    /**
     * Gets the time departing trains from the given departure station heading to the given
     * destination station.
     * @param destinationStation, String, the name of the destination station.
     * @param departureStation, String, the name of the departure station.
     */
    public void getDepartures (String destinationStation, String departureStation){
        System.out.println("Current time: ");
        System.out.println(java.time.LocalTime.now());

        for(ArrayList<String> route : this.routes){
            if (route.contains(destinationStation) && route.contains(departureStation)){
                int departuresPerHour = 24 / route.size();
                int[] departureMinutes = new int[departuresPerHour];
                for (int i = 0; i < departuresPerHour; i++) {
                    departureMinutes[i] = 60 / departuresPerHour * (i+1);
                }
                System.out.println(route);
                System.out.println(
                        "Coming departures from " + departureStation +
                        " on this route travelling towards " + destinationStation +
                        " departs at: ");
            }
        }
    }

    /**
     * Searches for routes available between the departure and destination stations of
     * the ticket with the given ticket id. Finds the route with the least amount of stops.
     * @param ticketId, int, the id of the ticket to find available routes for.
     */
    public void getAvailableRoutes(int ticketId){
        ArrayList<String> shortestRoute = new ArrayList<String>();
        // Iterates through the list of tickets, attempting to find one
        // with the same id as the one passed to the method.
        for(Ticket ticket : this.tickets){
            if(ticket.getTicketId() == ticketId){
                boolean routeFound = false;
                System.out.println(
                        "\nAvailable routes (" + ticket.getDepartureStation() + " - " +
                                                 ticket.getDestinationStation() + "): "
                );
                // Iterates through the list of routes
                for (ArrayList<String> route : this.routes){
                    /*
                     If the route of the current iteration contains both the departure station
                     and the destination station of the ticket the route will be printed out and
                     the routeFound variable set to true.
                     */
                    if(route.contains(ticket.getDepartureStation()) &&
                       route.contains(ticket.getDestinationStation())){
                        System.out.println(route);
                        routeFound = true;

                        /*
                         Checks if the route is the shortest one found by comparing the absolute value
                         of the index of the destination station minus the index of the departure station
                         of the route in the current iteration with the currently shortest route. If the
                         size of shortestRoute is 0, the shortestRoute will be assigned the value of the
                         route in the current iteration.
                        */
                        if(shortestRoute.size() == 0 ||
                           Math.abs(
                                   route.indexOf(ticket.getDestinationStation()) -
                                   route.indexOf(ticket.getDepartureStation())
                           )
                               <
                           Math.abs(
                                   shortestRoute.indexOf(ticket.getDestinationStation()) -
                                   shortestRoute.indexOf(ticket.getDepartureStation())
                           )
                        ){
                            shortestRoute = route;
                        }
                    }
                }
                // If no direct routes are found the following block will be executed and a method
                // attempting to find available transitions called.
                if(!routeFound){
                    System.out.println("No direct lines found for your trip.");
                    getAvailableTransitions(ticket.getDepartureStation(), ticket.getDestinationStation());
                }
                else{
                    System.out.println("Shortest route: ");
                    System.out.println(shortestRoute);
                }
            }
        }
    }

    /**
     * Searches for available transitions, stations where it is possuble to change train
     * when going from the given departure station to the given destination station.
     * @param departureStation, String, the name of the departure station.
     * @param destinationStation, String, the name of the destination station.
     */
    public void getAvailableTransitions(String departureStation, String destinationStation){
        // Iterates through the list of routes
        for(ArrayList<String> route : this.routes){
            // If the current route contains the given departure station execute the block
            if(route.contains(departureStation)){
                System.out.println("\nSuggested trip: ");
                System.out.println(route);
                // Nested iteration of the list of routes
                for(ArrayList<String> destinationRoute : this.routes){
                    // If the route contains the given destination station execute the block
                    if(destinationRoute.contains(destinationStation)){
                        // Prints out the route that has been suggested for changing train
                        System.out.println("Suggested line for changing train: ");
                        System.out.println(destinationRoute);

                        /*
                         Prints out stations that are available to change train at, the stations
                         are found by iterating through the stations at the nested level loop
                         and checking if they are also in the route at the top level loop.
                         */
                        System.out.println("Available stations for transitioning to a train towards " +
                                           destinationStation + ":");
                        for(String station : destinationRoute){
                            if(route.contains(station)){
                                System.out.println(station);
                            }
                        }

                    }
                }
            }
        }
    }

    /**
     * Calls method of the FileManager class to serialize the list of tickets.
     * @return boolean, true/false depending on if the serialization was
     * successful or not.
     */
    public boolean saveTickets(){
        return FileManager.serializeTickets(this.tickets);
    }

    /**
     * Calls method of the FileManager class to deserialize a list of tickets
     * and assign them to the tickets field which is an ArrayList<Ticket>.
     * If the call doesn't return null, the checkTicketIdValues method will be
     * called to make sure no Ticket objects get the same value for the id variable.
     * If the call does return null the tickets field will be assigned the value of
     * a new ArrayList<Ticket>.
     * @return boolean, true/false depending on if any serialized tickets was read
     * from file or not.
     */
    public boolean loadTickets(){
        this.tickets = FileManager.deserializeTickets();
        if(this.tickets != null){
            checkTicketIdValues();

            return true;
        }
        else{
            this.tickets = new ArrayList<Ticket>();

            return false;
        }
    }
}
