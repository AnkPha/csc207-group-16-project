package use_case.favorite_list;

public class AddToFavoritesInputData {
    private final String username;
    private final String restaurantId;

    public AddToFavoritesInputData(String username, String restaurantId) {
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
