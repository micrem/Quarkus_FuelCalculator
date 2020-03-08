package apis.openRouteAPI;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RouteAPI {
    // https://api.openrouteservice.org/v2/directions/driving-car?api_key=5b3ce3597851110001cf6248db35efc2a3fc4c6386a52907a6cb40f0&start=9.15130,49.35398&end=9.15175,49.35808
    private static final String apiBaseUrl = "https://api.openrouteservice.org/v2";
    private static final String apiKey = "5b3ce3597851110001cf6248db35efc2a3fc4c6386a52907a6cb40f0";
    private static final String service = "directions";
    private static final String meansOfTransportation = "driving-car";

    public RouteAPI() {
    }


    public RouteData calculateDistance(double startGeographicLatitude, double startGeographicLongitude, double endGeographicLatitude, double endGeographicLongitude) {

        HttpURLConnection connURL = null;

        StringBuilder stringBuilder = new StringBuilder(apiBaseUrl);
        stringBuilder.append("/" + service);
        stringBuilder.append("/" + meansOfTransportation);
        stringBuilder.append("?api_key=" + apiKey);
        stringBuilder.append("&start=" + startGeographicLongitude + "," + startGeographicLatitude);
        stringBuilder.append("&end=" + endGeographicLongitude + "," + endGeographicLatitude);

        // nur zum test
        //System.out.println(stringBuilder);
        JSONObject returnObjJson;
        StringBuilder apiResultJson = new StringBuilder();


        try {
            URL url = new URL(stringBuilder.toString());
            connURL = (HttpURLConnection) url.openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(connURL.getInputStream());
            int read;
            char[] buff = new char[1024];

            while ((read = inputStreamReader.read(buff)) != -1) {
                apiResultJson.append(buff, 0, read);
            }

        } catch (MalformedURLException e) {
            System.err.println("Fehler bei RoteStation API URL" + e);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Verbidnungsfehler zu Route API" + e);
            e.printStackTrace();
        } finally {
            if (connURL != null)
                connURL.disconnect();
        }


        JSONObject jsonObject = new JSONObject(apiResultJson.toString());
        JSONObject jsonObjectDistDura = jsonObject.getJSONArray("features").getJSONObject(0).getJSONObject("properties").getJSONArray("segments").getJSONObject(0);
        double distance = jsonObjectDistDura.getDouble("distance");
        double duration = jsonObjectDistDura.getDouble("duration");

        RouteData routeData = new RouteData(distance, duration);

        return routeData;

    }


}
