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
    public void execute(RemoveFromFavoritesInputData removeFromFavoritesInputData) {
        try {
            if (!dataAccessInterface.isFavorite(removeFromFavoritesInputData.getUserName(),
                    removeFromFavoritesInputData.getRestaurantName())) {
                RemoveFromFavoritesOutputData outputData = new RemoveFromFavoritesOutputData(
                        removeFromFavoritesInputData.getUserName(),
                        removeFromFavoritesInputData.getRestaurantName()
                );
                presenter.prepareErrorView(outputData);
                return;
            }

            dataAccessInterface.removeFromFavorites(removeFromFavoritesInputData.getUserName(),
                    removeFromFavoritesInputData.getRestaurantName());

            RemoveFromFavoritesOutputData outputData = new RemoveFromFavoritesOutputData(
                    removeFromFavoritesInputData.getUserName(),
                    removeFromFavoritesInputData.getRestaurantName()
            );
            presenter.prepareSuccessView(outputData);

        }
        catch (Exception e) {
            RemoveFromFavoritesOutputData outputData = new RemoveFromFavoritesOutputData(
                    removeFromFavoritesInputData.getUserName(),
                    removeFromFavoritesInputData.getRestaurantName()
            );
            presenter.prepareErrorView(outputData);
        }
    }
}
