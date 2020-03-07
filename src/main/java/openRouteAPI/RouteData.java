package openRouteAPI;

public class RouteData {
    private final double distance;
    private final double duration;

    public RouteData(double distance, double duration) {
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
