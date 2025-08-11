package use_case.friends;

public class SearchUserInputData {
    private final String query;

    public SearchUserInputData(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
