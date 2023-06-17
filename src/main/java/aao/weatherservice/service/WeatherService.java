package aao.weatherservice.service;

import aao.weatherservice.dto.Weather;

import java.util.List;

public interface WeatherService {
    List<Weather> getWeather(String city);
}
