package apis;

import apis.openRouteAPI.RouteData;
import apis.petrolApi.PetrolStationDat;

import java.util.UUID;

public class ApiRequestData {
    private final PetrolStationDat petrolStationDat;
    private final RouteData routeData;
    private final double completePrice;
    private final UUID uuid;


    public ApiRequestData(PetrolStationDat petrolStationDat, RouteData routeData, double completePrice, UUID uuid) {
        this.petrolStationDat = petrolStationDat;
        this.routeData = routeData;
        this.completePrice = completePrice;
        this.uuid = uuid;
    }

    public PetrolStationDat getPetrolStationDat() {
        return petrolStationDat;
    }

    public RouteData getRouteData() {
        return routeData;
    }

    public double getCompletePrice() {
        return completePrice;
    }

    public UUID getUuid() {
        return uuid;
    }
}
