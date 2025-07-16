package data_access.favorite_list;

import java.util.*;

public class FavoritesDataAccessObject implements FavoritesDataAccessInterface {
    private Map<String, Set<String>> userFavorites = new HashMap<>();

    @Override
    public void addToFavorites(String username, String restaurantName) {
        userFavorites.computeIfAbsent(username, name -> new HashSet<>()).add(restaurantName);
    }

    @Override
    public void removeFromFavorites(String username, String restaurantName) {
        final Set<String> favorites = userFavorites.get(username);
        if (favorites != null) {
            favorites.remove(restaurantName);
        }
    }

    @Override
    public List<String> getFavorites(String username) {
        return new ArrayList<>(userFavorites.get(username));
    }

    @Override
    public boolean isFavorite(String username, String restaurantName) {
        return userFavorites.get(username).contains(restaurantName);
    }
}
