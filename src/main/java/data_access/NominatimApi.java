package data_access;

import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NominatimApi {
    private final OkHttpClient client = new OkHttpClient();
    private final String baseUrl = "https://nominatim.openstreetmap.org/search";

    /**
     * A method that gets the coordinates of an address.
     * @param address the address
     * @return an object that contains the cooridnates
     * @throws IOException If the API request fails or cannot be completed.
     */
    public double[] geocode(String address) throws IOException {
        // Format address string for URL
        final String encodedAddress = address.replace(" ", "+");

        final String url = baseUrl + "?q=" + encodedAddress + "&format=json&limit=1";

        final Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "MySwingApp/1.0 dtan89609@gmail.com")
                .build();
        // 0 size = no result of address
        // 2 size = found an address and got the coords
        // 1 size = request whent through but no coords
        try (Response response = client.newCall(request).execute()) {
            double[] result;
            if (!response.isSuccessful() || response.body() == null) {
                result = new double[]{};
            }

            final String json = response.body().string();

            // Parse the JSON response
            final JsonArray resultArray = JsonParser.parseString(json).getAsJsonArray();

            if (resultArray.size() == 0) {
                result = new double[1];
            }
            else {
                final JsonObject location = resultArray.get(0).getAsJsonObject();
                final double lat = location.get("lat").getAsDouble();
                final double lon = location.get("lon").getAsDouble();
                result = new double[]{lat, lon};
            }
            return result;
        }
    }
}
