package org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.tankerKoenig;

import org.dhbw.mosbach.ai.spritCalc18A.ApiResponseWrapper;

import java.util.List;

public class TankerKoenigTest {
    public static void main(String[] args) {
        TankerKoenigConnector tankerKoenigConnectorMosbach = new TankerKoenigConnector();
        ApiResponseWrapper<List<TankerKoenigData>> a = tankerKoenigConnectorMosbach.search(49.337470, 9.120130, PetrolTyp.e10);
        if(a.getStatus()!=200){
            System.out.println("error:"+a.getMessage());
        }
        System.out.println(a.getResponseData());

    }
}