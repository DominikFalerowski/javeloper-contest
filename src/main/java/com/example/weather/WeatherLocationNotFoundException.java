package com.example.weather;

class WeatherLocationNotFoundException extends RuntimeException {

    public WeatherLocationNotFoundException(String location) {
        super("Can't find weather for given location = " + location);
    }

}
