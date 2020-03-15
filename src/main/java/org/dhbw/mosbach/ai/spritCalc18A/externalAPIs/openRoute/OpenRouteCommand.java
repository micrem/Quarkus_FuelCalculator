package org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.openRoute;

import org.dhbw.mosbach.ai.spritCalc18A.ApiResponseWrapper;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

import java.util.Arrays;

public class OpenRouteCommand extends HystrixCommand<ApiResponseWrapper<OpenRouteData>> {
    private double startLat;
    private double startLng;
    private double endLat;
    private double endLng;

    private OpenRouteConnector openRouteClient;


    public OpenRouteCommand(double startLat, double startLng, double endLat, double endLng) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("openCageCommands")).andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter().withCircuitBreakerErrorThresholdPercentage(30)
                        .withCircuitBreakerRequestVolumeThreshold(5).withCircuitBreakerSleepWindowInMilliseconds(3000)
                        .withExecutionTimeoutEnabled(true).withExecutionTimeoutInMilliseconds(3000))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(2)));
        this.startLat = startLat;
        this.startLng = startLng;
        this.endLat = endLat;
        this.endLng = endLng;
        openRouteClient = new OpenRouteConnector();
    }

    @Override
    protected ApiResponseWrapper<OpenRouteData> run() throws Exception {
        ApiResponseWrapper<OpenRouteData> routeData = openRouteClient.calculateDistance(startLat, startLng, endLat, endLng);
        System.out.println("OpenRouteCommand call, params:" + Arrays.asList(startLat, startLng, endLat, endLng) + " result: " + routeData.getResponseData().getDistance());
        return routeData;
    }

    @Override
    protected ApiResponseWrapper<OpenRouteData> getFallback() {
        return new ApiResponseWrapper<>(200,"fallback",null);
    }

}
