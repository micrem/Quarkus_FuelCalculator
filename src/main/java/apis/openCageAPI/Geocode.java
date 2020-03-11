package apis.openCageAPI;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;


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
