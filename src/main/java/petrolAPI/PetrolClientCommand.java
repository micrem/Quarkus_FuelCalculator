///**
// *
// */
//package petrolAPI;
//
//import java.util.Date;
//
//import org.dhbw.mosbach.ai.model.WeatherData;
//
//import com.netflix.hystrix.HystrixCommand;
//import com.netflix.hystrix.HystrixCommandGroupKey;
//import com.netflix.hystrix.HystrixCommandProperties;
//import com.netflix.hystrix.HystrixThreadPoolProperties;
//import org.dhbw.mosbach.ai.owm.OpenWeatherMapClient;
//
///**
// * @author Alexander.Auch
// *
// */
//public class PetrolClientCommand extends HystrixCommand<PetrolStationData>
//{
//	private final double geographischeBreite;
//	private final double geographischeLaenge;
//
//	private final OpenWeatherMapClient owmClient = new OpenWeatherMapClient(OpenWeatherMapClient.API_KEY);
//
//	public PetrolClientCommand(double geographischeBreite, double geographischeLaenge)
//	{
//		// See https://github.com/Netflix/Hystrix/wiki/Configuration
//
//
//		//** Erstmal nur timeout nach 500 ms gestzt
//
//		// CommandProperties:
//		// Open the Circuit when 30% of requests result in an error.
//		// Set minimum no. of failing requests within a window to trip the circuit.
//		// Set request reject time if circuit has been tripped -> after this time,
//		// the functionality of the service is tested again.
//		// Enable Execution timeout.
//		// Set timeout to 500ms.
//
//		// ThreadPoolProperties:
//		// Set thread pool number to 2.
//
//		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("weatherCommands")).andCommandPropertiesDefaults(
//				HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(500)));
//
//		this.geographischeBreite = geographischeBreite;
//		this.geographischeLaenge = geographischeLaenge;
//	}
//
//	/* (non-Javadoc)
//	 * @see com.netflix.hystrix.HystrixCommand#run()
//	 */
//	@Override
//	protected PetrolStationData run() throws Exception
//	{
//		return owmClient.getCurrentWeatherData(cityId, testData);
//	}
//
//	@Override
//	protected PetrolStationData getFallback()
//	{
//		return new WeatherData(0f, 0f, 0f, "Fallback Data!", new Date());
//	}
//}