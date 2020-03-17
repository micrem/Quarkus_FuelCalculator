package org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.tankerKoenig;

import org.dhbw.mosbach.ai.spritCalc18A.ApiResponseWrapper;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

import java.util.Arrays;
import java.util.List;

/**
 * Ein mittels Hysterix umgesetzter Circuit-Breaker wurde zunächst mit einer Kapselung der Service-Aufrufe in Command-Objekte implementiert,
 * musste aber wieder entfernt werden. Aus nicht geklärtem Grund wurde von dem Command-Object immer das Fallback-Ergebnis empfangen,
 * obwohl der Aufruf selbst korrekt verlief und intern die erwarteten Ergebnisse lieferte. Ein möglicher Grund dafür wäre die asynchrone Verarbeitung der Eingaben innerhalb des Hysterix-Frameworks
 */
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
