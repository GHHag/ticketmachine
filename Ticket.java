import java.io.Serializable;
import java.util.ArrayList;

public class Ticket implements Serializable{
    private final int ticketId;
    private static final double TICKET_BASE_PRICE = 70;
    private double totalPrice;
    private ArrayList<Traveler> travelers;
    private String destinationStation;
    private String departureStation;

    public Ticket(String destination, String departureStation, int ticketId){
        this.ticketId = ticketId;
        this.totalPrice = 0;
        this.destinationStation = destination;
        this.departureStation = departureStation;
        this.travelers = new ArrayList<Traveler>();
    }

    public static double getTicketBasePrice() {
        return TICKET_BASE_PRICE;
    }

    public int getTicketId() {
        return this.ticketId;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    @Override
    public String toString() {
        String travelersString = this.travelers.toString();
        return "Ticket{" +
                "ticketId=" + this.ticketId +
                ", totalPrice=" + this.totalPrice +
                ", travelers=" + travelersString +
                ", destinationStation='" + this.destinationStation + '\'' +
                ", departureStation='" + this.departureStation + '\'' +
                '}';
    }

    public void addTraveler(Traveler.TravelerType travelerType){
        Traveler traveler = new Traveler(travelerType);
        this.travelers.add(traveler);
        this.totalPrice += (TICKET_BASE_PRICE - TICKET_BASE_PRICE * traveler.getDiscountRate());
    }

    public String getDepartureStation() {
        return this.departureStation;
    }

    public String getDestinationStation() {
        return this.destinationStation;
    }
}
