package com.example.weather;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class WeatherCache {

    private final Map<String, Weather> cache = new ConcurrentHashMap<>();

    void add(Weather weather) {
        cache.put(weather.getLocationName(), weather);
    }

    List<Weather> cachedWeather() {
        return new ArrayList<>(cache.values());
    }
}
