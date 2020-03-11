package apis.petrolApi;

import apis.openRouteAPI.RouteAPI;
import apis.openRouteAPI.RouteData;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

import java.util.Arrays;
import java.util.List;

public class PetrolStationCommand extends HystrixCommand<List<PetrolStationDat>>{
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
        this.geographicLatitude=geographicLatitude;
        this.geographicLongitude=geographicLongitude;
        this.petrolTyp=petrolTyp;
    }

    @Override
    protected List<PetrolStationDat> run() throws Exception {
        final List<PetrolStationDat> search = petrolStationApi.search(geographicLatitude, geographicLongitude, petrolTyp);
        System.out.println("PetrolStationCommand call, params:"+ Arrays.asList(geographicLatitude, geographicLongitude, petrolTyp) + " result: "+search.size()+"found");
        return search;
    }

    @Override
    protected List<PetrolStationDat> getFallback() {
        return Arrays.asList(new PetrolStationDat());
    }
}
