package org.dhbw.mosbach.ai.spritCalc18A;

import org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.tankerKoenig.TankerKoenigData;
import org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.openRoute.OpenRouteData;

import java.util.UUID;

public class SpritCalcBLData {
    private final TankerKoenigData tankerKoenigData;
    private final OpenRouteData openRouteData;
    private final double completePrice;
    private final double travelcost;
    private final UUID uuid;
    private String message;


    public SpritCalcBLData(TankerKoenigData tankerKoenigData, OpenRouteData openRouteData, double completePrice, double travelcost, UUID uuid) {
        this.tankerKoenigData = tankerKoenigData;
        this.openRouteData = openRouteData;
        this.completePrice = completePrice;
        this.travelcost = travelcost;
        this.uuid = uuid;
    }

    public TankerKoenigData getTankerKoenigData() {
        return tankerKoenigData;
    }

    public OpenRouteData getOpenRouteData() {
        return openRouteData;
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
