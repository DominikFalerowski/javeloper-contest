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
        WeatherService weatherService = new WeatherService(weatherConnector -> Optional.of(Weather.of(location, temperature)), new LocationProviderFake(location), new MailSenderFake(), cache);

        weatherService.processWeather();

        assertThat(cache.cachedWeather()).contains(Weather.of(location, temperature));
    }

    @Test
    void givenAlreadyCachedWeather_whenProcessWeather_thenShouldReplacePreviousWeather() {
        var location = "Kielce";
        var temperature = "32.9";
        var cache = new WeatherCache();
        cache.add(Weather.of(location, temperature));
        var updatedTemperature = "-10.9";
        WeatherService weatherService = new WeatherService(weatherConnector -> Optional.of(Weather.of(location, updatedTemperature)), new LocationProviderFake(location), new MailSenderFake(), cache);

        weatherService.processWeather();

        assertThat(cache.cachedWeather()).contains(Weather.of(location, updatedTemperature));
    }

    @Test
    void whenProcessWeather_thenShouldSendMailWithWeather() {
        var location = "Sopot";
        var temperature = "5.1";
        var cache = new WeatherCache();
        var mailSender = new MailSenderFake();
        WeatherService weatherService = new WeatherService(weatherConnector -> Optional.of(Weather.of(location, temperature)), new LocationProviderFake(location), mailSender, cache);

        weatherService.processWeather();

        assertThat(mailSender.sentWeather).isEqualTo(Weather.of(location, temperature));
    }

    @Test
    void givenWeatherConnectorDoesNotHaveLocation_whenProcessWeather_thenShouldThrowLocationNotPresentException() {
        var location = "Warsaw";
        WeatherService weatherService = new WeatherService(weatherConnector -> Optional.empty(), new LocationProviderFake(location), new MailSenderFake(), new WeatherCache());

        assertThatThrownBy(weatherService::processWeather)
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

    private static class LocationProviderFake implements LocationProvider {

        private final String[] locations;

        private LocationProviderFake(String... locations) {
            this.locations = locations;
        }

        @Override
        public String location() {
            return locations[0];
        }

        @Override
        public int getSize() {
            return locations.length;
        }
    }
}