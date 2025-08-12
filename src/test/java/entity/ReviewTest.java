package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class ReviewTest {
    Review reviewTest;
    double[] testCoords = {45.54, 59.29};
    LocalDateTime testTimestamp;
    Restaurant restaurantTest = new Restaurant(
            "Burger King",
            "121 Test Address, Toronto, Canada",
            "Sushi",
            "Not Given",
            "Mo-Fr 12:00-23:00",
            "Not Given",
            testCoords
    );
    User userTest = new CommonUser(
            "Adam",
            "1234"
    );
    @BeforeEach
    public void setup() {
        testTimestamp = LocalDateTime.now();
        reviewTest = new Review (
                restaurantTest,
                10,
                2,
                testTimestamp,
                userTest,
                "Good Food"
        );
    }

    @Test
    void testGetters() {
        assertEquals(restaurantTest, reviewTest.getRestaurant());
        assertEquals(2, reviewTest.getRating());
        assertEquals(10, reviewTest.getReviewId());
        assertEquals(userTest, reviewTest.getUser());
        assertEquals(testTimestamp, reviewTest.getTimestamp());
        assertEquals("Good Food", reviewTest.getReviewText());
    }

    @Test
    void testConstructorWithoutReviewText() {
        Review reviewWithoutText = new Review(
                restaurantTest,
                15,
                4,
                testTimestamp,
                userTest
        );

        assertEquals(restaurantTest, reviewWithoutText.getRestaurant());
        assertEquals(4, reviewWithoutText.getRating());
        assertEquals(15, reviewWithoutText.getReviewId());
        assertEquals(userTest, reviewWithoutText.getUser());
        assertEquals(testTimestamp, reviewWithoutText.getTimestamp());
        assertEquals("", reviewWithoutText.getReviewText());
    }

    @Test
    void testConstructorWithRatingTooLow() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Review(restaurantTest, 20, 0, testTimestamp, userTest, "Bad rating");
        });
        assertEquals("Rating must be between 1 and 5", exception.getMessage());
    }

    @Test
    void testConstructorWithRatingTooHigh() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Review(restaurantTest, 21, 6, testTimestamp, userTest, "Bad rating");
        });
        assertEquals("Rating must be between 1 and 5", exception.getMessage());
    }

    @Test
    void testConstructorWithMinValidRating() {
        Review reviewMinRating = new Review(restaurantTest, 22, 1, testTimestamp, userTest, "Min rating");
        assertEquals(1, reviewMinRating.getRating());
    }

    @Test
    void testConstructorWithMaxValidRating() {
        Review reviewMaxRating = new Review(restaurantTest, 23, 5, testTimestamp, userTest, "Max rating");
        assertEquals(5, reviewMaxRating.getRating());
    }

    @Test
    void testEqualsSameObject() {
        assertTrue(reviewTest.equals(reviewTest));
    }

    @Test
    void testEqualsNull() {
        assertFalse(reviewTest.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        String notAReview = "Not Given";
        assertFalse(reviewTest.equals(notAReview));
    }

    @Test
    void testEqualsWithSameReviewId() {
        Review anotherReview = new Review(
                restaurantTest,
                10,
                3,
                LocalDateTime.now(),
                new CommonUser("Different", "User"),
                "Different text"
        );
        assertTrue(reviewTest.equals(anotherReview));
    }

    @Test
    void testEqualsWithDifferentReviewId() {
        Review anotherReview = new Review(
                restaurantTest,
                9,
                2,
                testTimestamp,
                userTest,
                "Good Food"
        );
        assertFalse(reviewTest.equals(anotherReview));
    }

    @Test
    void testHashcode() {
        Review anotherReview = new Review(
                restaurantTest,
                10,
                3,
                testTimestamp,
                userTest,
                "Different text"
        );

        assertEquals(reviewTest.hashCode(), anotherReview.hashCode());

        Review differentReview = new Review(
                restaurantTest,
                25,
                2,
                testTimestamp,
                userTest,
                "Good Food"
        );

        assertNotEquals(reviewTest.hashCode(), differentReview.hashCode());
    }

    @Test
    void testHashCodeConsistency() {
        int firstHashCode = reviewTest.hashCode();
        int secondHashCode = reviewTest.hashCode();
        assertEquals(firstHashCode, secondHashCode);
    }
}
