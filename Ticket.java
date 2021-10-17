import java.io.Serializable;
import java.util.ArrayList;

public class Ticket implements Serializable{
    private final int ticketId;
    private static final double TICKET_BASE_PRICE = 50;
    private double price;
    private ArrayList<Traveler> travelers;
    private String destinationStation;
    private String departureStation;
    private String departureTime;

    public Ticket(String destination, String departureStation, int ticketId){
        this.ticketId = ticketId;
        this.price = 0;
        this.destinationStation = destination;
        this.departureStation = departureStation;
        this.travelers = new ArrayList<Traveler>();
    }

    public int getTicketId() {
        return this.ticketId;
    }

    public double getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        String travelersString = this.travelers.toString();
        return "Ticket{" +
                "ticketId=" + this.ticketId +
                ", price=" + this.price +
                ", travelers=" + travelersString +
                ", destinationStation='" + this.destinationStation + '\'' +
                ", departureStation='" + this.departureStation + '\'' +
                '}';
    }

    public void addTraveler(Traveler.TravelerType travelerType){
        Traveler traveler = new Traveler(travelerType);
        this.travelers.add(traveler);
        this.price += (TICKET_BASE_PRICE - TICKET_BASE_PRICE * traveler.getDiscountRate());
    }

    public void calculatePrice(int numOfStations){
        for(Traveler traveler : this.travelers){
            this.price += (numOfStations * 10 - numOfStations * 10 * traveler.getDiscountRate());
        }
    }

    public String getDepartureStation() {
        return this.departureStation;
    }

    public String getDestinationStation() {
        return this.destinationStation;
    }
}
