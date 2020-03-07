package apis;

import apis.openCageAPI.Geocode;
import apis.openCageAPI.OCAPI;
import apis.openRouteAPI.RouteAPI;
import apis.openRouteAPI.RouteData;
import apis.petrolApi.PetrolStationApi;
import apis.petrolApi.PetrolStationDat;
import apis.petrolApi.PetrolTyp;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ApiRequests {



    private Multimap<Double, UUID> apiRequestMultimapUUID = TreeMultimap.create();
    private Map<UUID,ApiRequestData> uuidApiRequestDataHashmap = new HashMap<>();




    public ArrayList<ApiRequestData> apiSearchStart(String street, int streetNum, int postalCode, String city, PetrolTyp petrolTyp, double petrolVolume, double petrolUsageCar) throws IOException {
        // Adresse in GPS Koordinate umwandel
        OCAPI ocapi = new OCAPI();
        Geocode geocodePosition = ocapi.returnGeocodeForAddressInput(streetNum,street,postalCode,city);
        // mit GPS Koordinate Tankstellsuche durchführen
        PetrolStationApi petrolStationApi = new PetrolStationApi();
        ArrayList<PetrolStationDat> petrolStationDats = petrolStationApi.search(geocodePosition.getLat(),geocodePosition.getLng(),petrolTyp);


        // Wegstrecke ermittel
        RouteAPI routeAPI = new RouteAPI();
        for (int i = 0; i <petrolStationDats.size() ; i++) {
            RouteData routeData =routeAPI.calculateDistance(geocodePosition.getLat(),geocodePosition.getLng(),petrolStationDats.get(i).getGeographicLatitude(),petrolStationDats.get(i).getGeographicLongitude());
            // Ausrechnen vom Gesamtpreis
            double totalPrice;
            totalPrice = (petrolStationDats.get(i).getPrice()*petrolVolume) +(petrolUsageCar*(routeData.getDistance()/100000));
            UUID uuid = UUID.randomUUID();
            ApiRequestData apiRequestData = new ApiRequestData(petrolStationDats.get(i),routeData,totalPrice,uuid);
            apiRequestMultimapUUID.put(totalPrice,uuid);
            uuidApiRequestDataHashmap.put(uuid,apiRequestData);
        }
        // die drei mit den günstigen Gesmatpreis in die Liste Aufsteigende nach Preis einfuegen
        ArrayList<ApiRequestData> apiRequestDataArrayList = new ArrayList<>();
        int i = 0;
        for (Map.Entry<Double, UUID> mapData: apiRequestMultimapUUID.entries()) {
            if (i==3){
                break;
            }
            i++;
            apiRequestDataArrayList.add(uuidApiRequestDataHashmap.get(mapData.getValue()));

        }
        return apiRequestDataArrayList;
    }



}
