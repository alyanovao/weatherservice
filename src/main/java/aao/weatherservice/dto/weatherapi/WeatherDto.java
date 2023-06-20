package aao.weatherservice.dto.weatherapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {

    @JsonProperty(value = "location")
    private Location location;

    @JsonProperty(value = "current")
    private CurrentDto current;
}
