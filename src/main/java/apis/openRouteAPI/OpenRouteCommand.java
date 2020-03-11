package apis.openRouteAPI;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

import java.util.Arrays;

public class OpenRouteCommand extends HystrixCommand<RouteData> {
    private double startLat;
    private double startLng;
    private double endLat;
    private double endLng;

    private RouteAPI openRouteClient;


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
        openRouteClient = new RouteAPI();
    }

    @Override
    protected RouteData run() throws Exception {
        final RouteData routeData = openRouteClient.calculateDistance(startLat, startLng, endLat, endLng);
        System.out.println("OpenRouteCommand call, params:" + Arrays.asList(startLat, startLng, endLat, endLng) + " result: " + routeData.getDistance());
        return routeData;
    }

    @Override
    protected RouteData getFallback() {
        return new RouteData(-1, -1);
    }

}
