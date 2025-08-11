package data_access.favorite_list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        final List<String> result = new ArrayList<>();
        final Set<String> favorites = userFavorites.get(username);
        if (favorites != null) {
            result.add(favorites.iterator().next());
        }
        else {
            result.add("");
        }
        return result;
    }

    @Override
    public boolean isFavorite(String username, String restaurantId) {
        final Set<String> favorites = userFavorites.get(username);
        return favorites != null && favorites.contains(restaurantId);
    }
}
