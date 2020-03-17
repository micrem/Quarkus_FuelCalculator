package org.dhbw.mosbach.ai.spritCalc18A;

import org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.openCage.OpenCageData;
import org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.tankerKoenig.PetrolTyp;
import org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.openCage.OpenCageConnector;
import org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.openCage.OpenCageCommand;
import org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.openRoute.OpenRouteConnector;
import org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.openRoute.OpenRouteData;
import org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.tankerKoenig.TankerKoenigConnector;
import org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.tankerKoenig.TankerKoenigData;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.*;

/**
 * Ausführen aller exterenen Apis + Brechnunglogik + Eventuelle FallBack Daten zurueckliefern
 */
public class SpritCalcBL {
    private static final Logger LOG = Logger.getLogger(SpritCalcBL.class);

    private Multimap<Double, UUID> apiRequestMultimapUUID = TreeMultimap.create();
    private Map<UUID, SpritCalcBLData> uuidApiRequestDataHashmap = new HashMap<>();

    /**
     * Ausführen der externen Apis und drei Guenstigeste Tankstelen zurückliefern
     * @param street    Standort Strassenname
     * @param streetNum Standort Strassennummer
     * @param postalCode  Standort  PLZ
     * @param city Standort Stadt
     * @param petrolTyp Benzintyp nach dem gesucht wird
     * @param petrolVolume  Menge des zu erwerbende Kraftstoffes in Littern
     * @param petrolUsageCar    Verbrauch des eignen Autos  in Littern pro 100 km
     * @return Liefert eine Liste mit den drei guenstigsten Tankstellen zurueck
     * @throws IOException
     */
    public List<SpritCalcBLData> apiSearchStart(String street, int streetNum, int postalCode, String city, PetrolTyp petrolTyp, double petrolVolume, double petrolUsageCar) throws IOException {
        String circuitStatus;
        SpritCalcBLData defaultReturn = new SpritCalcBLData(null, null, 0, 0, null);
        // Adresse in GPS Koordinate umwandel
        OpenCageCommand ocCommand = new OpenCageCommand(streetNum, street, postalCode, city);
        //Geocode geocodePosition = ocCommand.execute();
        OpenCageConnector openCageAPI = new OpenCageConnector();
        ApiResponseWrapper<OpenCageData> geocodePosition = openCageAPI.returnGeocodeForAddressInput(streetNum, street, postalCode, city);
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
        ApiResponseWrapper<List<TankerKoenigData>> petrolStationDatsWrapper = new TankerKoenigConnector().search(geocodePosition.getResponseData().getLat(), geocodePosition.getResponseData().getLng(), petrolTyp);
        if(petrolStationDatsWrapper.getStatus()!=200) {
            return  getFallBackData("PetrolAPI Http response not 200");
        }
        List<TankerKoenigData> tankerKoenigData = petrolStationDatsWrapper.getResponseData();
        //PetrolStationCommand petrolStationCommand = new PetrolStationCommand(geocodePosition.getLat(), geocodePosition.getLng(), petrolTyp);
        //List<PetrolStationDat> petrolStationDats = petrolStationCommand.execute();
//        if (petrolStationCommand.isResponseFromFallback())
//            LOG.info("PetrolStationCommand returned fallback! called with: " + Arrays.asList(geocodePosition.getLat(), geocodePosition.getLng(), petrolTyp));
//        circuitStatus = petrolStationCommand.isCircuitBreakerOpen() ? "Open" : "Closed";
        if (tankerKoenigData.isEmpty()) {
            System.out.println("petrolStationDats.isEmpty() true");
            return getFallBackData("Es konnten keine Tankstellen gefunden werden");
        } else if (tankerKoenigData.get(0).getId().equals("emptyID")) {
            System.out.println("petrolStationDats.get(0).getId()=" + tankerKoenigData.get(0).getId());
        }
        System.out.println("petrolStations size: " + tankerKoenigData.size());
        if (tankerKoenigData.isEmpty() || tankerKoenigData.get(0).getId().equals("emptyID")) {
            defaultReturn.setMessage("Fehler Geocode: konnte keine Tankstellen zur Adresse finden!");
//            if (circuitStatus.equals("Open"))
//                defaultReturn.setMessage(defaultReturn.getMessage() + " Circuitbreaker: Open");
            return Arrays.asList(defaultReturn);
        }

        System.out.println("found " + tankerKoenigData.size() + " stations");
        boolean fallback= wegstreckeErmitteln(petrolVolume, petrolUsageCar, geocodePosition.getResponseData(), tankerKoenigData);
        if (fallback){
            return getFallBackData("RouteAPI Http response not 200");
        }

        ArrayList<SpritCalcBLData> spritCalcBLDataArrayList = getTop3CheapestStations();

        return spritCalcBLDataArrayList;
    }

