/**
 *
 */
package org.dhbw.mosbach.ai.owm;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import org.dhbw.mosbach.ai.model.WeatherData;

import java.util.Date;

/**
 * @author Alexander.Auch
 *
 */
public class OwmClientCommand extends HystrixCommand<WeatherData>
{
	private final int cityId;
	private final String testData;

	private final OpenWeatherMapClient owmClient = new OpenWeatherMapClient(OpenWeatherMapClient.API_KEY);

	public OwmClientCommand(int cityId, String testData)
	{
		// See https://github.com/Netflix/Hystrix/wiki/Configuration

		// CommandProperties:
		// Open the Circuit when 30% of requests result in an error.
		// Set minimum no. of failing requests within a window to trip the circuit.
		// Set request reject time if circuit has been tripped -> after this time,
		// the functionality of the service is tested again.
		// Enable Execution timeout.
		// Set timeout to 500ms.

		// ThreadPoolProperties:
		// Set thread pool number to 2.

		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("weatherCommands")).andCommandPropertiesDefaults(
				HystrixCommandProperties.Setter().withCircuitBreakerErrorThresholdPercentage(30)
				.withCircuitBreakerRequestVolumeThreshold(5).withCircuitBreakerSleepWindowInMilliseconds(3000)
				.withExecutionTimeoutEnabled(true).withExecutionTimeoutInMilliseconds(500))
				.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(2)));

		this.cityId = cityId;
		this.testData = testData;
	}

	/* (non-Javadoc)
	 * @see com.netflix.hystrix.HystrixCommand#run()
	 */
	@Override
	protected WeatherData run() throws Exception
	{
		return owmClient.getCurrentWeatherData(cityId, testData);
	}

	@Override
	protected WeatherData getFallback()
	{
		return new WeatherData(0f, 0f, 0f, "Fallback Data!", new Date());
	}
}
