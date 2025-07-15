package use_case.filter;

/**
 * The input data for the Filter Use Case.
 */
public class FilterInputData {

    private final String cuisine;
    private final Map <String> allergens;
    private final String hours;
    private final Map <String> ratings;
    private final HashMap <String, Integer> nutrition;

    public FilterInputData(String cuis, Map <String> alle,
                           String hours, Map <String> rate, Hashmap <String, Integer> nutr) {
        this.cuisine = cuis;
        this.allergens = alle;
        this.hours_of_ops = hours;
        this.ratings = rate;
        this.nutrition = nutr;
    }

    String getCuisine() {
        return cuisine;
    }

    Map <String> getAllergens() {return allergens;}

    String getHoursOfOps() {return hours_of_ops;}

    Map <String> getRatings() {return ratings;}

    HashMap <String, Integer> getNutrition() {return nutrition;}
}
