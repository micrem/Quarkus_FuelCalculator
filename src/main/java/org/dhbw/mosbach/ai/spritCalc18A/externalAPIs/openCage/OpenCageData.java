package org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.openCage;

public class OpenCageData {
    private final double lng;//Laenge
    private final double lat;//Breite

    public OpenCageData(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }


}
