package interface_adapter.favorites_list;

import java.util.ArrayList;
import java.util.List;

public class FavoritesState {

    private String searchQuery = "";
    private List<String> favoriteList = new ArrayList<>();
    private String errorMsg = "";

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public List<String> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(List<String> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public String getErrorMessage() {
        return errorMsg;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMsg = errorMessage;
    }
}
