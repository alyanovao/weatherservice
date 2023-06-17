package aao.weatherservice.controller.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "yandex.server")
public class YandexServerConfiguration extends ServerConfiguration {

}
