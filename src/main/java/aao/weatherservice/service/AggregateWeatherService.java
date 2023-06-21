package aao.weatherservice.service;

import aao.weatherservice.dto.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("all")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app.weather", value = "resource", havingValue = "all")
public class AggregateWeatherService implements WeatherService {
    private final List<WeatherService> services;

    @Override
    public List<Weather> getWeather(String city) {
        return services.stream().map(service -> service.getWeather(city))
                .flatMap(List::stream)
                .toList();
    }
}
