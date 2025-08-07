package data_access.favorite_list;

import java.util.*;

public class FavoritesDataAccessObject implements FavoritesDataAccessInterface {
    private Map<String, Set<String>> userFavorites = new HashMap<>();

    @Override
    public void addToFavorites(String username, String restaurantId) {
        userFavorites.computeIfAbsent(username, name -> new HashSet<>()).add(restaurantId);
    }

    @Override
    public void removeFromFavorites(String username, String restaurantId) {
        final Set<String> favorites = userFavorites.get(username);
        if (favorites != null) {
            favorites.remove(restaurantId);
        }
    }

    @Override
    public List<String> getFavorites(String username) {
        Set<String> favorites = userFavorites.get(username);
        return favorites != null ? new ArrayList<>(favorites) : new ArrayList<>();
    }

    @Override
    public boolean isFavorite(String username, String restaurantId) {
        Set<String> favorites = userFavorites.get(username);
        return favorites != null && favorites.contains(restaurantId);
    }
}
