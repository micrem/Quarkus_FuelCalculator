package apis.openCageAPI;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class OCAPITestClass {
    public static void main(String[] args) throws IOException {
        String text = "München";
        String encodedText = URLEncoder.encode(text);
        System.out.println(encodedText);

        int streetNum = 10;
        String street = "Hauptstraße";
        int postalCode = 85579;
        String city = "München";
        String apiURL = "https://api.opencagedata.com/geocode/v1/google-v3-json";
        String apikey = "5c8ac5624e884cbc988ba9a5d3a23520";
        String country = "Deutschland";

        String encodedStreet = URLEncoder.encode(street);
        String encodedCity = URLEncoder.encode(city);
        //URLEncoder wandelt STrassenname/Stadtname in URL-Kodierung um(d.h. die deutsche non ASCII Zeichen)
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(apiURL + "?key=" + apikey);
        urlBuilder.append("&address=" + streetNum + "+" + encodedStreet + "%2C" + "+" + postalCode + "+" + encodedCity + "+" + country);

        System.out.println(urlBuilder.toString());//funktioniert


        URL obj = new URL(urlBuilder.toString());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print in String
        System.out.println(response.toString());

        JSONObject myResponse = new JSONObject(response.toString());

        System.out.println(myResponse);


        JSONObject geocode = myResponse.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
        double length = geocode.getDouble("lng");


        System.out.println(length);
        System.out.println(geocode.getDouble("lat"));


    }
}
