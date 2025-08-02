package use_case.favorite_list;

import data_access.favorite_list.FavoritesDataAccessInterface;

public class RemoveFromFavoritesInteractor implements RemoveFromFavoritesInputBoundary {
    private final FavoritesDataAccessInterface dataAccessInterface;

    public RemoveFromFavoritesInteractor(FavoritesDataAccessInterface DataAccessInterface) {
        this.dataAccessInterface = DataAccessInterface;
    }

    @Override
    public void execute(RemoveFromFavoritesInputData removeFromFavoritesInputData) {
        dataAccessInterface.removeFromFavorites(removeFromFavoritesInputData.getUserName(),
                removeFromFavoritesInputData.getRestaurantName());
    }
}
