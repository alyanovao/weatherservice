package aao.weatherservice.service;

import aao.weatherservice.dto.WindDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class ConvertorServiceImpl implements ConvertorService {

    @Override
    public WindDto windSpeedConvert(WindDto windkm) {
        BigDecimal speedKm = windkm.getWindSpeed();
        BigDecimal speedm = speedKm.divide(BigDecimal.valueOf(3.6), RoundingMode.HALF_UP);
        return new WindDto(speedm);
    }
}
