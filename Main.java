import java.util.Scanner;

public class Main {
    private static Scanner input = new Scanner(System.in);
    private static RailroadSystem rrs;

    public static void main(String[] args) {
        rrs = createRailroadSystem();

        boolean loadSuccess = rrs.loadTickets();
        if(!loadSuccess){
            System.out.println("Failed to load tickets. Please contact support.");
        }
        else{
            System.out.println("Tickets was loaded successfully.");
        }

        mainMenu();
    }

    private static RailroadSystem createRailroadSystem(){
        return new RailroadSystem();
    }

    private static void mainMenu(){
        System.out.println(
                "\n" +
                "1. New ticket\n" +
                "2. Display tickets\n" +
                "3. Ticket prices\n" +
                "4. Exit"
        );
        int userChoice = input.nextInt();
        switch (userChoice){
            case 1:
                userCreateTicket();
                break;
            case 2:
                rrs.printTickets();
                mainMenu();
                break;
            case 3:
                System.out.println("");
                break;
            case 4:
                System.exit(0);
            default:
                System.out.println("Wrong input. Please try again.");
                mainMenu();
                break;
        }
    }

    private static void userCreateTicket(){
        // Get and present stations, the user selects destination
        System.out.println("\nChoose your destination: ");
        for (int i = 0; i < rrs.getStations().size(); i++) {
            System.out.println(i + ". " + rrs.getStations().get(i).getName());
        }
        int destination = input.nextInt();

        // The user selects departure station
        System.out.println("\nChoose your departure station: ");
        for (int i = 0; i < rrs.getStations().size(); i++) {
            if(i == destination){
                continue;
            }
            else{
                System.out.println(i + ". " + rrs.getStations().get(i).getName());
            }
        }
        int departureStation = input.nextInt();

        // Create ticket via the RailroadSystem object
        int ticketId = rrs.createTicket(
                rrs.getStations().get(destination).getName(),
                rrs.getStations().get(departureStation).getName()
        );

        // Call method to let the user add travelers. Pass ticket ID as argument.
        userAddTravelers(ticketId);

        rrs.getDepartures(rrs.getStations().get(destination).getName(),
                          rrs.getStations().get(departureStation).getName());

        // Call method to let the user pay. Pass ticket ID as arument.
        userPay(ticketId, rrs.getTicketPrice(ticketId));
    }

    private static void userAddTravelers(int ticketId){
        System.out.println("\nHow many are travelling?");
        int numOfTravelers = input.nextInt();
        boolean addSuccess = true;

        for (int i = 0; i < numOfTravelers; i++) {
            System.out.println(
                    "Traveler " + (i+1) + ":\n" +
                    "1. Child\n" +
                    "2. Adult\n" +
                    "3. Senior"
            );
            int travelerType = input.nextInt();

            switch (travelerType){
                case 1:
                    addSuccess = rrs.rrsAddTraveler(ticketId, Traveler.TravelerType.CHILD);
                    break;
                case 2:
                    addSuccess = rrs.rrsAddTraveler(ticketId, Traveler.TravelerType.ADULT);
                    break;
                case 3:
                    addSuccess = rrs.rrsAddTraveler(ticketId, Traveler.TravelerType.SENIOR);
                    break;
                default:
                    break;
            }

            if(!addSuccess){
                System.out.println(
                        "\nWrong input was given while trying to add a traveler to your ticket.\n" +
                        "Please try again."
                );
                userAddTravelers(ticketId);
            }
        }
    }

    private static void userPay(int ticketId, double amount){
        System.out.println("\nTotal price for the ticket: " + amount + ":-");
        System.out.println("Please make your payment: ");
        double amountPayed = input.nextDouble();

        if(!rrs.verifyPayment(ticketId, amountPayed)){
            System.out.println(
                    "Your payment was not verified.\n" +
                    "Please check your balance and try again."
            );
            userPay(ticketId, amount);
        }
        else{
            if(!rrs.saveTickets()){
                System.out.println("Ticket was not saved. Please contact support.");
            }
            else{
                System.out.println("Ticket saved successfully.");
            }
            rrs.getAvailableRoutes(ticketId);
            mainMenu();
        }
    }
}
