package com.example.weather;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

class RandomWeatherGenerator implements WeatherConnector {

    private final double minimumTemperature;
    private final double maximumTemperature;

    RandomWeatherGenerator(double minimumTemperature, double maximumTemperature) {
        this.minimumTemperature = minimumTemperature;
        this.maximumTemperature = maximumTemperature;
    }

    @Override
    public Optional<Weather> weather(String location) {
        return Optional.of(Weather.of(location, generateRandomTemperatureInRange(minimumTemperature, maximumTemperature)));
    }

    private String generateRandomTemperatureInRange(double min, double max) {
        return String.valueOf(ThreadLocalRandom.current().nextDouble(min, max));
    }
}
