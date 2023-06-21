package aao.weatherservice.service;

import aao.weatherservice.configuration.WeatherApiServerConfiguration;
import aao.weatherservice.dto.WindDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@ConditionalOnBean(WeatherApiServerConfiguration.class)
public class ConvertorServiceImpl implements ConvertorService {

    @Value("${app.wind.divine}")
    private BigDecimal windDivine;

    @Override
    public WindDto windSpeedConvert(WindDto windkm) {
        BigDecimal speedKm = windkm.getWindSpeed();
        BigDecimal speedm = speedKm.divide(windDivine, RoundingMode.HALF_UP);
        return new WindDto(speedm);
    }
}
