package org.dhbw.mosbach.ai.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "temperatureMeasurementUnit")
@XmlEnum
public enum TemperatureMeasurementUnit
{
	Celsius
	{
		private static final float KELVIN_0_CELSIUS = 273.15F;

		@Override
		protected float fromKelvin(float value)
		{
			return value - KELVIN_0_CELSIUS;
		}

		@Override
		protected float toKelvin(float value)
		{
			return value + KELVIN_0_CELSIUS;
		}
	},

	Kelvin
	{
		@Override
		protected float fromKelvin(float value)
		{
			return value;
		}

		@Override
		protected float toKelvin(float value)
		{
			return value;
		}
	},

	Fahrenheit
	{
		private static final float FACT_FH_TO_K = 1.8F;
		private static final float KELVIN_0_FH = 459.67F;

		@Override
		protected float fromKelvin(float value)
		{
			return value * FACT_FH_TO_K - KELVIN_0_FH;
		}

		@Override
		protected float toKelvin(float value)
		{
			return (value + KELVIN_0_FH) / FACT_FH_TO_K;
		}
	};


	/**
	 * Converts from Kelvin to this unit.
	 *
	 * @param value
	 *          in Kelvin
	 * @return value converted to this unit
	 */
	protected abstract float fromKelvin(float value);


	/**
	 * Converts from this unit to Kelvin.
	 *
	 * @param value
	 *          in this unit
	 * @return value converted to Kelvin
	 */
	protected abstract float toKelvin(float value);
}
