package apis.openRouteAPI;

public class testRouteApi {

    public static void main(String[] args) {
        double startLat=49.35398;
        double startLng=9.15130;
        double endLat=49.358089;
        double endLng=9.15175;
        RouteAPI routeAPI = new RouteAPI();
        System.out.println("default distance: " + routeAPI.calculateDistance(startLat,  startLng,  endLat,  endLng).getDistance());

        OpenRouteCommand orCommand = new OpenRouteCommand(startLat,  startLng,  endLat,  endLng);
        RouteData resp = orCommand.execute();
        System.out.println("Hysterix distance: " + resp.getDistance());
    }
}