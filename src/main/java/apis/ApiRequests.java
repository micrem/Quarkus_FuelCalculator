package apis;

import apis.openCageAPI.Geocode;
import apis.openCageAPI.OCAPI;
import apis.openRouteAPI.RouteAPI;
import apis.petrolApi.PetrolStationApi;
import apis.petrolApi.PetrolStationDat;
import apis.petrolApi.PetrolTyp;

import java.util.ArrayList;

public class ApiRequests {

    OCAPI ocapi = new OCAPI();


    public void apiSearchStart(String street, int streetNum, int postalCode, String city, PetrolTyp petrolTyp, double petrolVolume, double petrolUsageCar){
        // Adresse in GPS Koordinate umwandel
        OCAPI ocapi = new OCAPI();
        Geocode geocodePosition = ocapi.returnGeocodeForAddressInput(streetNum,street,postalCode,city);
        // mit GPS Koordinate Tankstellsuche durchführen
        PetrolStationApi petrolStationApi = new PetrolStationApi();
        ArrayList<PetrolStationDat> petrolStationDats = petrolStationApi.search(geocodePosition.getLat(),geocodePosition.getLng(),petrolTyp);


        // Wegstrecke ermittel
        RouteAPI routeAPI = new RouteAPI();
        //RouteData routeData =routeAPI.calculateDistance();




    }



}
