package aao.weatherservice.service;

import aao.weatherservice.configuration.ServerConfiguration;
import aao.weatherservice.configuration.YandexServerConfiguration;
import aao.weatherservice.dto.PlaceDto;
import aao.weatherservice.dto.yandex.geocode.GeocodeResponse;
import aao.weatherservice.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
import java.util.Map;

@Slf4j
@Service
@ConditionalOnBean(YandexServerConfiguration.class)
public class GeolocationServiceImpl implements GeolocationService {

    @Value("${app.format}")
    private String format;

    private final RestOperations rest;
    private final ServerConfiguration geolocationServerConfiguration;

    public GeolocationServiceImpl(@Qualifier(value = "getRestOperation") RestOperations rest,
                                  @Qualifier(value = "geolocationServerConfiguration") ServerConfiguration geolocationServerConfiguration) {
        this.rest = rest;
        this.geolocationServerConfiguration = geolocationServerConfiguration;
    }

    @Override
    public PlaceDto getPlaceByCityName(String city) {
        Map<String, Object> params = new HashMap<>();
        params.put("api-key", geolocationServerConfiguration.getApiKey());
        params.put("city", city);
        params.put("format", format);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            val response = rest.exchange(
                    geolocationServerConfiguration.getConfiguration(),
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
