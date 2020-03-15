package org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.openCage;

import org.dhbw.mosbach.ai.spritCalc18A.ApiResponseWrapper;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

import java.util.Arrays;

public class OpenCageCommand extends HystrixCommand<ApiResponseWrapper<OpenCageData>> {
    private int streetNum;
    private String street;
    private int postalCode;
    private String city;
    private OpenCageConnector openCageClient;


    public OpenCageCommand(int streetNum, String street, int postalCode, String city) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("openCageCommands")).andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter().withCircuitBreakerErrorThresholdPercentage(30)
                        .withCircuitBreakerRequestVolumeThreshold(20).withCircuitBreakerSleepWindowInMilliseconds(3000)
                        .withExecutionTimeoutEnabled(true).withExecutionTimeoutInMilliseconds(3000))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(1)));

        this.streetNum = streetNum;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;

        openCageClient = new OpenCageConnector();
    }

    @Override
    protected ApiResponseWrapper<OpenCageData> run() throws Exception {
        final ApiResponseWrapper<OpenCageData> geocode = openCageClient.returnGeocodeForAddressInput(streetNum, street, postalCode, city);
        System.out.println("OpenCageCommand call, params:" + Arrays.asList(streetNum, street, postalCode, city) + " result: " + geocode.getResponseData().getLat()+ " " + geocode.getResponseData().getLng());
        return geocode;
    }

    @Override
    protected ApiResponseWrapper<OpenCageData> getFallback() {
        return new ApiResponseWrapper<OpenCageData>(200,"fallback",null);
    }


}
