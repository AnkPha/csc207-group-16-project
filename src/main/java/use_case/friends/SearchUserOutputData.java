package use_case.friends;

import java.util.Collection;
import java.util.List;

public class SearchUserOutputData {
    private final List<String> matchedUsernames;

    public SearchUserOutputData(List<String> matchedUsernames) {
        this.matchedUsernames = matchedUsernames;
    }

    public List<String> results() {
        return matchedUsernames;
    }
}
