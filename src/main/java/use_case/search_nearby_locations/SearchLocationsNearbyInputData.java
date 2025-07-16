package use_case.search_nearby_locations;

public class SearchLocationsNearbyInputData{
    private final String address;
    private final int limit;
    private final int radius;

    public SearchLocationsNearbyInputData(String address, int limit, int radius){
        this.address = address;
        this.limit = limit;
        this.radius = radius;
    }

    public String getAddress(){
        return this.address;
    }
    public int getLimit(){ return this.limit; }
    public int getRadius(){ return this.radius; }
}