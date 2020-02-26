package org.dhbw.mosbach.ai.owm;

public class WeatherServiceException extends Exception
{
	/**
	 *
	 */
	private static final long serialVersionUID = -8244637279526395211L;

	public WeatherServiceException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public WeatherServiceException(String message)
	{
		super(message);
	}

	public WeatherServiceException(Throwable cause)
	{
		super(cause);
	}
}
