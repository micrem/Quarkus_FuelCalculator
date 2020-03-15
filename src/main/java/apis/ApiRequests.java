package apis;

import apis.openCageAPI.Geocode;
import apis.openCageAPI.OCAPI;
import apis.openCageAPI.OpenCageCommand;
import apis.openRouteAPI.RouteAPI;
import apis.openRouteAPI.RouteData;
import apis.petrolApi.PetrolStationApi;
import apis.petrolApi.PetrolStationDat;
import apis.petrolApi.PetrolTyp;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.*;

public class ApiRequests {
    private static final Logger LOG = Logger.getLogger(ApiRequests.class);

    private Multimap<Double, UUID> apiRequestMultimapUUID = TreeMultimap.create();
    private Map<UUID, ApiRequestData> uuidApiRequestDataHashmap = new HashMap<>();


    public List<ApiRequestData> apiSearchStart(String street, int streetNum, int postalCode, String city, PetrolTyp petrolTyp, double petrolVolume, double petrolUsageCar) throws IOException {
        String circuitStatus;
        ApiRequestData defaultReturn = new ApiRequestData(null, null, 0, 0, null);
        // Adresse in GPS Koordinate umwandel
        OpenCageCommand ocCommand = new OpenCageCommand(streetNum, street, postalCode, city);
        //Geocode geocodePosition = ocCommand.execute();
        OCAPI openCageAPI = new OCAPI();
        ApiResponseWrapper<Geocode> geocodePosition = openCageAPI.returnGeocodeForAddressInput(streetNum, street, postalCode, city);
        if (geocodePosition.getStatus()!=200){
            return getFallBackData("OCAPI Http response not 200");
        }
        LOG.info("OpenCage result:" + geocodePosition.getResponseData().getLng() + " " + geocodePosition.getResponseData().getLat());
        circuitStatus = ocCommand.isCircuitBreakerOpen() ? "Open" : "Closed";
        if (ocCommand.isResponseFromFallback())
            LOG.info("OpenCageCommand returned fallback! called with: " + Arrays.asList(streetNum, street, postalCode, city));

        if (geocodePosition.getResponseData().getLat() == -1) {
            defaultReturn.setMessage("Fehler Geocode: konnte keinen Geocode zur Adresse finden!");

            if (circuitStatus.equals("Open"))
                defaultReturn.setMessage(defaultReturn.getMessage() + " Circuitbreaker: Open");
            //return Arrays.asList(defaultReturn);
            return getFallBackData("Fehler Geocode: konnte keinen Geocode zur Adresse finden!");
        }

        // mit GPS Koordinate Tankstellsuche durchführen
        ApiResponseWrapper<List<PetrolStationDat>> petrolStationDatsWrapper = new PetrolStationApi().search(geocodePosition.getResponseData().getLat(), geocodePosition.getResponseData().getLng(), petrolTyp);
        if(petrolStationDatsWrapper.getStatus()!=200) {
            return  getFallBackData("PetrolAPI Http response not 200");
        }
        List<PetrolStationDat> petrolStationDats = petrolStationDatsWrapper.getResponseData();
        //PetrolStationCommand petrolStationCommand = new PetrolStationCommand(geocodePosition.getLat(), geocodePosition.getLng(), petrolTyp);
        //List<PetrolStationDat> petrolStationDats = petrolStationCommand.execute();
//        if (petrolStationCommand.isResponseFromFallback())
//            LOG.info("PetrolStationCommand returned fallback! called with: " + Arrays.asList(geocodePosition.getLat(), geocodePosition.getLng(), petrolTyp));
//        circuitStatus = petrolStationCommand.isCircuitBreakerOpen() ? "Open" : "Closed";
        if (petrolStationDats.isEmpty()) {
            System.out.println("petrolStationDats.isEmpty() true");
            return getFallBackData("Es konnten keine Tankstellen gefunden werden");
        } else if (petrolStationDats.get(0).getId().equals("emptyID")) {
            System.out.println("petrolStationDats.get(0).getId()=" + petrolStationDats.get(0).getId());
        }
        System.out.println("petrolStations size: " + petrolStationDats.size());
        if (petrolStationDats.isEmpty() || petrolStationDats.get(0).getId().equals("emptyID")) {
            defaultReturn.setMessage("Fehler Geocode: konnte keine Tankstellen zur Adresse finden!");
//            if (circuitStatus.equals("Open"))
//                defaultReturn.setMessage(defaultReturn.getMessage() + " Circuitbreaker: Open");
            return Arrays.asList(defaultReturn);
        }

        System.out.println("found " + petrolStationDats.size() + " stations");
        boolean fallback= wegstreckeErmitteln(petrolVolume, petrolUsageCar, geocodePosition.getResponseData(), petrolStationDats);
        if (fallback){
            return getFallBackData("RouteAPI Http response not 200");
        }

        ArrayList<ApiRequestData> apiRequestDataArrayList = getTop3CheapestStations();

        return apiRequestDataArrayList;
    }

