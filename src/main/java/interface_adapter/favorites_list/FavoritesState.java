package interface_adapter.favorites_list;

import java.util.ArrayList;
import java.util.List;

public class FavoritesState {
    private List<String> favoriteList = new ArrayList<>();
    private String errorMessage = "";

    public List<String> getFavoriteList() {
        return new ArrayList<>(favoriteList);
    }

    public void setFavoriteList(List<String> favoriteList) {
        this.favoriteList = new ArrayList<>(favoriteList);
    }

    public void addFavorite(String restaurantId) {
        if (!favoriteList.contains(restaurantId)) {
            favoriteList.add(restaurantId);
        }
    }

    public void removeFavorite(String restaurantId) {
        favoriteList.remove(restaurantId);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
