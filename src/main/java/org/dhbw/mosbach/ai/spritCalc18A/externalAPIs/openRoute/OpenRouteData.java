package org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.openRoute;

public class OpenRouteData {
    private final double distance;
    private final double duration;

    public OpenRouteData(double distance, double duration) {
        this.distance = distance;
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public double getDuration() {
        return duration;
    }
}
