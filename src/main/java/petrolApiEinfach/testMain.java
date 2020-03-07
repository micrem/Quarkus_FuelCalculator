package petrolApiEinfach;

import org.json.JSONArray;

public class testMain {
    public static void main(String[] args) {
        PetrolStationApi petrolStationApiMosbach = new PetrolStationApi(49.337470,9.120130,PetrolTyp.e10);
        petrolStationApiMosbach.search();
        JSONArray jarr = petrolStationApiMosbach.returnObj.getJSONArray("stations");
        System.out.println(jarr.getJSONObject(0));
    }
}