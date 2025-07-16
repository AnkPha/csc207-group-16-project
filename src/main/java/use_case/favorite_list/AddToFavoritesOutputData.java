package use_case.favorite_list;

public class AddToFavoritesOutputData {
    private final String username;
    private final String restaurantId;

    public AddToFavoritesOutputData(String username, String restaurantId) {
        this.username = username;
        this.restaurantId = restaurantId;
    }

    public String getUsername() {
        return username;
    }

    public String getRestaurantId() {
        return restaurantId;
    }
}
