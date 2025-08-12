package interface_adapter.favorites_list;

import Search.ViewModel;

public class FavoritesViewModel extends ViewModel<FavoritesState> {
    public FavoritesViewModel() {
        super("favorites");
        setState(new FavoritesState());
    }
}
