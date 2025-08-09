package use_case.friends;

import java.util.List;
import java.util.Map;

import entity.Review;

public class SearchUserOutputData {
    private final List<String> usernames;
    private final Map<String, List<Review>> userReviews;

    public SearchUserOutputData(List<String> usernames, Map<String, List<Review>> userReviews) {
        this.usernames = usernames;
        this.userReviews = userReviews;
    }

    /**
     * Retrieves the list of usernames currently stored in this object.
     *
     * @return a list of usernames representing the current results
     */
    public List<String> results() {
        return usernames;
    }

    public Map<String, List<Review>> getUserReviews() {
        return userReviews;
    }
}
