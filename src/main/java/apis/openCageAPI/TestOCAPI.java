package apis.openCageAPI;

import java.io.IOException;

public class TestOCAPI {
    public static void main(String[] args) throws IOException {
        OCAPI openCageAPI = new OCAPI();
        System.out.println("default Request lat: " + openCageAPI.returnGeocodeForAddressInput(10, "Hauptstraße", 78421, "München").getLat());
        //Hysterix Text
        OpenCageCommand ocCommand = new OpenCageCommand(10, "Hauptstraße", 78421, "München");
        Geocode execute = ocCommand.execute();
        Double lat = execute.getLat();
        System.out.println("Hysterix Request lat: " + lat);
    }
}
