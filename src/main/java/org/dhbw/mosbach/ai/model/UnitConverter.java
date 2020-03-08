package org.dhbw.mosbach.ai.model;

import com.google.common.base.Preconditions;

public final class UnitConverter {
    public static float convert(TemperatureMeasurementUnit source, TemperatureMeasurementUnit target, float value) {
        Preconditions.checkNotNull(source);
        Preconditions.checkNotNull(target);

        return target.fromKelvin(source.toKelvin(value));
    }
}
