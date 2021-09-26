package com.example.weather;

import java.util.Objects;
import java.util.StringJoiner;

class Weather {

    private static final int SCALE = 1;

    private final Location location;
    private final Temperature temperature;

    private Weather(String location, String temperature) {
        this.location = Location.of(location);
        this.temperature = Temperature.ofCelsius(temperature, SCALE);
    }

    static Weather of(String location, String temperature) {
        return new Weather(location, temperature);
    }

    String getLocationName() {
        return location.getName();
    }

    String getTemperature() {
        return temperature.celsius().toString();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Weather.class.getSimpleName() + "[", "]")
                .add("location='" + location + "'")
                .add("temperature=" + temperature)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Objects.equals(location, weather.location) && Objects.equals(temperature, weather.temperature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, temperature);
    }
}
