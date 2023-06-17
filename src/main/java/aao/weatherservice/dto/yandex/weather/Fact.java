package aao.weatherservice.dto.yandex.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Fact {

    @JsonProperty("temp")
    private BigDecimal temp;

    @JsonProperty("condition")
    private String condition;

    @JsonProperty("wind_speed")
    private BigDecimal windSpeed;
}
