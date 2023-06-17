package aao.weatherservice.dto.openmap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Weather {
    @JsonProperty("main")
    private String main;
}
