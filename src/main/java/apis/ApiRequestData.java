package apis;

import apis.openRouteAPI.RouteData;
import apis.petrolApi.PetrolStationDat;

public class ApiRequestData {
    private final PetrolStationDat petrolStationDat;
    private final RouteData routeData;

    public ApiRequestData(PetrolStationDat petrolStationDat, RouteData routeData) {
        this.petrolStationDat = petrolStationDat;
        this.routeData = routeData;
    }
    
}
