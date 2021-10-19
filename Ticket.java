import java.io.Serializable;
import java.util.ArrayList;

public class Ticket implements Serializable{
    /**
     * A class representing a ticket. Contains fields with the data of a ticket.
     *
     * Author: Gustav Hagenblad
     * 2021-10
     */

    private final int ticketId;
    private static final double TICKET_BASE_PRICE = 70;
    private double totalPrice;
    private ArrayList<Traveler> travelers;
    private String destinationStation;
    private String departureStation;

    /**
     * Constructor
     * @param destinationStation, String, the destination of a travel.
     * @param departureStation, String the departure station of a travel.
     * @param ticketId, int, an integer used to identify the ticket.
     */
    public Ticket(String destinationStation, String departureStation, int ticketId){
        this.ticketId = ticketId;
        this.totalPrice = 0;
        this.destinationStation = destinationStation;
        this.departureStation = departureStation;
        this.travelers = new ArrayList<Traveler>();
    }

    /**
     * Getter for the base price of a ticket.
     * @return double
     */
    public static double getTicketBasePrice() {
        return TICKET_BASE_PRICE;
    }

    /**
     * Getter for the ticket id.
     * @return int
     */
    public int getTicketId() {
        return this.ticketId;
    }

    /**
     * Getter for the total price of the ticket.
     * @return double
     */
    public double getTotalPrice() {
        return this.totalPrice;
    }

    /**
     * Returns a String representation of the ticket.
     * @return String
     */
    @Override
    public String toString() {
        String travelersString = this.travelers.toString();
        return "Ticket{" +
                "Ticket ID=" + this.ticketId +
                ", Total Price=" + this.totalPrice +
                ", Travelers=" + travelersString +
                ", Destination='" + this.destinationStation + '\'' +
                ", Departure='" + this.departureStation + '\'' +
                '}';
    }

    /**
     * Adds a traveler to the tickets list of travelers and adds the cost of that
     * traveler to the tickets total price.
     * @param travelerType, Enum TravelerType from the Traveler class.
     */
    public void addTraveler(Traveler.TravelerType travelerType){
        Traveler traveler = new Traveler(travelerType);
        this.travelers.add(traveler);
        this.totalPrice += (TICKET_BASE_PRICE - TICKET_BASE_PRICE * traveler.getDiscountRate());
    }

    /**
     * Getter for the departure station.
     * @return String
     */
    public String getDepartureStation() {
        return this.departureStation;
    }

    /**
     * Getter for the destination station.
     * @return String
     */
    public String getDestinationStation() {
        return this.destinationStation;
    }
}
