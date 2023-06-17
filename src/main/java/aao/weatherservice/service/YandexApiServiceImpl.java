package aao.weatherservice.service;

import aao.weatherservice.controller.configuration.ServerConfiguration;
import aao.weatherservice.dto.PlaceDto;
import aao.weatherservice.dto.Weather;
import aao.weatherservice.dto.yandex.geocode.GeocodeResponse;
import aao.weatherservice.dto.yandex.weather.YandexDto;
import aao.weatherservice.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class YandexApiServiceImpl implements YandexApiService {

    @Value("${app.format}")
    private String format;

    private final RestOperations rest;
    private final ServerConfiguration weatherConfiguration;
    private final ServerConfiguration geocoderConfiguration;

    public YandexApiServiceImpl(@Qualifier(value = "getRestOperation") RestOperations rest,
                                @Qualifier(value = "yandexServerConfiguration") ServerConfiguration weatherConfiguration,
                                @Qualifier(value = "geoCoderServerConfiguration") ServerConfiguration geocoderConfiguration) {
        this.rest = rest;
        this.weatherConfiguration = weatherConfiguration;
        this.geocoderConfiguration = geocoderConfiguration;
    }

    @Override
    public List<Weather> getWeather(PlaceDto place) {

        Map<String, Object> params = new HashMap<>();
        params.put("lat", place.getLatitude());
        params.put("lon", place.getLongitude());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Yandex-API-Key", weatherConfiguration.getApiKey());

        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            val response = rest.exchange(weatherConfiguration.getConfiguration(),
                    HttpMethod.GET,
                    request,
                    YandexDto.class, params);
            val body = response.getBody();
            val fact = Objects.requireNonNull(body).getFact();
            val weather = Weather.builder().city(place.getCity())
                    .temperature(fact.getTemp())
                    .windSpeed(fact.getWindSpeed())
                    .condition(fact.getCondition())
                    .source(weatherConfiguration.getName())
                    .build();

            return List.of(weather);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e.getStackTrace());
            throw new ApplicationException(e);
        }
    }

    @Override
    public PlaceDto getPlace(String city) {
        Map<String, Object> params = new HashMap<>();
        params.put("api-key", geocoderConfiguration.getApiKey());
        params.put("city", city);
        params.put("format", format);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(headers);
        val url = geocoderConfiguration.getConfiguration();

        try {
            val response = rest.exchange(url,
                    HttpMethod.GET,
                    request,
                    GeocodeResponse.class, params);
            val body = response.getBody();

            return getPlaceDto(body);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e.getStackTrace());
            throw new ApplicationException(e);
        }
    }

    private static PlaceDto getPlaceDto(GeocodeResponse body) throws ParseException {
        val element = body.getResponse().getGeoObjectCollection().getFeatureMember().get(0);
        val place = element.getGeoObject();
        val point = place.getPoint();
        val latitude = point.getPos().substring(point.getPos().indexOf(" ") + 1);
        val longitude = point.getPos().substring(0, point.getPos().indexOf(" "));

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        String pattern = "#0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        BigDecimal lan = (BigDecimal) decimalFormat.parse(latitude);
        BigDecimal lon = (BigDecimal) decimalFormat.parse(longitude);

        return new PlaceDto(place.getName(), lan, lon);
    }
}
