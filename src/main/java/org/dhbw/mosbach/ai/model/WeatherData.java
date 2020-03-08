package org.dhbw.mosbach.ai.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class WeatherData implements Cloneable {
    private float temperatureCelsiusCurrent;
    private float temperatureCelsiusMax;
    private float temperatureCelsiusMin;
    private String testData;
    private Date timeStamp;

    public WeatherData(float temperatureCelsiusCurrent, float temperatureCelsiusMax, float temperatureCelsiusMin,
                       String testData, Date timeStamp) {
        this.temperatureCelsiusCurrent = temperatureCelsiusCurrent;
        this.temperatureCelsiusMax = temperatureCelsiusMax;
        this.temperatureCelsiusMin = temperatureCelsiusMin;
        this.testData = testData;
        this.timeStamp = timeStamp;
    }

    public WeatherData() {
        super();
    }

    public WeatherData copyToNew(String testData) {
        try {
            final WeatherData newWd = (WeatherData) this.clone();
            newWd.setTestData(testData);

            return newWd;
        } catch (final CloneNotSupportedException e) {
            // nothing to do here
            throw new IllegalStateException(e);
        }
    }

    public float getTemperatureCelsiusCurrent() {
        return temperatureCelsiusCurrent;
    }

    public void setTemperatureCelsiusCurrent(float temperatureCelsiusCurrent) {
        this.temperatureCelsiusCurrent = temperatureCelsiusCurrent;
    }

    public float getTemperatureCelsiusMax() {
        return temperatureCelsiusMax;
    }

    public void setTemperatureCelsiusMax(float temperatureCelsiusMax) {
        this.temperatureCelsiusMax = temperatureCelsiusMax;
    }

    public float getTemperatureCelsiusMin() {
        return temperatureCelsiusMin;
    }

    public void setTemperatureCelsiusMin(float temperatureCelsiusMin) {
        this.temperatureCelsiusMin = temperatureCelsiusMin;
    }

    public String getTestData() {
        return testData;
    }

    public void setTestData(String testData) {
        this.testData = testData;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return String.format(
                "WeatherData [temperatureCelsiusCurrent=%s, temperatureCelsiusMax=%s, temperatureCelsiusMin=%s, testData=%s, timeStamp=%s]",
                temperatureCelsiusCurrent, temperatureCelsiusMax, temperatureCelsiusMin, testData, timeStamp);
    }
}
