package apis;

import apis.openRouteAPI.RouteData;
import apis.petrolApi.PetrolStationDat;

import java.util.UUID;

public class ApiRequestData {
    private final PetrolStationDat petrolStationDat;
    private final RouteData routeData;
    private final double completePrice;
    private final double travelcost;
    private final UUID uuid;
    private String message;


    public ApiRequestData(PetrolStationDat petrolStationDat, RouteData routeData, double completePrice, double travelcost, UUID uuid) {
        this.petrolStationDat = petrolStationDat;
        this.routeData = routeData;
        this.completePrice = completePrice;
        this.travelcost = travelcost;
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

    public double getTravelcost() {
        return travelcost;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
