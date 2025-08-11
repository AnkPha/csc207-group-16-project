package data_access;

public class DataAccessObjectManager {
    private static InMemoryReviewDataAccessObject reviewDAOInstance;
    private static FilterDataAccessObject filterDAOInstance;

    public static InMemoryReviewDataAccessObject getReviewDAO() {
        if (reviewDAOInstance == null) {
            reviewDAOInstance = new InMemoryReviewDataAccessObject();
        }
        return reviewDAOInstance;
    }

    public static FilterDataAccessObject getFilterDAO() {
        if (filterDAOInstance == null) {
            // Use the same review DAO instance!
            filterDAOInstance = new FilterDataAccessObject(getReviewDAO());
        }
        return filterDAOInstance;
    }
}
