package use_case.review;


public class AddReviewInteractor implements AddReviewInputBoundary {
    private final AddReviewAccessInterface userDataAccessObject;
    private final AddReviewOutputData userPresenter;

    public AddReviewInteractor(AddReviewAccessInterface userDataAccessObject, AddReviewOutputData dataPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = dataPresenter;

    }

    @Override
    public void execute(AddReviewInputData inputData) {
        new Review review
    }
}
