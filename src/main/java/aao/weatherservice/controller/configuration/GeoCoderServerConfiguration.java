package aao.weatherservice.controller.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "geocoder.server")
public class GeoCoderServerConfiguration extends ServerConfiguration {

}
