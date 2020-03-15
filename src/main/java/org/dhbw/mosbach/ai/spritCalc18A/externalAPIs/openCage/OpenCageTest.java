package org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.openCage;

import org.dhbw.mosbach.ai.spritCalc18A.ApiResponseWrapper;

import java.io.IOException;

public class OpenCageTest {
    public static void main(String[] args) throws IOException {
        OpenCageConnector openCageAPI = new OpenCageConnector();
        System.out.println("default Request lat: " + openCageAPI.returnGeocodeForAddressInput(10, "Hauptstraße", 78421, "München").getResponseData().getLat());
        System.out.println("default Request lat: " + openCageAPI.returnGeocodeForAddressInput(0, "", 0, "").getResponseData().getLat());
        //Hysterix Text
        OpenCageCommand ocCommand = new OpenCageCommand(10, "Hauptstraße", 78421, "München");
        ApiResponseWrapper<OpenCageData> execute = ocCommand.execute();
        Double lat = execute.getResponseData().getLat();
        System.out.println("Hysterix Request lat: " + lat);
    }
}
