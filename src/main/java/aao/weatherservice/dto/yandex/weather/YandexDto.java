package aao.weatherservice.dto.yandex.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YandexDto {
    @JsonProperty("fact")
    private Fact fact;
}
