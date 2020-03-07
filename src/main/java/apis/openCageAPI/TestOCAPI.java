package apis.openCageAPI;

import java.io.IOException;

public class TestOCAPI {
    public static void main(String[] args) throws IOException {
        OCAPI openCageAPI=new OCAPI();
        System.out.println(openCageAPI.returnGeocodeForAddressInput(10,"Lohrtalweg",78421, "Mosbach").getLat());
    }
}
