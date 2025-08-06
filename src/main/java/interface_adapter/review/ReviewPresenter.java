package interface_adapter.review;

import use_case.review.AddReviewOutputBoundary;
import use_case.review.AddReviewOutputData;

public class ReviewPresenter implements AddReviewOutputBoundary {

    private ReviewViewModel ReviewViewModel;

    public void addReviewPresenter(ReviewViewModel addReviewViewModel) {
        this.ReviewViewModel = addReviewViewModel;
    }

    @Override
    public void prepareSuccessView(AddReviewOutputData outputData) {
        final ReviewState state = ReviewViewModel.getState();
        state.setSuccess(true);
        state.setSuccessMessage("Review added successfully!");
        state.setErrorMessage(null);
        ReviewViewModel.setState(state);
        ReviewViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final ReviewState state = ReviewViewModel.getState();
        state.setSuccess(false);
        state.setErrorMessage(errorMessage);
        state.setSuccessMessage(null);
        ReviewViewModel.setState(state);
        ReviewViewModel.firePropertyChanged();
    }
}

