package use_case.favorite_list;

public class RemoveFromFavoritesOutputData {
    private final String userName;
    private final String restaurantName;

    public RemoveFromFavoritesOutputData(String userName, String restaurantName) {
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
