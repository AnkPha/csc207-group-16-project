package use_case.search_nearby_locations;

public class SearchLocationsNearbyInputData{
    private final String address;

    public SearchLocationsNearbyInputData(String address){
        this.address = address;
    }

    public String getAddress(){
        return this.address;
    }
}