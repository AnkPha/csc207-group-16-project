package data_access;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.*;

public class NominatimAPI {
        private final OkHttpClient client = new OkHttpClient();
        private final String baseUrl = "https://nominatim.openstreetmap.org/search";

        public double[] geocode(String address) throws Exception {
            System.out.println("RAN");
            // Format address string for URL
            String encodedAddress = address.replace(" ", "+");

            String url = baseUrl + "?q=" + encodedAddress + "&format=json&limit=1";

            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent", "MySwingApp/1.0 dtan89609@gmail.com")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful() || response.body() == null) {
                    return new double[]{};
                }

                String json = response.body().string();

                // Parse the JSON response
                JsonArray resultArray = JsonParser.parseString(json).getAsJsonArray();

                if (resultArray.size() == 0) {
                    System.out.println("resultArray.size() in NominatimAPI == 0");
                    return new double[0];
                }

                JsonObject location = resultArray.get(0).getAsJsonObject();
                double lat = location.get("lat").getAsDouble();
                double lon = location.get("lon").getAsDouble();

                return new double[]{lat, lon};
            }
        }
    }
