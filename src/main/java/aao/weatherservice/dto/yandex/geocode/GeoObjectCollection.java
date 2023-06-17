package aao.weatherservice.dto.yandex.geocode;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeoObjectCollection {

    @JsonProperty("featureMember")
    private List<FeatureMember> featureMember;
}
