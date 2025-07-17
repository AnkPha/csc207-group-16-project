package use_case.filter;

import use_case.search_nearby_locations.SearchLocationsNearbyInputData;

/**
 * The Filter Use Case.
 */
public interface FilterInputBoundary {

    /**
     * Execute the Filter Use Case.
     * @param filterInputData the input data for this use case
     * @param searchLocationsNearbyInputData the input data for the address and locations for this use case
     */
    void execute(SearchLocationsNearbyInputData searchLocationsNearbyInputData, FilterInputData filterInputData);

}
