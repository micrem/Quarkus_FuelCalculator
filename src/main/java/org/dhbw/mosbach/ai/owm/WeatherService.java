package org.dhbw.mosbach.ai.owm;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dhbw.mosbach.ai.model.TemperatureMeasurementUnit;
import org.dhbw.mosbach.ai.model.UnitConverter;
import org.dhbw.mosbach.ai.model.WeatherData;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.HystrixCommandMetrics.HealthCounts;

@Path("/weather")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WeatherService 
{
	private final Logger logger = Logger.getLogger("root");

	/* (non-Javadoc)
	 * @see org.dhbw.mosbach.ai.WeatherService#getVersion()
	 */
	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getVersion()
	{
		return "Test Hallo";
	}

	@GET
	@Path("/getMosbachWeatherToday/{testData}")
	public WeatherData getMosbachWeatherToday(@PathParam("testData") String testData) throws WeatherServiceException
	{
		final OwmClientCommand owmClientCommand = new OwmClientCommand(OpenWeatherMapClient.OWM_CITY_ID_MOSBACH, testData);
		final WeatherData weatherData = owmClientCommand.execute();

		logMetrics(owmClientCommand);

		return weatherData;
	}

	private void logMetrics(HystrixCommand<?> command)
	{
		final HystrixCommandMetrics metrics = command.getMetrics();

		final String status = command.isCircuitBreakerOpen() ? "Open" : "Closed";

		logger.info(String.format("owm Command Status: %s, Timeout: %b, Fallback: %b, Cached: %b", status,
				Boolean.valueOf(command.isResponseTimedOut()), Boolean.valueOf(command.isResponseFromFallback()),
				Boolean.valueOf(command.isResponseFromCache())));

		if (metrics != null)
		{
			final HealthCounts healthCounts = metrics.getHealthCounts();

			logger.info("Execution time mean: " + metrics.getExecutionTimeMean());
			logger.info("Total request: " + healthCounts.getTotalRequests());
			logger.info(String.format("Error count: %d (%d%%)", Long.valueOf(healthCounts.getErrorCount()),
					Integer.valueOf(healthCounts.getErrorPercentage())));
		}
	}

	@GET
	@Path("/converttemp/{source}/{target}/{value}")
	public float convertTemperature(@PathParam("source") TemperatureMeasurementUnit source,	
			@PathParam("target") TemperatureMeasurementUnit target, @PathParam("value") float value) throws WeatherServiceException
	{
		if ((source == null) || (target == null))
		{
			throw new WeatherServiceException("source and target must be specified");
		}

		return UnitConverter.convert(source, target, value);
	}
}
