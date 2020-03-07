package apis;

import apis.openCageAPI.Geocode;
import apis.openCageAPI.OCAPI;
import apis.petrolApi.PetrolStationApi;
import apis.petrolApi.PetrolTyp;

public class ApiRequests {

    OCAPI ocapi = new OCAPI();


    public void apiSearchStart(String street, int streetNum, int postalCode, String city, PetrolTyp petrolTyp, double petrolVolume, double petrolUsageCar){
        // Adresse in GPS Koordinate umwandel
        OCAPI ocapi = new OCAPI();
        Geocode geocodePosition = ocapi.returnGeocodeForAddressInput(streetNum,street,postalCode,city);
        // mit GPS Koordinate Tankstellsuche durchführen
        PetrolStationApi petrolStationApi = new PetrolStationApi();




    }



}
