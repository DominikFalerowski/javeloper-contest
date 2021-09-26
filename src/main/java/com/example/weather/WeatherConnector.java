package com.example.weather;

import java.util.Optional;

interface WeatherConnector {

    Optional<Weather> weather(String location);

}
