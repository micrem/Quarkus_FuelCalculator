package apis.petrolApi;

import apis.ApiResponseWrapper;
import org.json.JSONArray;

import java.util.List;

public class testMain {
    public static void main(String[] args) {
        PetrolStationApi petrolStationApiMosbach = new PetrolStationApi();
        ApiResponseWrapper<List<PetrolStationDat>> a = petrolStationApiMosbach.search(49.337470, 9.120130, PetrolTyp.e10);
        if(a.getStatus()!=200){
            System.out.println("error:"+a.getMessage());
        }
        System.out.println(a.getResponseData());

    }
}