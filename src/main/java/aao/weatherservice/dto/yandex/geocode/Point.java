package aao.weatherservice.dto.yandex.geocode;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Point {

    @JsonProperty("pos")
    private String pos;
}
