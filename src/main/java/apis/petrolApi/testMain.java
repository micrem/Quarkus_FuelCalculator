package apis.petrolApi;

import org.json.JSONArray;

public class testMain {
    public static void main(String[] args) {
        PetrolStationApi petrolStationApiMosbach = new PetrolStationApi();
        petrolStationApiMosbach.search(49.337470, 9.120130, PetrolTyp.e10);
        JSONArray jarr = petrolStationApiMosbach.returnObj.getJSONArray("stations");
        System.out.println("Test");
        System.out.println(jarr.getJSONObject(0));
    }
}