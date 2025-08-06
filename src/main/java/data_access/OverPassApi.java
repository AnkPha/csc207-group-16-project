package data_access;

//  import com.google.gson.*;
//  import okhttp3.*;
//  import okhttp3.*;
//  import java.util.*;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Restaurant;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OverPassApi {
    private final OkHttpClient client = new OkHttpClient();
    //  https://overpass.kumi.systems/api/interpreter
    //  https://overpass-api.de/api/interpreter
    private final String baseUrl = "https://overpass.kumi.systems/api/interpreter";

    /**
     * A method that gets the nearby restaurants.
     * @param latitude the lat
     * @param longitude the lon
     * @param radiusMeters the radius in meters
     * @return an arraylist of restaurats
     */
    public ArrayList<Restaurant> getNearbyRestaurants(double latitude, double longitude, int radiusMeters) {
        final ArrayList<Restaurant> restaurantList = new ArrayList<>();
        final String query = buildQuery(latitude, longitude, radiusMeters);
        final RequestBody body = new FormBody.Builder().add("data", query).build();

        final Request request = new Request.Builder()
                .url(baseUrl)
                .post(body)
                .header("User-Agent", "MySwingApp/1.0 (dtan89609@gmail.com)")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Overpass API request failed with code: " + response.code());
            }

            final String json = response.body().string();
            final JsonArray elements = JsonParser.parseString(json).getAsJsonObject().getAsJsonArray("elements");

            for (JsonElement element : elements) {
                restaurantList.add(parseRestaurant(element.getAsJsonObject()));
            }

        }
        catch (IOException exception) {
            System.out.println("Threw error");
            exception.printStackTrace();
        }
        return restaurantList;
    }

    private String buildQuery(double latitude, double longitude, int radiusMeters) {
        return "[out:json];"
                + "node[\"amenity\"=\"restaurant\"](around:" + radiusMeters + "," + latitude + "," + longitude + ");"
                + "out;";
    }

    private Restaurant parseRestaurant(JsonObject obj) {
        final String notGiven = "Not given";
        final JsonObject tags = obj.getAsJsonObject("tags");

        final String name = getTagValue(tags, "name", "Unnamed Restaurant");
        final String address = buildAddress(tags);
        final String cuisine = getTagValue(tags, "cuisine", notGiven);
        final String vegStat = getTagValue(tags, "diet:vegetarian", notGiven);
        final String openingHours = getTagValue(tags, "opening_hours", notGiven);
        final String website = getTagValue(tags, "website", notGiven);
        final double lat;
        if (obj.has("lat")) {
            lat = obj.get("lat").getAsDouble();
        }
        else {
            lat = 0.0;
        }

        final double lon;
        if (obj.has("lon")) {
            lon = obj.get("lon").getAsDouble();
        }
        else {
            lon = 0.0;
        }
        final double[] coords = {lat, lon};
        return new Restaurant(name, address, cuisine, vegStat, openingHours, website, coords);
    }

    private String getTagValue(JsonObject tags, String key, String defaultValue) {
        final String result;
        if (tags.has(key)) {
            result = tags.get(key).getAsString();
        }
        else {
            result = defaultValue;
        }
        return result;
    }

    private String buildAddress(JsonObject tags) {
        String result = "Address not available";
        if (tags.has("addr:street")) {
            final String street = tags.get("addr:street").getAsString();
            if (tags.has("addr:housenumber")) {
                result = tags.get("addr:housenumber").getAsString() + " " + street;
            }
        }
        return result;
    }
}
