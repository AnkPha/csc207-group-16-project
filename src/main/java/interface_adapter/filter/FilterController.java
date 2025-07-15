package interface_adapter.filter;

import use_case.filter.FilterInputBoundary;
import use_case.filter.FilterInputData;

/**
 * Controller for the Filter Use Case.
 */
public class FilterController {
    private FilterInputBoundary userFilterUseCaseInteractor;

    public FilterController(FilterInputBoundary userFilterUseCaseInteractor) {
        this.userFilterUseCaseInteractor = userFilterUseCaseInteractor;
    }

    /**
     * Executes the Filter Use Case.
     * @param cuis the cuisine option to filter.
     * @param alle the allergen option to filter.
     * @param hours the hours of operation option to filter.
     * @param rate the rate option to filter.
     * @param nutr the nutrition option to filter.
     */
    public void execute(String cuis,Map <String> alle,
                        String hours, Map <String> rate, Hashmap <String, Integer> nutr) {
        final FilterInputData userFilterInputData = new FilterInputData(cuis, hours, avai, rate, nutr);

        userFilterUseCaseInteractor.execute(userFilterInputData);
    }
}
