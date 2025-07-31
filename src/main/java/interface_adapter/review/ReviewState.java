package interface_adapter.review;

import entity.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewState {
    private String searchQuery = "";
    private List<Review> reviews = new ArrayList<>();
}
