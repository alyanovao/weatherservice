package aao.weatherservice.service;

import aao.weatherservice.configuration.ServerConfiguration;
import aao.weatherservice.configuration.WeatherApiServerConfiguration;
import aao.weatherservice.dto.Weather;
import aao.weatherservice.dto.WindDto;
import aao.weatherservice.dto.weatherapi.WeatherDto;
import aao.weatherservice.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("weatherapi")
@ConditionalOnBean(WeatherApiServerConfiguration.class)
public class WeatherApiService implements WeatherService {

    private final ProducerTemplate template;
    private final ServerConfiguration configuration;
    private final ConvertorService convertor;

    public WeatherApiService(ProducerTemplate template,
                             ConvertorService convertor,
                             @Qualifier(value = "weatherApiServerConfiguration") ServerConfiguration configuration) {
        this.template = template;
        this.convertor = convertor;
        this.configuration = configuration;
    }

    @Override
    public List<Weather> getWeather(String city) {
        val response = template.requestBody("direct:weatherApiRoute", city, Object.class);
        if (response instanceof Exception exception) {
            log.error(exception.getMessage());
            throw new ApplicationException(exception);
        }
        WeatherDto weatherDto = (WeatherDto) response;
        val weather = Weather.builder()
                .city(weatherDto.getLocation().getName())
                .temperature(weatherDto.getCurrent().getTemp())
                .windSpeed(convertor.windSpeedConvert(new WindDto(weatherDto.getCurrent().getWindSpeed())).getWindSpeed())
                .condition(weatherDto.getCurrent().getCondition().getText())
                .source(configuration.getName())
                .build();
        return List.of(weather);
    }
}
