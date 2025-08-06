package use_case.friends;

import entity.Review;

import java.util.List;
import java.util.Map;

public class SearchUserOutputData {
    private final List<String> usernames;
    private final Map<String, List<Review>> userReviews;

    public SearchUserOutputData(List<String> usernames, Map<String, List<Review>> userReviews) {
        this.usernames = usernames;
        this.userReviews = userReviews;
    }

    public List<String> results() {
        return usernames;
    }

    public Map<String, List<Review>> getUserReviews() {
        return userReviews;
    }
}
