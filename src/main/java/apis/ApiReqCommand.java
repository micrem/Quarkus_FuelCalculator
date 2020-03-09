package apis;

import apis.openRouteAPI.RouteAPI;
import apis.openRouteAPI.RouteData;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

public class ApiReqCommand {

//    private double startLat;
//    private double startLng;
//    private double endLat;
//    private double endLng;
//
//    private RouteAPI openRouteClient;
//
//
//    public OpenRouteCommand(double startLat, double startLng, double endLat, double endLng) {
//        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("openCageCommands")).andCommandPropertiesDefaults(
//                HystrixCommandProperties.Setter().withCircuitBreakerErrorThresholdPercentage(30)
//                        .withCircuitBreakerRequestVolumeThreshold(5).withCircuitBreakerSleepWindowInMilliseconds(3000)
//                        .withExecutionTimeoutEnabled(true).withExecutionTimeoutInMilliseconds(500))
//                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(2)));
//        this.startLat = startLat;
//        this.startLng = startLng;
//        this.endLat = endLat;
//        this.endLng = endLng;
//        openRouteClient = new RouteAPI();
//    }
//
//    @Override
//    protected RouteData run() throws Exception {
//        return openRouteClient.calculateDistance( startLat,  startLng,  endLat,  endLng);
//    }

}
