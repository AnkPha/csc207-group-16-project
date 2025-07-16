package data_access.favorite_list;

import java.util.List;

public interface FavoritesDataAccessInterface {
    void addToFavorites(String username, String restaurantId);
    void removeFromFavorites(String username, String restaurantId);
    List<String> getFavorites(String username);
    boolean isFavorite(String username, String restaurantId);
}
