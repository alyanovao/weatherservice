package aao.weatherservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Weather {
    private String city;
    private BigDecimal temperature;
    private BigDecimal windSpeed;
    private String condition;
    private String source;
}
