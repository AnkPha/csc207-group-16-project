package interface_adapter.filter;

import java.util.List;

import use_case.filter.FilterInputBoundary;
import use_case.filter.FilterInputData;

/**
 * Controller for the Filter Use Case.
 */
public class FilterController {
    private final FilterInputBoundary userFilterUseCaseInteractor;

    public FilterController(FilterInputBoundary userFilterUseCaseInteractor) {
        this.userFilterUseCaseInteractor = userFilterUseCaseInteractor;
    }

    /**
     * Executes the Filter Use Case.
     * @param address  the address for filtering
     * @param radius the radius for filtering
     * @param selectedAvailability the selected availability for filtering
     * @param selectedCuisines the selected cuisines for filtering
     * @param selectedRating the selected rating for filtering
     * @param selectedVegStat the selected vegetarian status for filtering
     */
    public void execute(String address, int radius, List<String> selectedCuisines, String selectedVegStat,
                        String selectedAvailability, String selectedRating) {
        final FilterInputData userFilterInputData = new FilterInputData(address, radius,
                selectedCuisines, selectedVegStat, selectedAvailability, selectedRating);
        userFilterUseCaseInteractor.execute(userFilterInputData);
    }
}
