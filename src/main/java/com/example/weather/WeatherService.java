package com.example.weather;

class WeatherService {

    private final WeatherConnector weatherConnector;
    private final LocationProvider locationProvider;
    private final MailSender mailSender;
    private final WeatherCache weatherCache;

    WeatherService(WeatherConnector weatherConnector, LocationProvider locationProvider, MailSender mailSender, WeatherCache weatherCache) {
        this.weatherConnector = weatherConnector;
        this.locationProvider = locationProvider;
        this.mailSender = mailSender;
        this.weatherCache = weatherCache;
    }

    void processWeather() {
        var location = locationProvider.location();
        var weather = weatherConnector.weather(location)
                .orElseThrow(() -> new LocationNotPresentException(location));

        weatherCache.add(weather);
        mailSender.sendMail(weather);
    }

    void logCache() {
        System.out.println("Logging cache -> ");
        var cachedWeathers = weatherCache.cachedWeather();
        cachedWeathers.forEach(weather -> System.out.println("Cached value = " + weather));
    }

    int numberOfTasks(int factor) {
        return locationProvider.getSize() * factor;
    }
}
