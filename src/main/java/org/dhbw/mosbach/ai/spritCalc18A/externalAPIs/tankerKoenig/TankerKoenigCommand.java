package org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.tankerKoenig;

import org.dhbw.mosbach.ai.spritCalc18A.ApiResponseWrapper;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

import java.util.Arrays;
import java.util.List;

public class TankerKoenigCommand extends HystrixCommand<ApiResponseWrapper<List<TankerKoenigData>> > {
    double geographicLatitude;
    double geographicLongitude;
    PetrolTyp petrolTyp;

    private TankerKoenigConnector tankerKoenigConnector;

    public TankerKoenigCommand(double geographicLatitude, double geographicLongitude, PetrolTyp petrolTyp) {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("openCageCommands")).andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter().withCircuitBreakerErrorThresholdPercentage(30)
                        .withCircuitBreakerRequestVolumeThreshold(5).withCircuitBreakerSleepWindowInMilliseconds(10000)
                        .withExecutionTimeoutEnabled(true).withExecutionTimeoutInMilliseconds(10000))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(1)));

        tankerKoenigConnector = new TankerKoenigConnector();
        this.geographicLatitude = geographicLatitude;
        this.geographicLongitude = geographicLongitude;
        this.petrolTyp = petrolTyp;
    }

    @Override
    protected ApiResponseWrapper<List<TankerKoenigData>> run() throws Exception {
        ApiResponseWrapper<List<TankerKoenigData>> ret = tankerKoenigConnector.search(geographicLatitude, geographicLongitude, petrolTyp);
        System.out.println("PetrolStationCommand call, params:" + Arrays.asList(geographicLatitude, geographicLongitude, petrolTyp) + " result: " + ret.getResponseData().size() + "found");
        return ret;
    }

    @Override
    protected ApiResponseWrapper<List<TankerKoenigData>>  getFallback() {
        return new ApiResponseWrapper<>(200, "fallback", null);
    }
}
