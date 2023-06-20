package aao.weatherservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "geolocation.server")
public class GeolocationServerConfiguration extends ServerConfiguration {

}
