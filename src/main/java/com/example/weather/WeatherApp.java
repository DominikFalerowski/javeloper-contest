package com.example.weather;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class WeatherApp {

    public static void main(String[] args) throws InterruptedException {
        List<Callable<Object>> tasks = new ArrayList<>();
        var locationProvider = new RandomLocationGenerator();
        var weatherService = new WeatherService(new RandomWeatherGenerator(-100.0, 100.0), new LoggingMailSender(), new WeatherCache());
        var executor = Executors.newFixedThreadPool(2);
        var numberOfTasksToRun = locationProvider.getSize() * 20;

        for (int i = 0; i < numberOfTasksToRun; i++) {
            tasks.add(Executors.callable(() -> weatherService.processWeather(Location.of(locationProvider.location()))));
        }

        executor.invokeAll(tasks);
        weatherService.logCache();

        executor.shutdown();
    }

}

