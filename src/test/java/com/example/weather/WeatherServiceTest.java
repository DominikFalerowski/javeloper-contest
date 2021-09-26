package com.example.weather;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WeatherServiceTest {

    @Test
    void whenProcessWeather_thenShouldAddWeatherToCache() {
        var location = "Gdynia";
        var temperature = "32.9";
        var cache = new WeatherCache();
        WeatherService weatherService = new WeatherService(weatherConnector -> Optional.of(Weather.of(location, temperature)), new MailSenderFake(), cache);

        weatherService.processWeather(location);

        assertThat(cache.cachedWeather()).contains(Weather.of(location, temperature));
    }

    @Test
    void givenAlreadyCachedWeather_whenProcessWeather_thenShouldReplacePreviousWeather() {
        var location = "Kielce";
        var temperature = "32.9";
        var cache = new WeatherCache();
        cache.add(Weather.of(location, temperature));
        var updatedTemperature = "-10.9";
        WeatherService weatherService = new WeatherService(weatherConnector -> Optional.of(Weather.of(location, updatedTemperature)), new MailSenderFake(), cache);

        weatherService.processWeather(location);

        assertThat(cache.cachedWeather()).contains(Weather.of(location, updatedTemperature));
    }

    @Test
    void whenProcessWeather_thenShouldSendMailWithWeather() {
        var location = "Sopot";
        var temperature = "5.1";
        var cache = new WeatherCache();
        var mailSender = new MailSenderFake();
        WeatherService weatherService = new WeatherService(weatherConnector -> Optional.of(Weather.of(location, temperature)), mailSender, cache);

        weatherService.processWeather(location);

        assertThat(mailSender.sentWeather).isEqualTo(Weather.of(location, temperature));
    }

    @Test
    void givenWeatherConnectorDoesNotHaveLocation_whenProcessWeather_thenShouldThrowLocationNotPresentException() {
        var location = "Warsaw";
        WeatherService weatherService = new WeatherService(weatherConnector -> Optional.empty(), new MailSenderFake(), new WeatherCache());

        assertThatThrownBy(() -> weatherService.processWeather(location))
                .isInstanceOf(LocationNotPresentException.class)
                .hasMessageContaining(location);
    }

    private static class MailSenderFake implements MailSender {

        Weather sentWeather;

        @Override
        public void sendMail(Weather weather) {
            this.sentWeather = weather;
        }
    }
}