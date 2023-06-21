package aao.weatherservice.controller;

import aao.weatherservice.dto.Weather;
import aao.weatherservice.service.WeatherService;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherController {

    private final WeatherService service;

    public WeatherController(@Qualifier(value = "${app.weather.resource}") WeatherService service) {
        this.service = service;
    }

    @GetMapping(value = "/api/getWeather/{city}")
    public ResponseEntity<List<Weather>> getWeather(@PathVariable String city) {
        val res = service.getWeather(city);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
