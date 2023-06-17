package aao.weatherservice.controller.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "openmap.server")
public class OpenWeatherMapServerConfiguration extends ServerConfiguration {

}
