package org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.tankerKoenig;

import org.dhbw.mosbach.ai.spritCalc18A.ApiResponseWrapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Api implimentierung um Umkreis-Tankstellensuche durchzuführen
 */
public class TankerKoenigConnector {
    // https://creativecommons.tankerkoenig.de/json/list.php?lat=52.521&lng=13.438&rad=1.5&sort=dist&type=all&apikey=00000000-0000-0000-0000-000000000002

    private static final String apiBaseUrl = "https://creativecommons.tankerkoenig.de/json/list.php";
    // maximaler Suchradius ist 25 km
    private static final float searchRauisIn_km = 10;
    private static final String apiKey = "2cf81686-a985-f980-7616-f299465739b1";
    private static final String sort = "dist";
    // nur zum test Public muss dann noch auf private geändetr werden datenzugriff erfolgt über die Klasse Petrol StattionApi
    public JSONObject returnObj;
    private List<TankerKoenigData> petrolStationDataList;

    public TankerKoenigConnector() {

    }

    /**
     * UmkreisTankstellensuche durchführen
     * @param geographicLatitude Startpunkt geographische Breite
     * @param geographicLongitude Startpunkt geographische Laenge
     * @param petrolTyp Benzintyp
     * @return  Liefert ApiResponseWrapper Klasse mit TankerKoeningData zurück
     */
    public ApiResponseWrapper<List<TankerKoenigData>> search(double geographicLatitude, double geographicLongitude, PetrolTyp petrolTyp) {
        petrolStationDataList = new ArrayList<>();

        HttpURLConnection connURL = null;
        StringBuilder apiResultJson = new StringBuilder();

        StringBuilder stringBuilder = new StringBuilder(apiBaseUrl);
        stringBuilder.append("?lat=" + geographicLatitude);
        stringBuilder.append("&lng=" + geographicLongitude);
        stringBuilder.append("&rad=" + searchRauisIn_km);
        stringBuilder.append("&sort=" + sort);
        stringBuilder.append("&type=" + petrolTyp.toString());
        stringBuilder.append("&apikey=" + apiKey);

        // nur zum test
        System.out.println(stringBuilder);
        try {
            URL url = new URL(stringBuilder.toString());
            connURL = (HttpURLConnection) url.openConnection();
            if (connURL.getResponseCode() != 200) return new ApiResponseWrapper<>(connURL.getResponseCode(),"PetrolAPI Http response not 200",null);
            InputStreamReader inputStreamReader = new InputStreamReader(connURL.getInputStream());
            int read;
            char[] buff = new char[1024];
            while ((read = inputStreamReader.read(buff)) != -1) {
                apiResultJson.append(buff, 0, read);
            }
            returnObj = new JSONObject(apiResultJson.toString());

        } catch (MalformedURLException e) {
            System.err.println("Fehler bei PetrolStation API URL" + e);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Verbidnungsfehler zu PetrolStation API" + e);
            e.printStackTrace();
        } finally {
            if (connURL != null)
                connURL.disconnect();
        }

        //System.out.println(apiResultJson);

        JSONObject jsonObject = new JSONObject(apiResultJson.toString());
        //System.out.println(jsonObject);
        JSONArray petrolStationArray = jsonObject.getJSONArray("stations");
        //System.out.println(petrolStationArray);
        int test = petrolStationArray.length();
        for (int i = 0; i < petrolStationArray.length(); i++) {
            try {
                JSONObject jsonObj = petrolStationArray.getJSONObject(i);
                if (jsonObj.isNull("price")) {continue;}
                if (jsonObj.isNull("lat")) {continue;}
                if (jsonObj.isNull("lng")) {continue;}
                if (jsonObj.isNull("dist")) {continue;}
                if (jsonObj.isNull("isOpen")) {continue;}
                if (jsonObj.isNull("postCode")) {continue;}


                String id = jsonObj.getString("id");
                String name = jsonObj.getString("name");
                String brand = jsonObj.getString("brand");
                String street = jsonObj.getString("street");
                String placeNamer = jsonObj.getString("place");
                double geographicLatitudeStation = jsonObj.getDouble("lat");
                double geographicLongitudeStation = jsonObj.getDouble("lng");
                double distanc = jsonObj.getDouble("dist");
                double price = jsonObj.getDouble("price");
                boolean isOpen = jsonObj.getBoolean("isOpen");
                String houseNumber = jsonObj.getString("houseNumber");
                int postCode = jsonObj.getInt("postCode");

                TankerKoenigData tankerKoenigData = new TankerKoenigData(id, name, brand, street, placeNamer, geographicLatitudeStation, geographicLongitudeStation, distanc, price, isOpen, houseNumber, postCode);

                petrolStationDataList.add(tankerKoenigData);

            } catch (JSONException e) {
                System.err.println("Fehler beim Einlesen JSON" + e);
            }
        }
        return new ApiResponseWrapper<List<TankerKoenigData>>(200,"alles gut", petrolStationDataList);
    }
}
