package use_case.favorite_list;

public class RemoveFromFavoritesInputData {
    private final String userName;
    private final String restaurantName;

    public RemoveFromFavoritesInputData(String userName, String restaurantName) {
        this.userName = userName;
        this.restaurantName = restaurantName;
    }

    public String getUserName() {
        return userName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }
}
