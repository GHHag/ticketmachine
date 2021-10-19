import java.io.Serializable;

public class Traveler implements Serializable{
    /**
     * A class that manages different types of travelers and the
     * discount rate they get when purchasing a ticket.
     *
     * Author: Gustav Hagenblad
     * 2021-10
     */

    private TravelerType travelerType;
    private static final double CHILD_DISCOUNT = 0.3;
    private static final double SENIOR_DISCOUNT = 0.15;
    private static final double ADULT_DISCOUNT = 0.0;

    /**
     * Enum with the different types of traveler price brackets.
     */
    enum TravelerType{
        CHILD,
        ADULT,
        SENIOR
    }

    /**
     * Constructor
     * @param travelerType, enum TravelerType with CHILD, ADULT or SENIOR as the assigned value.
     */
    public Traveler(TravelerType travelerType){
        this.travelerType = travelerType;
    }

    /**
     * Getter for the static field CHILD_DISCOUNT
     * @return double
     */
    public static double getChildDiscount() {
        return CHILD_DISCOUNT;
    }

    /**
     * Getter for the static field SENIOR_DISCOUNT
     * @return double
     */
    public static double getSeniorDiscount() {
        return SENIOR_DISCOUNT;
    }

    /**
     * Getter for the static field ADULT_DISCOUNT
     * @return double
     */
    public static double getAdultDiscount() {
        return ADULT_DISCOUNT;
    }

    /**
     * Returns a String representation of a Traveler-object.
     * @return String
     */
    @Override
    public String toString() {
        return travelerType.toString();
    }

    /**
     * Gets the discount rate of the traveler. A value between 0.0 and 1.0 (100% discount).
     * @return double
     */
    public double getDiscountRate(){
        if(this.travelerType == TravelerType.CHILD){
            return CHILD_DISCOUNT;
        }
        else if(this.travelerType == TravelerType.SENIOR){
            return SENIOR_DISCOUNT;
        }
        else{
            return ADULT_DISCOUNT;
        }
    }
}
