package data_access.favorite_list;

import java.util.List;

public interface FavoritesDataAccessInterface {
    void addToFavorites(String username, String restaurantName);
    void removeFromFavorites(String username, String restaurantName);
    List<String> getFavorites(String username);
    boolean isFavorite(String username, String restaurantId);
}
