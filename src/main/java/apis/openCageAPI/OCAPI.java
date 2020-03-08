package apis.openCageAPI;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class OCAPI {

    private static final String urlComma = "%2C";
    private String apiURL = "https://api.opencagedata.com/geocode/v1/google-v3-json";
    private String apikey = "5c8ac5624e884cbc988ba9a5d3a23520";
    private String country = "Deutschland";//steht fest, da Tankerkoenig API nur fuer Deutschland vorgesehen ist

    public Geocode returnGeocodeForAddressInput(int streetNum, String street, int postalCode, String city) throws IOException {
        //HOW TO USE: OCAPI.returnGeocodeForAddressInput(entsprechende Variablen)
        //PLZ ist in der Tat optional, API sucht nach Stadtnamen
        String encodedStreet = URLEncoder.encode(street);
        String encodedCity = URLEncoder.encode(city);
        //URLEncoder wandelt Strassenname/Stadtname in URL-Kodierung um(d.h. die deutsche non ASCII Zeichen)
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(apiURL + "?key=" + apikey);
        urlBuilder.append("&address=" + streetNum + "+" + encodedStreet + urlComma + "+" + postalCode + "+" + encodedCity + "+" + country);

        StringBuilder results = new StringBuilder();


        URL obj = new URL(urlBuilder.toString());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        //nicht optional
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        //System.out.println("\nSending 'GET' request to URL : " + (urlBuilder.toString()));
        //System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print in String
        //System.out.println(response.toString());

        JSONObject myResponse = new JSONObject(response.toString());

        JSONObject geocode = myResponse.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");

        Geocode placeholderGeocode = new Geocode((geocode.getDouble("lng")), (geocode.getDouble("lat")));
        return placeholderGeocode;
    }


}
