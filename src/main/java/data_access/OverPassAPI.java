package data_access;
import com.google.gson.*;
import entity.Restaurant;
import okhttp3.*;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.*;
import java.io.IOException;
//private static final String API_KEY = "PU2YFOKXUAZOVC5IM2MMSXZQJCCIC0LCQFFYK32VFJD2QAAK"; // 🔐 replace with your key

public class OverPassAPI {
    private final OkHttpClient client = new OkHttpClient();
    private final String baseUrl = "https://overpass-api.de/api/interpreter";

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

                Restaurant restaurant = new Restaurant(name, address, cuisine, vegStat, openingHours, website);
                restaurantList.add(restaurant);
            }

        } catch (IOException e) {
            System.out.println("Threw error");
            e.printStackTrace();
        }

        return restaurantList;
    }

    // Send the request and print results


    // Search by city name with custom limit and radius
//    public void searchByCity(String city, int limit, int radiusMeters) {
//        HttpUrl url = HttpUrl.parse(BASE_URL).newBuilder()
//                .addQueryParameter("near", city)
//                .addQueryParameter("categories", "13065") // Restaurants
//                .addQueryParameter("limit", String.valueOf(limit))
//                .addQueryParameter("radius", String.valueOf(radiusMeters)) // radius in meters
//                .build();
//
//        sendRequest(url);
//    }

}
