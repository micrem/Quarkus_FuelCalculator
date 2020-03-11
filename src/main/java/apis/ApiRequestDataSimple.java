package apis;

import apis.petrolApi.PetrolTyp;

public class ApiRequestDataSimple {
    /*
    name tanke
    adresse tanke
    gesamtpreis
    entfernung
    fahrtkosten
    spritpreis
    sprittyp
    */
    private String nameStation;
    private String adressStation; //FIXME: change to AdressClass instance
    private double totalPrice;
    private double distance;
    private double travelcost;
    private double spritpreis;
    private PetrolTyp petrolTyp;

//
//
//
//    public ApiRequestDataSimple(ApiRequestData apiReqFull) {
//        nameStation = petrolStationDat = petrolStationDat;
//        adressStation = routeData = routeData;
//        totalPrice = completePrice = completePrice;
//        distance = apiReqFull.getRouteData().getDistance();
//    }
//
//    public PetrolStationDat getPetrolStationDat() {
//        return petrolStationDat;
//    }
//
//    public RouteData getRouteData() {
//        return routeData;
//    }
//
//    public double getCompletePrice() {
//        return completePrice;
//    }
//
//    public UUID getUuid() {
//        return uuid;
//    }
}
