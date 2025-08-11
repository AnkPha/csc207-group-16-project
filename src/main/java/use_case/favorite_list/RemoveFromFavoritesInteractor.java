package use_case.favorite_list;

import data_access.favorite_list.FavoritesDataAccessInterface;

public class RemoveFromFavoritesInteractor implements RemoveFromFavoritesInputBoundary {
    private final FavoritesDataAccessInterface dataAccessInterface;
    private final RemoveFromFavoritesOutputBoundary presenter;

    public RemoveFromFavoritesInteractor(FavoritesDataAccessInterface dataAccessInterface,
                                         RemoveFromFavoritesOutputBoundary presenter) {
        this.dataAccessInterface = dataAccessInterface;
        this.presenter = presenter;
    }

    @Override
    public void execute(RemoveFromFavoritesInputData inputData) {
        boolean isFavorite = false;
        boolean errorOccured = false;
        try {
            isFavorite = dataAccessInterface.isFavorite(inputData.getUserName(), inputData.getRestaurantName());
        }
        catch (Exception exception) {
            errorOccured = true;
        }
        if (errorOccured || !isFavorite) {
            presenter.prepareErrorView(new RemoveFromFavoritesOutputData(inputData.getUserName(),
                    inputData.getRestaurantName()));
        }
        else {
            try {
                dataAccessInterface.removeFromFavorites(inputData.getUserName(), inputData.getRestaurantName());
                presenter.prepareSuccessView(new RemoveFromFavoritesOutputData(inputData.getUserName(),
                        inputData.getRestaurantName()));
            }
            catch (Exception exception) {
                presenter.prepareErrorView(new RemoveFromFavoritesOutputData(inputData.getUserName(),
                        inputData.getRestaurantName()));
            }
        }
    }
}
