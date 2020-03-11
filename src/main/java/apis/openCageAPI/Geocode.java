package apis.openCageAPI;

public class Geocode {
    private final double lng;//Laenge
    private final double lat;//Breite

    public Geocode(double lng, double lat) {
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