    private boolean wegstreckeErmitteln(double petrolVolume, double petrolUsageCar, Geocode geocodePosition, List<PetrolStationDat> petrolStationDats) {

        for (int i = 0; i < petrolStationDats.size(); i++) {
//            OpenRouteCommand orCommand = new OpenRouteCommand(geocodePosition.getLat(), geocodePosition.getLng(), petrolStationDats.get(i).getGeographicLatitude(), petrolStationDats.get(i).getGeographicLongitude());
//            RouteData routeData = orCommand.execute();
//            if (orCommand.isCircuitBreakerOpen()) LOG.info("OpenRouteCommand Circuitbreaker open!");
//            if (orCommand.isResponseFromFallback())
//                LOG.info("OpenRouteCommand returned fallback! called with: " + Arrays.asList(geocodePosition.getLat(), geocodePosition.getLng(), petrolStationDats.get(i).getGeographicLatitude(), petrolStationDats.get(i).getGeographicLongitude()));
            RouteAPI routeAPI = new RouteAPI();
            ApiResponseWrapper<RouteData> routeData = routeAPI.calculateDistance(geocodePosition.getLat(), geocodePosition.getLng(), petrolStationDats.get(i).getGeographicLatitude(), petrolStationDats.get(i).getGeographicLongitude());
            if (routeData.getStatus()!=200){
                return true;
            }
            // Ausrechnen vom Gesamtpreis
            double totalPrice;
            double travelcost = (petrolUsageCar * (routeData.getResponseData().getDistance() / 100000));
            totalPrice = (petrolStationDats.get(i).getPrice() * petrolVolume) + travelcost;
            UUID uuid = UUID.randomUUID();
            ApiRequestData apiRequestData = new ApiRequestData(petrolStationDats.get(i), routeData.getResponseData(), totalPrice, travelcost, uuid);
            apiRequestMultimapUUID.put(totalPrice, uuid);
            uuidApiRequestDataHashmap.put(uuid, apiRequestData);
        }
        return false;
    }

    private ArrayList<ApiRequestData> getTop3CheapestStations() {
        // die drei mit den günstigen Gesamtpreis in die Liste Aufsteigende nach Preis einfuegen
        ArrayList<ApiRequestData> apiRequestDataArrayList = new ArrayList<>();
        int i = 0;
        for (Map.Entry<Double, UUID> mapData : apiRequestMultimapUUID.entries()) {
            if (i++ >= 3) break;
            apiRequestDataArrayList.add(uuidApiRequestDataHashmap.get(mapData.getValue()));
        }
        return apiRequestDataArrayList;
    }

    public ArrayList<ApiRequestData> getFallBackData(String reason){
        PetrolStationDat petrolStationDat1 = new PetrolStationDat(null,"FallbackDaten: "+reason + "   Freie Automatentankstelle","Freie Tankstelle","Musterstarsee","",49.148681640625,8.9788904190063,6.7,1.249,true,"14",75050);
        RouteData routeData1 = new RouteData(8569.1,695.1);
        ApiRequestData apiRequestData1 = new ApiRequestData(petrolStationDat1,routeData1,10.79,0.685,UUID.randomUUID());
        ArrayList<ApiRequestData> apiRequestDataArrayList = new ArrayList<>();
        apiRequestDataArrayList.add(apiRequestData1);
        apiRequestDataArrayList.add(apiRequestData1);
        apiRequestDataArrayList.add(apiRequestData1);






        return apiRequestDataArrayList;


    }


}
