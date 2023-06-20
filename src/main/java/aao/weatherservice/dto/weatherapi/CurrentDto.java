package aao.weatherservice.dto.weatherapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentDto {

    @JsonProperty(value = "temp_c")
    private BigDecimal temp;

    @JsonProperty(value = "wind_mph")
    private BigDecimal windSpeed;

    @JsonProperty(value = "condition")
    private Condition condition;
}
