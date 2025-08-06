//package data_access;
//
//import entity.Restaurant;
//
//import java.util.ArrayList;
//
//public class Main {
//    public static void main(String[] args){
//        SearchLocationNearbyDataAccessObject search = new SearchLocationNearbyDataAccessObject();
//        //Put Address here
//        ArrayList<Restaurant> results = search.getNearbyRestaurants("1 Dundas St E, Toronto, Canada", 500);
//        for (int i = 0; i < results.size(); i++) {
//            System.out.println("Name " + results.get(i).getName() +
//                    " Address " + results.get(i).getAddress() +
//                    " Cuisine "  + results.get(i).getCuisine() +
//                    " VegStat? " + results.get(i).getVegStat() +
//                    " Hours Of Operation " + results.get(i).getOpeningHours() +
//                    " URL " + results.get(i).getWebsite()
//            );
//        }
//    }
//}

//        final ArrayList<Restaurant> restaurantList = new ArrayList<>();
//
//        final String query = "[out:json];"
//                + "node[\"amenity\"=\"restaurant\"](around:" + radiusMeters + "," + latitude + "," + longitude + ");"
//                + "out;";
//
//        final RequestBody body = new FormBody.Builder()
//                .add("data", query)
//                .build();
//
//        final Request request = new Request.Builder()
//                .url(baseUrl)
//                .post(body)
//                .header("User-Agent", "MySwingApp/1.0 (dtan89609@gmail.com)")
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful() || response.body() == null) {
//                throw new IOException("Overpass API request failed with code: " + response.code());
//            }
//
//            final String json = response.body().string();
//            final JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
//            final JsonArray elements = jsonObject.getAsJsonArray("elements");
//
//            for (JsonElement element : elements) {
//                final JsonObject obj = element.getAsJsonObject();
//                final JsonObject tags = obj.getAsJsonObject("tags");
//
//                final String name = tags.has("name") ? tags.get("name").getAsString() : "Unnamed Restaurant";
//
//                String address = "Address not available";
//                if (tags.has("addr:street")) {
//                    address = tags.get("addr:street").getAsString();
//                    if (tags.has("addr:housenumber")) {
//                        address = tags.get("addr:housenumber").getAsString() + " " + address;
//                    }
//                }
//
//                final String cuisine = tags.has("cuisine") ? tags.get("cuisine").getAsString() : "Not given";
//                final String vegStat = tags.has("diet:vegetarian")
//                ? tags.get("diet:vegetarian").getAsString() : "Not given";

//                final String openingHours = tags.has("opening_hours")
//                ? tags.get("opening_hours").getAsString() : "Not given";

//                final String website = tags.has("website") ? tags.get("website").getAsString() : "Not given";
//
//                final double lat = obj.has("lat") ? obj.get("lat").getAsDouble() : 0.0;
//                final double lon = obj.has("lon") ? obj.get("lon").getAsDouble() : 0.0;
//                final Restaurant restaurant = new Restaurant(
//                name, address, cuisine, vegStat, openingHours, website, lat, lon);
//                restaurantList.add(restaurant);
//            }
//
//        }
//        catch (IOException exception) {
//            System.out.println("Threw error");
//            exception.printStackTrace();
//        }
//
//      return restaurantList;