    private boolean wegstreckeErmitteln(double petrolVolume, double petrolUsageCar, OpenCageData openCageDataPosition, List<TankerKoenigData> tankerKoenigData) {

        for (int i = 0; i < tankerKoenigData.size(); i++) {
//            OpenRouteCommand orCommand = new OpenRouteCommand(geocodePosition.getLat(), geocodePosition.getLng(), petrolStationDats.get(i).getGeographicLatitude(), petrolStationDats.get(i).getGeographicLongitude());
//            RouteData routeData = orCommand.execute();
//            if (orCommand.isCircuitBreakerOpen()) LOG.info("OpenRouteCommand Circuitbreaker open!");
//            if (orCommand.isResponseFromFallback())
//                LOG.info("OpenRouteCommand returned fallback! called with: " + Arrays.asList(geocodePosition.getLat(), geocodePosition.getLng(), petrolStationDats.get(i).getGeographicLatitude(), petrolStationDats.get(i).getGeographicLongitude()));
            OpenRouteConnector openRouteConnector = new OpenRouteConnector();
            ApiResponseWrapper<OpenRouteData> routeData = openRouteConnector.calculateDistance(openCageDataPosition.getLat(), openCageDataPosition.getLng(), tankerKoenigData.get(i).getGeographicLatitude(), tankerKoenigData.get(i).getGeographicLongitude());
            if (routeData.getStatus()!=200){
                return true;
            }
            // Ausrechnen vom Gesamtpreis
            double totalPrice;
            double travelcost = (petrolUsageCar * (routeData.getResponseData().getDistance() / 100000));
            totalPrice = (tankerKoenigData.get(i).getPrice() * petrolVolume) + travelcost;
            UUID uuid = UUID.randomUUID();
            SpritCalcBLData spritCalcBLData = new SpritCalcBLData(tankerKoenigData.get(i), routeData.getResponseData(), totalPrice, travelcost, uuid);
            apiRequestMultimapUUID.put(totalPrice, uuid);
            uuidApiRequestDataHashmap.put(uuid, spritCalcBLData);
        }
        return false;
    }

    private ArrayList<SpritCalcBLData> getTop3CheapestStations() {
        // die drei mit den günstigen Gesamtpreis in die Liste Aufsteigende nach Preis einfuegen
        ArrayList<SpritCalcBLData> spritCalcBLDataArrayList = new ArrayList<>();
        int i = 0;
        for (Map.Entry<Double, UUID> mapData : apiRequestMultimapUUID.entries()) {
            if (i++ >= 3) break;
            spritCalcBLDataArrayList.add(uuidApiRequestDataHashmap.get(mapData.getValue()));
        }
        return spritCalcBLDataArrayList;
    }

    public ArrayList<SpritCalcBLData> getFallBackData(String reason){
        TankerKoenigData tankerKoenigData1 = new TankerKoenigData(null,"FallbackDaten: "+reason + "   Freie Automatentankstelle","Freie Tankstelle","Musterstarsee","",49.148681640625,8.9788904190063,6.7,1.249,true,"14",75050);
        OpenRouteData openRouteData1 = new OpenRouteData(8569.1,695.1);
        SpritCalcBLData spritCalcBLData1 = new SpritCalcBLData(tankerKoenigData1, openRouteData1,10.79,0.685,UUID.randomUUID());
        ArrayList<SpritCalcBLData> spritCalcBLDataArrayList = new ArrayList<>();
        spritCalcBLDataArrayList.add(spritCalcBLData1);
        spritCalcBLDataArrayList.add(spritCalcBLData1);
        spritCalcBLDataArrayList.add(spritCalcBLData1);

        return spritCalcBLDataArrayList;
    }


}
