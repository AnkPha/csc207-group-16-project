package interface_adapter.search_user;

import java.util.List;
import java.util.Map;

import entity.Review;
import Search.ViewModel;

public class SearchUserViewModel extends ViewModel<SearchUserState> {
    public SearchUserViewModel() {
        super("search_user");
        setState(new SearchUserState());
    }

    /**
     * Updates the view model state with the provided list of usernames and their corresponding reviews,
     * and notifies observers that the state has changed.
     *
     * @param users   A list of usernames that were returned from a search query.
     * @param reviews A map where each key is a username and the corresponding value is a list of
     *                that user's reviews. Users with no reviews may have an empty list or be absent.
     */
    public void setResults(List<String> users, Map<String, List<Review>> reviews) {
        getState().setUsernames(users);
        getState().setUserReviews(reviews);
        firePropertyChanged();
    }
}
