import java.util.Scanner;

public class Main {
    /**
     * Class containing the main method of the program.
     * Handles interactions with the user with input and output.
     *
     * Author: Gustav Hagenblad
     * 2021-10
     */

    private static Scanner input = new Scanner(System.in);
    private static RailroadSystem rrs;

    /**
     * Main method. The starting point of the program.
     * @param args String array
     */
    public static void main(String[] args) {
        // Creates and instance of the RailroadSystem class.
        rrs = createRailroadSystem();

        // Attempts to load saved tickets. Gives output accordingly whether the tickets
        // was loaded successfully or not.
        boolean loadSuccess = rrs.loadTickets();
        if(!loadSuccess){
            System.out.println("Failed to load tickets. Please contact support.");
        }
        else{
            System.out.println("Tickets was loaded successfully.");
        }

        // Calls method to display a menu, presenting choices to the user
        mainMenu();
    }

    /**
     * Creates and returns an instance of the RailroadSystem class.
     * @return RailroadSystem
     */
    private static RailroadSystem createRailroadSystem(){
        return new RailroadSystem();
    }

    /**
     * Presents a menu and handles input and output via interactions with the user.
     */
    private static void mainMenu(){
        System.out.println(
                "\n" +
                "1. New ticket\n" +
                "2. Display tickets\n" +
                "3. Ticket prices\n" +
                "4. Exit"
        );
        String userChoice = input.next();
        // Switch case block that performs different actions depending on the given input
        switch (userChoice){
            case "1":
                // Calls method to create a Ticket object
                userCreateTicket();
                break;
            case "2":
                // Calls method to print all available Ticket objects
                rrs.printTickets();
                mainMenu();
                break;
            case "3":
                // Calls method to print a list ticket prices
                rrs.printTicketPriceList();
                mainMenu();
                break;
            case "4":
                // Exits the programs
                System.exit(0);
            default:
                // The default case handles any wrong input given
                System.out.println("Wrong input. Please try again.");
                mainMenu();
                break;
        }
    }

    /**
     * Prompts the user to give inputs with information about the trip the user wants to make.
     * Creates a Ticket object using the given information.
     */
    private static void userCreateTicket(){
        // Get and present stations, the user selects destination
        System.out.println("\nChoose your destination: ");
        for (int i = 0; i < rrs.getStations().size(); i++) {
            System.out.println(i + ". " + rrs.getStations().get(i));
        }
        int destination = input.nextInt();

        // The user selects departure station
        System.out.println("\nChoose your departure station: ");
        for (int i = 0; i < rrs.getStations().size(); i++) {
            if(i == destination){
                continue;
            }
            else{
                System.out.println(i + ". " + rrs.getStations().get(i));
            }
        }
        int departureStation = input.nextInt();

        // Create ticket via the RailroadSystem object
        int ticketId = rrs.createTicket(rrs.getStations().get(destination),
                                        rrs.getStations().get(departureStation));

        // Call method to let the user add travelers. Pass ticket ID as argument.
        userAddTravelers(ticketId);

        rrs.getDepartures(rrs.getStations().get(destination),
                          rrs.getStations().get(departureStation));

        // Call method to let the user pay. Pass ticket ID as arument.
        userPay(ticketId, rrs.getTicketTotalPrice(ticketId));
    }

    /**
     * Prompts the user to input how many travelers should be included on the ticket with
     * the given ticket id and which traveler category they fall into. Adds the travelers
     * to the ticket.
     * @param ticketId int, the id of the Ticket object that is currently being processed.
     */
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
            String travelerType = input.next();

            /*
            Switch case that will add a traveler with the traveler type corresponding
            to the given input. The travelers are added via a call to the rrsAddTraveler
            method of the RailroadSystem class which returns true/false depending on if
            the travelers were successfully added or not.
            */
            switch (travelerType){
                case "1":
                    addSuccess = rrs.rrsAddTraveler(ticketId, Traveler.TravelerType.CHILD);
                    break;
                case "2":
                    addSuccess = rrs.rrsAddTraveler(ticketId, Traveler.TravelerType.ADULT);
                    break;
                case "3":
                    addSuccess = rrs.rrsAddTraveler(ticketId, Traveler.TravelerType.SENIOR);
                    break;
                default:
                    break;
            }

            // If the call to the rrsAddTraveler method did not return the boolean value true
            // the following block will be executed and the user will be asked to try again.
            if(!addSuccess){
                System.out.println(
                        "\nWrong input was given while trying to add a traveler to your ticket.\n" +
                        "Please try again."
                );
                userAddTravelers(ticketId);
            }
        }
    }

    /**
     * Prompts the user to pay the given amount for the ticket with the given id.
     * @param ticketId, int, the id of the Ticket object that is currently being processed.
     * @param amount, double, the price of the ticket.
     */
    private static void userPay(int ticketId, double amount){
        System.out.println("\nTotal price for the ticket: " + amount + ":-");
        System.out.println("Please make your payment: ");
        String amountPayedStr = input.next();
        double amountPayed = 0;

        // Tries to parse the input as a double and catches NumberFormatExceptions.
        try{
            amountPayed = Double.parseDouble(amountPayedStr);
        }
        catch (NumberFormatException nfe){
            System.out.println("Your payment was not verified.\nPlease try again.");
            userPay(ticketId, amount);
        }

        // Attempts to verify the payment through a call to the RailroadSystem's
        // verifyPayment method.
        if(!rrs.verifyPayment(ticketId, amountPayed)){
            System.out.println(
                    "Your payment was not verified.\n" +
                    "Please check your balance and try again."
            );
            userPay(ticketId, amount);
        }
        else{
            // Attempts to save tickets
            if(!rrs.saveTickets()){
                System.out.println("Ticket was not saved. Please contact support.");
            }
            else{
                System.out.println("Ticket saved successfully.");
            }
            // Calls method to get available routes for the ticket with the provided id.
            rrs.getAvailableRoutes(ticketId);
            mainMenu();
        }
    }
}
