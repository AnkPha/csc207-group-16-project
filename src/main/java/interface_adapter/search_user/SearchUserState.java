package interface_adapter.search_user;

import entity.Review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchUserState {
    private List<String> usernames = new ArrayList<>();
    private Map<String, List<Review>> userReviews = new HashMap<>();

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }

    public Map<String, List<Review>> getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(Map<String, List<Review>> userReviews) {
        this.userReviews = userReviews;
    }
}
