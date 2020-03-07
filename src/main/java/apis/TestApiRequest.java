package apis;

import apis.petrolApi.PetrolTyp;

import java.io.IOException;
import java.util.ArrayList;

public class TestApiRequest {

    public static void main(String[] args) throws IOException {
        ApiRequests apiRequests = new ApiRequests();
        ArrayList<ApiRequestData> apiRequestData = apiRequests.apiSearchStart("Lohrtalweg",10,78421,"Mosbach", PetrolTyp.e10,20.5,10);
        System.out.println(apiRequestData);
        System.out.println(apiRequestData.get(0).getPetrolStationDat().getName());

    }
}
