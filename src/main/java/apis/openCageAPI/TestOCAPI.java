package apis.openCageAPI;

import java.io.IOException;

public class TestOCAPI {
    public static void main(String[] args) throws IOException {
        OCAPI openCageAPI = new OCAPI();
        System.out.println("default Request lat: " + openCageAPI.returnGeocodeForAddressInput(10, "Hauptstraße", 78421, "München").getLat());
        //Hysterix Text
       // final OwmClientCommand owmClientCommand = new OwmClientCommand(OpenWeatherMapClient.OWM_CITY_ID_MOSBACH, testData);
//        final WeatherData weatherData = owmClientCommand.execute();
        OpenCageCommand ocCommand = new OpenCageCommand(10, "Hauptstraße", 78421, "München");
        Double lat = ocCommand.execute().getLat();
        System.out.println("Hysterix Request lat: " + lat);
    }
}
