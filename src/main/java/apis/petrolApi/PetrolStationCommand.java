package apis.petrolApi;

import apis.ApiResponseWrapper;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

import java.util.Arrays;
import java.util.List;

public class PetrolStationCommand extends HystrixCommand<ApiResponseWrapper<List<PetrolStationDat>> > {
    double geographicLatitude;
    double geographicLongitude;
    PetrolTyp petrolTyp;

    private PetrolStationApi petrolStationApi;

    public PetrolStationCommand(double geographicLatitude, double geographicLongitude, PetrolTyp petrolTyp) {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("openCageCommands")).andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter().withCircuitBreakerErrorThresholdPercentage(30)
                        .withCircuitBreakerRequestVolumeThreshold(5).withCircuitBreakerSleepWindowInMilliseconds(10000)
                        .withExecutionTimeoutEnabled(true).withExecutionTimeoutInMilliseconds(10000))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(1)));

        petrolStationApi = new PetrolStationApi();
        this.geographicLatitude = geographicLatitude;
        this.geographicLongitude = geographicLongitude;
        this.petrolTyp = petrolTyp;
    }

    @Override
    protected ApiResponseWrapper<List<PetrolStationDat>> run() throws Exception {
        ApiResponseWrapper<List<PetrolStationDat>> ret = petrolStationApi.search(geographicLatitude, geographicLongitude, petrolTyp);
        System.out.println("PetrolStationCommand call, params:" + Arrays.asList(geographicLatitude, geographicLongitude, petrolTyp) + " result: " + ret.getResponseData().size() + "found");
        return ret;
    }

    @Override
    protected ApiResponseWrapper<List<PetrolStationDat>>  getFallback() {
        return new ApiResponseWrapper<>(200, "fallback", null);
    }
}
