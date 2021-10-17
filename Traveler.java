import java.io.Serializable;

public class Traveler implements Serializable{
    private TravelerType travelerType;

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
     * Returns a String representation of a Traveler-object.
     * @return String
     */
    @Override
    public String toString() {
        return "Traveler{" +
                "travelerType=" + travelerType +
                '}';
    }

    /**
     * Gets the discount rate of the traveler. A value between 0.0 and 1.0 (100% discount).
     * @return double
     */
    public double getDiscountRate(){
        // 30% discount on ticket prices for children
        if(this.travelerType == TravelerType.CHILD){
            return 0.3;
        }
        // 15% discount on ticket prices for seiniors
        else if(this.travelerType == TravelerType.SENIOR){
            return 0.15;
        }
        // No discount currently for adults
        else{
            return 0.0;
        }
    }
}
