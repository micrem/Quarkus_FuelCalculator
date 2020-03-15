package apis.openCageAPI;

import apis.ApiResponseWrapper;
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

    public ApiResponseWrapper<Geocode> returnGeocodeForAddressInput(int streetNum, String street, int postalCode, String city) throws IOException {
        //HOW TO USE: OCAPI.returnGeocodeForAddressInput(entsprechende Variablen)
        //PLZ ist in der Tat optional, API sucht nach Stadtnamen
        String encodedStreet = "";
        String encodedCity = "";
        if (street != null) encodedStreet = URLEncoder.encode(street, java.nio.charset.Charset.defaultCharset());
        if (city != null) encodedCity = URLEncoder.encode(city, java.nio.charset.Charset.defaultCharset());

        //URLEncoder wandelt Strassenname/Stadtname in URL-Kodierung um(d.h. die deutsche non ASCII Zeichen)
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(apiURL + "?key=" + apikey);
        urlBuilder.append("&address=");
        if (streetNum != 0) urlBuilder.append(streetNum).append("+");
        urlBuilder.append(encodedStreet).append(urlComma);
        if (streetNum != 0) urlBuilder.append("+").append(postalCode);
        urlBuilder.append("+").append(encodedCity);
        urlBuilder.append("+").append(country);

        URL obj = new URL(urlBuilder.toString());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        //set http method GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        if (responseCode != 200) return new ApiResponseWrapper<>(responseCode,"OCAPI Http response not 200",null);
        System.out.println("\nSending 'GET' request to URL : " + (urlBuilder.toString()));
        System.out.println("Response Code : " + responseCode);
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

        JSONObject locationJSON = myResponse.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
        Geocode geocodeResult = new Geocode((locationJSON.getDouble("lng")), (locationJSON.getDouble("lat")));
        System.out.println("OpenCage result: " + geocodeResult.getLng() + "  " + geocodeResult.getLat());
        return new ApiResponseWrapper<Geocode>(200,"alles gut", geocodeResult);
    }


}
