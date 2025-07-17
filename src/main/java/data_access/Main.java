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
//        ArrayList<Restaurant> results = search.getNearbyRestaurants("1 Dundas St E, Toronto, Canada", 10, 500);
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
