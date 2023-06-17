package aao.weatherservice.dto.openmap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Main {

    @JsonProperty("temp")
    private BigDecimal temp;
}
