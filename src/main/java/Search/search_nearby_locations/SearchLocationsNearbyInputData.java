package Search.search_nearby_locations;

/**
 * A class that represents the input data of the search request.
 */
public class SearchLocationsNearbyInputData {
    private final String address;
    private final int radius;

    public SearchLocationsNearbyInputData(String address, int radius) {
        this.address = address;
        this.radius = radius;
    }

    public String getAddress() {
        return this.address;
    }

    public int getRadius() {
        return this.radius;
    }
}
