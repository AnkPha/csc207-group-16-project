package data_access;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.*;

public class NominatimAPI {
        private final OkHttpClient client = new OkHttpClient();
        private final String baseUrl = "https://nominatim.openstreetmap.org/search";

        public double[] geocode(String address) throws Exception {
            // Format address string for URL
            String encodedAddress = address.replace(" ", "+");

            String url = baseUrl + "?q=" + encodedAddress + "&format=json&limit=1";

            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent", "MySwingApp/1.0 (your@email.com)")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful() || response.body() == null) {
                    throw new RuntimeException("Nominatim request failed.");
                }

                String json = response.body().string();

                // Parse the JSON response
                JsonArray resultArray = JsonParser.parseString(json).getAsJsonArray();

                if (resultArray.size() == 0) {
                    throw new RuntimeException("No results found for: " + address);
                }

                JsonObject location = resultArray.get(0).getAsJsonObject();
                double lat = location.get("lat").getAsDouble();
                double lon = location.get("lon").getAsDouble();

                return new double[]{lat, lon};
            }
        }
    }
