package data_access.favorite_list;

import java.util.List;

public interface FavoritesDataAccessInterface {
    /**
     * Adds a restaurant to the user's favorites list.
     * @param username the username
     * @param restaurantId the restaurant identifier
     */
    void addToFavorites(String username, String restaurantId);

    /**
     * Removes a restaurant from the user's favorites list.
     * @param username the username
     * @param restaurantId the restaurant identifier
     */
    void removeFromFavorites(String username, String restaurantId);

    /**
     * Gets the list of favorite restaurants for a user.
     * @param username the username
     * @return list of favorite restaurant identifiers
     */
    List<String> getFavorites(String username);

    /**
     * Checks if a restaurant is in the user's favorites.
     * @param username the username
     * @param restaurantId the restaurant identifier
     * @return true if restaurant is favorited, false otherwise
     */
    boolean isFavorite(String username, String restaurantId);
}

