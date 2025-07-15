package use_case.search_nearby_locations;

public interface SearchLocationsNearbyOutputBoundary{
    void prepareSuccessView(SearchLocationsNearbyOutputData outputData);

    void prepareFailView(String errorMessage);

}