package aao.weatherservice.service;

import aao.weatherservice.dto.PlaceDto;
import aao.weatherservice.dto.Weather;

import java.util.List;

public interface YandexApiService {
    List<Weather> getWeather(PlaceDto place);
    PlaceDto getPlace(String city);
}
