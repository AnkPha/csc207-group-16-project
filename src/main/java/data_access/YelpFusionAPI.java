package data_access;
import com.google.gson.*;
import entity.Restaurant;
import okhttp3.*;
import java.util.*;

public class YelpFusionAPI {
    private static final String API_KEY = "YOUR_YELP_API_KEY"; // 🔐 replace with your key
    private static final String BASE_URL = "https://api.yelp.com/v3/businesses/search";
    private final OkHttpClient client = new OkHttpClient();

    public List<Restaurant> getRestaurants(double lat, double lon, int radiusMeters) throws Exception {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("latitude", String.valueOf(lat));
        urlBuilder.addQueryParameter("longitude", String.valueOf(lon));
        urlBuilder.addQueryParameter("radius", String.valueOf(radiusMeters));
        urlBuilder.addQueryParameter("categories", "restaurants");
        urlBuilder.addQueryParameter("limit", "20");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .header("Authorization", "Bearer " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new RuntimeException("Yelp API request failed with code: " + response.code());
            }

            String json = response.body().string();
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();
            JsonArray businesses = root.getAsJsonArray("businesses");

            List<Restaurant> results = new ArrayList<>();

            for (JsonElement e : businesses) {
                JsonObject business = e.getAsJsonObject();

                String name = business.get("name").getAsString();
                double businessLat = business.getAsJsonObject("coordinates").get("latitude").getAsDouble();
                double businessLon = business.getAsJsonObject("coordinates").get("longitude").getAsDouble();
                String yelpUrl = business.get("url").getAsString();
                String price = business.has("price") ? business.get("price").getAsString() : "N/A";
                double rating = business.get("rating").getAsDouble();

                JsonObject location = business.getAsJsonObject("location");
                String address = String.join(", ",
                        location.get("address1").getAsString(),
                        location.get("city").getAsString(),
                        location.get("state").getAsString(),
                        location.get("zip_code").getAsString());

                results.add(new Restaurant(name, businessLat, businessLon, address, price, rating, yelpUrl));
            }

            return results;
        }
    }
}
