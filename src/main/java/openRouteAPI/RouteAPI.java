package openRouteAPI;

import java.net.HttpURLConnection;

public class RouteAPI {
    // https://api.openrouteservice.org/v2/directions/driving-car?api_key=5b3ce3597851110001cf6248db35efc2a3fc4c6386a52907a6cb40f0&start=9.15130,49.35398&end=9.15175,49.35808
    private static  final  String apiBaseUrl ="https://api.openrouteservice.org/v2";
    private static final String apiKey ="5b3ce3597851110001cf6248db35efc2a3fc4c6386a52907a6cb40f0";
    private static final String  service = "directions";
    private static final String meansOfTransportation = "driving-car";

    public RouteAPI() {
    }


    public RouteData calculateDistance(double startGeographicLatitude, double startGeographicLongitude,double endGeographicLatitude, double endGeographicLongitude){

        HttpURLConnection connURL = null;

        StringBuilder stringBuilder = new StringBuilder(apiBaseUrl);
        stringBuilder.append("/"+service );
        stringBuilder.append("/"+meansOfTransportation);
        stringBuilder.append("?api_key="+apiKey);
        stringBuilder.append("&start="+startGeographicLongitude+","+startGeographicLatitude);
        stringBuilder.append("&end="+endGeographicLongitude+","+endGeographicLatitude);

        // nur zum test
        //System.out.println(stringBuilder);



        return  null;


    }


}
