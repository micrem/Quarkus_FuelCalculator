package petrolApiEinfach;

import org.json.JSONObject;
import org.json.JSONString;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PetrolStationApi {
    // https://creativecommons.tankerkoenig.de/json/list.php?lat=52.521&lng=13.438&rad=1.5&sort=dist&type=all&apikey=00000000-0000-0000-0000-000000000002

    private static  final  String apiBaseUrl ="https://creativecommons.tankerkoenig.de/json/list.php";
    // maximaler Suchradius ist 25 km
    private static final float  searchRauisIn_km =25;
    private static final String apiKey ="2cf81686-a985-f980-7616-f299465739b1";
    private static final String sort = "dist";
    private final double geographicLatitude;
    private final double geographicLongitude;
    private final PetrolTyp petrolTyp;
    private ArrayList<PetrolStationDat> petrolStationDatArrayList;
    public JSONObject returnObj;

    public PetrolStationApi(double geographicLatitude, double geographicLongitude, PetrolTyp petrolTyp) {
        this.geographicLatitude = geographicLatitude;
        this.geographicLongitude = geographicLongitude;
        this.petrolTyp = petrolTyp;
    }

    public void search(){
        petrolStationDatArrayList = new ArrayList<>();

        HttpURLConnection connURL = null;
        StringBuilder apiResultJson = new StringBuilder();

        StringBuilder stringBuilder = new StringBuilder(apiBaseUrl);
        stringBuilder.append("?lat="+ geographicLatitude);
        stringBuilder.append("&lng="+geographicLongitude);
        stringBuilder.append("&rad="+searchRauisIn_km);
        stringBuilder.append("&sort="+sort);
        stringBuilder.append("&type="+petrolTyp.toString());
        stringBuilder.append("&apikey="+apiKey);

        // nur zum test
        System.out.println(stringBuilder);
        try{
            URL url = new URL(stringBuilder.toString());
            connURL = (HttpURLConnection) url.openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(connURL.getInputStream());
            int read;
            char[] buff = new char[1024];
            while ((read = inputStreamReader.read(buff)) != -1) {
                apiResultJson.append(buff, 0, read);
            }
            returnObj = new JSONObject(apiResultJson.toString());
        // muss noch verbessert werden*********************** Siehe FreeTimeActicityExplorer
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connURL != null)
                connURL.disconnect();
        }

        System.out.println(apiResultJson);


    }




}
