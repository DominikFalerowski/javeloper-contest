package com.example.weather;

class WeatherService {

    private final WeatherConnector weatherConnector;
    private final MailSender mailSender;
    private final WeatherCache weatherCache;

    WeatherService(WeatherConnector weatherConnector, MailSender mailSender, WeatherCache weatherCache) {
        this.weatherConnector = weatherConnector;
        this.mailSender = mailSender;
        this.weatherCache = weatherCache;
    }

    void processWeather(Location location) {
        var locationName = location.getName();
        var weather = weatherConnector.weather(locationName)
                .orElseThrow(() -> new LocationNotPresentException(locationName));

        weatherCache.add(weather);
        mailSender.sendMail(weather);
    }

    void logCache() {
        System.out.println("Logging cache -> ");
        var cachedWeathers = weatherCache.cachedWeather();
        cachedWeathers.forEach(weather -> System.out.println("Cached value = " + weather));
    }

}
