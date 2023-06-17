package aao.weatherservice.service;

import aao.weatherservice.dto.Weather;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class YandexWeatherService implements WeatherService {

    private final YandexApiService service;

    @Override
    public List<Weather> getWeather(String city) {
        val place = service.getPlace(city);
        return service.getWeather(place);
    }
}
