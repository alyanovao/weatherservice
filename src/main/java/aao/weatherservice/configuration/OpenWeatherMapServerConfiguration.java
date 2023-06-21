package aao.weatherservice.configuration;

import aao.weatherservice.configuration.condition.OpenWeatherMapCondition;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Conditional(OpenWeatherMapCondition.class)
@ConfigurationProperties(prefix = "openmap.server")
public class OpenWeatherMapServerConfiguration extends ServerConfiguration {

}
