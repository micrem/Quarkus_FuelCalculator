package org.dhbw.mosbach.ai.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WeatherData implements Cloneable
{
	private float temperatureCelsiusCurrent;
	private float temperatureCelsiusMax;
	private float temperatureCelsiusMin;
	private String testData;
	private Date timeStamp;

	public WeatherData(float temperatureCelsiusCurrent, float temperatureCelsiusMax, float temperatureCelsiusMin,
			String testData, Date timeStamp)
	{
		this.temperatureCelsiusCurrent = temperatureCelsiusCurrent;
		this.temperatureCelsiusMax = temperatureCelsiusMax;
		this.temperatureCelsiusMin = temperatureCelsiusMin;
		this.testData = testData;
		this.timeStamp = timeStamp;
	}

	public WeatherData()
	{
		super();
	}

	public WeatherData copyToNew(String testData)
	{
		try
		{
			final WeatherData newWd = (WeatherData) this.clone();
			newWd.setTestData(testData);

			return newWd;
		}
		catch (final CloneNotSupportedException e)
		{
			// nothing to do here
			throw new IllegalStateException(e);
		}
	}

	public void setTemperatureCelsiusCurrent(float temperatureCelsiusCurrent)
	{
		this.temperatureCelsiusCurrent = temperatureCelsiusCurrent;
	}

	public void setTemperatureCelsiusMax(float temperatureCelsiusMax)
	{
		this.temperatureCelsiusMax = temperatureCelsiusMax;
	}

	public void setTemperatureCelsiusMin(float temperatureCelsiusMin)
	{
		this.temperatureCelsiusMin = temperatureCelsiusMin;
	}

	public void setTestData(String testData)
	{
		this.testData = testData;
	}

	public void setTimeStamp(Date timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	public float getTemperatureCelsiusCurrent()
	{
		return temperatureCelsiusCurrent;
	}

	public float getTemperatureCelsiusMax()
	{
		return temperatureCelsiusMax;
	}

	public float getTemperatureCelsiusMin()
	{
		return temperatureCelsiusMin;
	}

	public String getTestData()
	{
		return testData;
	}

	public Date getTimeStamp()
	{
		return timeStamp;
	}

	@Override
	public String toString()
	{
		return String.format(
				"WeatherData [temperatureCelsiusCurrent=%s, temperatureCelsiusMax=%s, temperatureCelsiusMin=%s, testData=%s, timeStamp=%s]",
				temperatureCelsiusCurrent, temperatureCelsiusMax, temperatureCelsiusMin, testData, timeStamp);
	}
}
