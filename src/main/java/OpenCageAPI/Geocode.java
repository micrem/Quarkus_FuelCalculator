package OpenCageAPI;

public class Geocode {
    private final String lng;//Laenge
    private final String lat;//Breite

    public Geocode(String lng, String lat){
        this.lng=lng;
        this.lat=lat;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }
}
