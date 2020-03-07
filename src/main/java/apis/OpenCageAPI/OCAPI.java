package apis.OpenCageAPI;

public class OCAPI {
    //https://api.opencagedata.com/geocode/v1/google-v3-json?address=10+Lohrtalweg%2C+74821+Mosbach+Deutschland&pretty=1&key=5c8ac5624e884cbc988ba9a5d3a23520

    String apiURL="https://api.opencagedata.com/geocode/v1/google-v3-json";
    String apikey="5c8ac5624e884cbc988ba9a5d3a23520";
    String country="Deutschland";//steht fest, da Tankerkoenig API nur fuer Deutschland vorgesehen ist

    public Geocode returnGeocodeForAddressInput(int streetNum, String street, int postalCode, String city){
        //PLZ ist in der Tat optional, API sucht nach Stadtnamen
        StringBuilder urlBuilder=new StringBuilder();
        urlBuilder.append(apiURL+"?key="+apikey);
        urlBuilder.append("&address="+streetNum+"+"+street+"%2C"+"+"+postalCode+"+"+city+country);
        return null;
    }
}
