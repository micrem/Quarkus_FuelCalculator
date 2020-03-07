//package petrolAPI;
//
//import com.google.common.cache.CacheBuilder;
//import com.google.common.cache.CacheLoader;
//import com.google.common.cache.LoadingCache;
//import org.dhbw.mosbach.ai.model.TemperatureMeasurementUnit;
//import org.dhbw.mosbach.ai.model.UnitConverter;
//import org.dhbw.mosbach.ai.model.WeatherData;
//import org.dhbw.mosbach.ai.owm.ServiceDiscoveryHelper;
//
//import javax.json.Json;
//import javax.json.JsonArray;
//import javax.json.JsonObject;
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.io.InputStream;
//import java.util.Date;
//import java.util.concurrent.TimeUnit;
//
//
//public class PetrolMapClient
//{
//	/**
//	 *
//	 */
//	public static final String API_KEY = "2cf81686-a985-f980-7616-f299465739b1";
//
////	/**
////	 * Important: do not use the original service here, since the openweathermap
////	 * API shall not be called more often than once within 10 minutes.
////	 */
////	private static final String OWM_API_2_5_URL = /*
////	 * "http://10.50.12.187:20480/weather/"
////	 */ "http://10.50.15.51:20480/weather/";
////
////	public static final int OWM_CITY_ID_MOSBACH = 2869120;
////
//	private final WebTarget wt;
//
//	/**
//	 * Caching implementation. For an overview of guava, see
//	 * https://github.com/google/guava/wiki
//	 */
//	private LoadingCache<Integer, WeatherData> weatherDataCache;
//
//	/**
//	 * Weather data cache expiration time in seconds.
//	 */
//	private int expirationTimeSecs = 5;
//
//	public PetrolMapClient(String apiKey)
//	{
//		final Client client = ClientBuilder.newClient();
//		wt = client.target(getUrl()).queryParam("appid", apiKey);
//
//		initCache();
//	}
//
//	private String getUrl()
//	{
//		final String resolvedUrl = ServiceDiscoveryHelper.getServiceUrl("/weather");
//
//		return resolvedUrl != null ? resolvedUrl : OWM_API_2_5_URL;
//	}
//
//	/**
//	 * Initializes the weather cache. Here, a cache loader instance is created
//	 * that invokes {@link #requestCurrentWeatherData(int, String)}.
//	 */
//	private void initCache()
//	{
//		weatherDataCache = CacheBuilder.newBuilder().expireAfterWrite(expirationTimeSecs, TimeUnit.SECONDS)
//				.build(new CacheLoader<Integer, WeatherData>()
//				{
//					@Override
//					public WeatherData load(Integer cityId) throws Exception
//					{
//						return requestCurrentWeatherData(cityId, "");
//					}
//				});
//	}
//
//	/**
//	 * Returns temperature in Celsius.
//	 *
//	 * @param jsonObject
//	 *          json object
//	 * @param name
//	 *          property of the given json object that represents the temperature
//	 *          in Kelvin
//	 * @return temperature in Celsius
//	 */
//	private float getTemperature(JsonObject jsonObject, String name)
//	{
//		final float tempInKelvin = (float) jsonObject.getJsonNumber(name).doubleValue();
//		return UnitConverter.convert(TemperatureMeasurementUnit.Kelvin, TemperatureMeasurementUnit.Celsius, tempInKelvin);
//	}
//
//	/**
//	 * Returns cached weather data for the given city, or current data if cached
//	 * value has expired.
//	 *
//	 * @param cityId
//	 * @param testData
//	 * @return {@link WeatherData}
//	 */
//	public WeatherData getCurrentWeatherData(int cityId, String testData)
//	{
//		return weatherDataCache.getUnchecked(Integer.valueOf(cityId)).copyToNew(testData);
//	}
//
//	/**
//	 * Does the actual REST request.
//	 *
//	 * @param cityId
//	 * @param testData
//	 * @return
//	 */
//	private WeatherData requestCurrentWeatherData(int cityId, String testData)
//	{
//		final Response response = wt.queryParam("id", cityId).request(MediaType.APPLICATION_JSON).get();
//		final JsonObject jsonObject = Json.createReader(response.readEntity(InputStream.class)).readObject();
//
//		return jsonResponseToWeatherData(jsonObject, testData);
//	}
//
//	/**
//	 * Converts a single location object to {@link WeatherData}.
//	 *
//	 * @param jsonObject
//	 * @param testData
//	 * @return {@link WeatherData}
//	 */
//	private WeatherData jsonResponseToWeatherData(final JsonObject jsonObject, String testData)
//	{
//		final JsonObject mainData = jsonObject.getJsonObject("main");
//
//		final float tempCelsiusCurrent = getTemperature(mainData, "temp");
//		final float tempCelsiusMax = getTemperature(mainData, "temp_max");
//		final float tempCelsiusMin = getTemperature(mainData, "temp_min");
//
//		return new WeatherData(tempCelsiusCurrent, tempCelsiusMax, tempCelsiusMin,
//				String.format("Mosbach Weather Service: %s", testData), new Date());
//	}
//
//	private WeatherData requestCurrentWeatherData(String cityName, String testData)
//	{
//		final Response response = wt.queryParam("q", cityName).request(MediaType.APPLICATION_JSON).get();
//		final JsonObject jsonObject = Json.createReader(response.readEntity(InputStream.class)).readObject();
//
//		final JsonArray resultList = jsonObject.getJsonArray("list");
//
//		if (!resultList.isEmpty())
//		{
//			return jsonResponseToWeatherData(resultList.getJsonObject(0), testData);
//		}
//		else
//		{
//			return null;
//		}
//	}
//
//	public int getExpirationTimeMin()
//	{
//		return expirationTimeSecs;
//	}
//
//	/**
//	 * Sets cache expiration time in minutes. When this method is invoked, all
//	 * cached values will be discarded and the cache will be reinitialized.
//	 *
//	 * @param expirationTimeMin
//	 */
//	public void setExpirationTimeMin(int expirationTimeMin)
//	{
//		this.expirationTimeSecs = expirationTimeMin;
//		initCache();
//	}
//}
