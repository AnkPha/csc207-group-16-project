package entity;
import Search.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurantTest;
    @BeforeEach
    void setup() {
//        String name,
//        String address,
//        String cuisine,
//        String vegStat,
//        String openingHours,
//        String website,
//        double[] coords
        double[] testCoords = {45.54, 59.29};
        restaurantTest = new Restaurant(
                "Burger King",
                "121 Test Address, Toronto, Canada",
                "Sushi",
                "Not Given",
                "Mo-Fr 12:00-23:00",
                "Not Given",
                testCoords
        );
    }

    @Test
    void testGetters() {
        assertEquals("Burger King", restaurantTest.getName());
        assertEquals("121 Test Address, Toronto, Canada", restaurantTest.getAddress());
        assertEquals("Sushi",restaurantTest.getCuisine());
        assertEquals("Not Given", restaurantTest.getVegStat());
        assertEquals("Mo-Fr 12:00-23:00", restaurantTest.getOpeningHours());
        assertEquals("Not Given", restaurantTest.getWebsite());
        assertEquals(59.29, restaurantTest.getLon());
        assertEquals(45.54, restaurantTest.getLat());
    }

    @Test
    void testAddRating(){
        assertEquals("No Ratings", restaurantTest.getRating());
        restaurantTest.addRating(5.0);
        assertEquals("5.0", restaurantTest.getRating());

        restaurantTest.addRating(2.0);
        assertEquals("3.5", restaurantTest.getRating());

        restaurantTest.addRating(1.0);
        assertEquals("2.6666666666666665", restaurantTest.getRating());

        restaurantTest.addRating(1.0);
        assertEquals("2.25", restaurantTest.getRating());

        restaurantTest.addRating(0.0);
        assertEquals("1.8", restaurantTest.getRating());

    }

}
