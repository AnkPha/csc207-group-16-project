class SearchLocationsNearbyInteractor implements SearchLocationsNearbyInputBoundary{
    private final SearchLocationsNearbyDataAccessInterface locationsDataAccessObject;
    private final SearchLocationsNearbyBoundary locationsPresenter;

    public SearchLocationsNearbyInteractor(SearchLocationsNearbyDataAccessInterface locationsDataAccessInterface,
                                           SearchLocationsNearbyOutputBoundary locationsOutputBoundary) {
        this.locationsDataAccessObject = locationsDataAccessInterface;
        this.locationsPresenter = locationsOutputBoundary;
    }

    execute(SearchLocationsNearbyInputData locationInputData){
        //Getting the address from the parameter input
        String address = locationInputData.getAddress();

        //Call the resturant api throuh the locationsDataAccessObject
        List<Restaurant> nearbyRestaurants = locationsDataAccessObject.getNearbyRestaurants(address);

        //Return the locations towards the output class
        SearchLocationsNearbyOutputData output = new SearchLocationsNearbyOutputData(nearbyRestaurants);
    }



}