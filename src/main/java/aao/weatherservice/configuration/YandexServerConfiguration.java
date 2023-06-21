package aao.weatherservice.configuration;

import aao.weatherservice.configuration.condition.YandexCondition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(YandexCondition.class)
@ConfigurationProperties(prefix = "yandex.server")
public class YandexServerConfiguration extends ServerConfiguration {

}
