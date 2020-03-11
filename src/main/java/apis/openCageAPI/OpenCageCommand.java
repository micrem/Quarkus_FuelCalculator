package apis.openCageAPI;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

import java.util.Arrays;

public class OpenCageCommand extends HystrixCommand<Geocode> {
    private int streetNum;
    private String street;
    private int postalCode;
    private String city;
    private OCAPI openCageClient;


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

        openCageClient = new OCAPI();
    }

    @Override
    protected Geocode run() throws Exception {
        final Geocode geocode = openCageClient.returnGeocodeForAddressInput(streetNum, street, postalCode, city);
        System.out.println("OpenCageCommand call, params:" + Arrays.asList(streetNum, street, postalCode, city) + " result: " + geocode.getLat() + " " + geocode.getLng());
        return geocode;
    }

    @Override
    protected Geocode getFallback() {
        return new Geocode(0, 0);
    }


}
