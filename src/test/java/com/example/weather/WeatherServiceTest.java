package com.example.weather;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WeatherServiceTest {

    @Test
    void whenProcessWeather_thenShouldAddWeatherToCache() {
        var locationName = "Gdynia";
        var temperature = "32.9";
        var cache = new WeatherCache();
        WeatherService weatherService = new WeatherService(weatherConnector -> Optional.of(Weather.of(locationName, temperature)), new MailSenderFake(), cache);

        weatherService.processWeather(Location.of(locationName));

        assertThat(cache.cachedWeather()).contains(Weather.of(locationName, temperature));
    }

    @Test
    void givenAlreadyCachedWeather_whenProcessWeather_thenShouldReplacePreviousWeather() {
        var locationName = "Kielce";
        var temperature = "32.9";
        var cache = new WeatherCache();
        cache.add(Weather.of(locationName, temperature));
        var updatedTemperature = "-10.9";
        WeatherService weatherService = new WeatherService(weatherConnector -> Optional.of(Weather.of(locationName, updatedTemperature)), new MailSenderFake(), cache);

        weatherService.processWeather(Location.of(locationName));

        assertThat(cache.cachedWeather()).contains(Weather.of(locationName, updatedTemperature));
    }

    @Test
    void whenProcessWeather_thenShouldSendMailWithWeather() {
        var locationName = "Sopot";
        var temperature = "5.1";
        var cache = new WeatherCache();
        var mailSender = new MailSenderFake();
        WeatherService weatherService = new WeatherService(weatherConnector -> Optional.of(Weather.of(locationName, temperature)), mailSender, cache);

        weatherService.processWeather(Location.of(locationName));

        assertThat(mailSender.sentWeather).isEqualTo(Weather.of(locationName, temperature));
    }

    @Test
    void givenWeatherConnectorDoesNotHaveLocation_whenProcessWeather_thenShouldThrowLocationNotPresentException() {
        var locationName = "Warsaw";
        WeatherService weatherService = new WeatherService(weatherConnector -> Optional.empty(), new MailSenderFake(), new WeatherCache());

        assertThatThrownBy(() -> weatherService.processWeather(Location.of(locationName)))
                .isInstanceOf(LocationNotPresentException.class)
                .hasMessageContaining(locationName);
    }

    private static class MailSenderFake implements MailSender {

        Weather sentWeather;

        @Override
        public void sendMail(Weather weather) {
            this.sentWeather = weather;
        }
    }
}