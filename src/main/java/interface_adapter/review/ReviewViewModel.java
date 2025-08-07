package interface_adapter.review;

import interface_adapter.ViewModel;

public class ReviewViewModel extends ViewModel<ReviewState> {
    public ReviewViewModel() {
        super("add review");
        setState(new ReviewState());
    }
}
