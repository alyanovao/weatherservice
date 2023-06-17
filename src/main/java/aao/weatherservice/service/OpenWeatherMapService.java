package aao.weatherservice.service;

import aao.weatherservice.controller.configuration.ServerConfiguration;
import aao.weatherservice.dto.openmap.OpenMapDto;
import aao.weatherservice.dto.Weather;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class OpenWeatherMapService implements WeatherService {

    private final RestOperations restTemplate;
    private final ServerConfiguration configuration;

    @Value(value = "${app.unit}")
    private String unit;

    public OpenWeatherMapService(@Qualifier(value = "getRestOperation") RestOperations restTemplate,
                                 @Qualifier(value = "openWeatherMapServerConfiguration") ServerConfiguration configuration) {
        this.restTemplate = restTemplate;
        this.configuration = configuration;
    }

    @Override
    public List<Weather> getWeather(String city) {
        Map<String, Object> params = new HashMap<>();
        params.put("city", city);
        params.put("api-key", configuration.getApiKey());
        params.put("units", unit);

        val res = restTemplate.getForEntity(configuration.getConfiguration(), OpenMapDto.class, params);
        val body = res.getBody();
        val weather = Weather.builder()
                .city(city)
                .temperature(Objects.requireNonNull(body).getMain().getTemp())
                .windSpeed(body.getWind().getSpeed())
                .condition(body.getWeather().get(0).getMain())
                .source(configuration.getName())
                .build();
        return List.of(weather);
    }
}
