package aao.weatherservice.configuration;

import aao.weatherservice.configuration.condition.WeatherApiCondition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(WeatherApiCondition.class)
@ConfigurationProperties(prefix = "weatherapi.server")
public class WeatherApiServerConfiguration extends ServerConfiguration {
}
