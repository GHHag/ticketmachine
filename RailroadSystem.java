import java.util.ArrayList;
import java.lang.Math;
import java.time.LocalTime;

public class RailroadSystem {
    private static int ticketId;
    private ArrayList<Station> stations;
    private ArrayList<ArrayList<String>> routes;
    private ArrayList<Ticket> tickets;

    public RailroadSystem(){
        ticketId = 100000;
        this.stations = FileManager.readStationsFileToArrayList();
        this.routes = FileManager.readRoutesFileToArrayList();
    }

    public ArrayList<Station> getStations() {
        return this.stations;
    }

    public int createTicket(String destination, String departureStation){
        Ticket ticket = new Ticket(destination, departureStation, ticketId);
        this.tickets.add(ticket);
        ticketId++;

        return ticket.getTicketId();
    }

    public void checkTicketIdValues(){
        int maxTicketId = 0;
        for(Ticket ticket : this.tickets){
            if (ticket.getTicketId() >= maxTicketId){
                maxTicketId = ticket.getTicketId() + 1;
            }
        }
        ticketId = maxTicketId;
    }

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

    public double getTicketPrice(int ticketIdToPay){
        for(Ticket ticket : this.tickets){
            if(ticket.getTicketId() == ticketIdToPay){
                ticket.calculatePrice(1); // ge antalet stationer för resan som arg

                return ticket.getPrice();
            }
        }

        return 0;
    }

    public boolean rrsAddTraveler(int ticketIdToAdd, Traveler.TravelerType travelerType){
        for(Ticket ticket : this.tickets){
            if(ticket.getTicketId() == ticketIdToAdd){
                ticket.addTraveler(travelerType);

                return true;
            }
        }

        return false;
    }

    public boolean verifyPayment(int ticketIdToVerify, double amountPayed){
        for(Ticket ticket: this.tickets){
            if(ticket.getTicketId() == ticketIdToVerify){
                if(amountPayed >= ticket.getPrice()){
                    System.out.println(
                            "Payment verified.\n" +
                            "Change back: " + (amountPayed - ticket.getPrice())
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

    public ArrayList<String> getDepartures(String destinationStation, String departureStation){
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

        return null;
    }

    public void getAvailableRoutes(int ticketId){
        ArrayList<String> shortestRoute = new ArrayList<String>();
        for(Ticket ticket : this.tickets){
            if(ticket.getTicketId() == ticketId){
                boolean routeFound = false;
                System.out.println(
                        "\nAvailable routes (" + ticket.getDepartureStation() + " - " +
                                                 ticket.getDestinationStation() + "): "
                );
                for (ArrayList<String> route : this.routes){
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

    public void getAvailableTransitions(String departureStation, String destinationStation){
        for(ArrayList<String> route : this.routes){
            if(route.contains(departureStation)){
                System.out.println("Suggested trip: ");
                System.out.println(route);
                for(ArrayList<String> destinationRoute : this.routes){
                    if(destinationRoute.contains(destinationStation)){
                        System.out.println("Suggested line for changing train: ");
                        System.out.println(destinationRoute);
                        System.out.println("Available stations for transitioning to a train towards " + destinationStation + ":");
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

    public boolean saveTickets(){
        return FileManager.serializeTickets(this.tickets);
    }

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
