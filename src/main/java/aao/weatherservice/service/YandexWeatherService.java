package aao.weatherservice.service;

import aao.weatherservice.configuration.ServerConfiguration;
import aao.weatherservice.configuration.YandexServerConfiguration;
import aao.weatherservice.dto.Weather;
import aao.weatherservice.dto.yandex.weather.YandexDto;
import aao.weatherservice.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service("yandex")
@ConditionalOnBean(YandexServerConfiguration.class)
public class YandexWeatherService implements WeatherService {

    private final RestOperations restTemplate;
    private final GeolocationService geolocationService;
    private final ServerConfiguration yandexServerConfiguration;

    public YandexWeatherService(RestOperations restTemplate,
                                GeolocationService geolocationService,
                                @Qualifier(value = "yandexServerConfiguration") ServerConfiguration yandexServerConfiguration) {
        this.restTemplate = restTemplate;
        this.geolocationService = geolocationService;
        this.yandexServerConfiguration = yandexServerConfiguration;
    }

    @Override
    public List<Weather> getWeather(String city) {

        val place = geolocationService.getPlaceByCityName(city);
        Map<String, Object> params = new HashMap<>();
        params.put("lat", place.getLatitude());
        params.put("lon", place.getLongitude());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Yandex-API-Key", yandexServerConfiguration.getApiKey());

        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            val response = restTemplate.exchange(
                    yandexServerConfiguration.getConfiguration(),
                    HttpMethod.GET,
                    request,
                    YandexDto.class, params);
            val body = response.getBody();
            val fact = Objects.requireNonNull(body).getFact();
            val weather = Weather.builder().city(place.getCity())
                    .temperature(fact.getTemp())
                    .windSpeed(fact.getWindSpeed())
                    .condition(fact.getCondition())
                    .source(yandexServerConfiguration.getName())
                    .build();

            return List.of(weather);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApplicationException(e);
        }
    }
}
