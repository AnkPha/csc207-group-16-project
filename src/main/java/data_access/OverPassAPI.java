package data_access;
import com.google.gson.*;
import entity.Restaurant;
import okhttp3.*;
import java.util.*;
import java.io.IOException;

public class OverPassAPI {
    private final OkHttpClient client = new OkHttpClient();
    //https://overpass.kumi.systems/api/interpreter
    //https://overpass-api.de/api/interpreter
    private final String baseUrl = "https://overpass.kumi.systems/api/interpreter";

    public ArrayList<Restaurant> getNearbyRestaurants(double latitude, double longitude, int radiusMeters) {
        ArrayList<Restaurant> restaurantList = new ArrayList<>();

        String query = "[out:json];"
                + "node[\"amenity\"=\"restaurant\"](around:" + radiusMeters + "," + latitude + "," + longitude + ");"
                + "out;";

        RequestBody body = new FormBody.Builder()
                .add("data", query)
                .build();

        Request request = new Request.Builder()
                .url(baseUrl)
                .post(body)
                .header("User-Agent", "MySwingApp/1.0 (dtan89609@gmail.com)")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Overpass API request failed with code: " + response.code());
            }

            String json = response.body().string();
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            JsonArray elements = jsonObject.getAsJsonArray("elements");

            for (JsonElement element : elements) {
                JsonObject obj = element.getAsJsonObject();
                JsonObject tags = obj.getAsJsonObject("tags");

                String name = tags.has("name") ? tags.get("name").getAsString() : "Unnamed Restaurant";

                String address = "Address not available";
                if (tags.has("addr:street")) {
                    address = tags.get("addr:street").getAsString();
                    if (tags.has("addr:housenumber")) {
                        address = tags.get("addr:housenumber").getAsString() + " " + address;
                    }
                }

                String cuisine = tags.has("cuisine") ? tags.get("cuisine").getAsString() : "Not given";
                String vegStat = tags.has("diet:vegetarian") ? tags.get("diet:vegetarian").getAsString() : "Not given";
                String openingHours = tags.has("opening_hours") ? tags.get("opening_hours").getAsString() : "Not given";
                String website = tags.has("website") ? tags.get("website").getAsString() : "Not given";

                double lat = obj.has("lat") ? obj.get("lat").getAsDouble() : 0.0;
                double lon = obj.has("lon") ? obj.get("lon").getAsDouble() : 0.0;
                Restaurant restaurant = new Restaurant(name, address, cuisine, vegStat, openingHours, website, lat, lon);
                restaurantList.add(restaurant);
            }

        } catch (IOException e) {
            System.out.println("Threw error");
            e.printStackTrace();
        }

        return restaurantList;
    }
}
