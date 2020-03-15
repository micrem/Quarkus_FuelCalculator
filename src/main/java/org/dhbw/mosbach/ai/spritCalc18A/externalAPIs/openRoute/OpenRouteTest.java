package org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.openRoute;

import org.dhbw.mosbach.ai.spritCalc18A.ApiResponseWrapper;

public class OpenRouteTest {

    public static void main(String[] args) {
        double startLat = 49.35398;
        double startLng = 9.15130;
        double endLat = 49.358089;
        double endLng = 9.15175;
        OpenRouteConnector openRouteConnector = new OpenRouteConnector();
        System.out.println("default distance: " + openRouteConnector.calculateDistance(startLat, startLng, endLat, endLng).getResponseData().getDistance());

        OpenRouteCommand orCommand = new OpenRouteCommand(startLat, startLng, endLat, endLng);
        ApiResponseWrapper<OpenRouteData> resp = orCommand.execute();
        System.out.println("Hysterix distance: " + resp.getResponseData());
    }
}
