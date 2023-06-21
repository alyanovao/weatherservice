package aao.weatherservice.configuration.condition;

import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

public class OpenWeatherMapCondition extends AnyNestedCondition {

    public OpenWeatherMapCondition() {
        super(ConfigurationPhase.PARSE_CONFIGURATION);
    }

    @ConditionalOnProperty(prefix = "app.weather", value = "resource", havingValue = "openWeatherMap")
    static class OpenWeatherMapConditionProperty {
    }

    @ConditionalOnProperty(prefix = "app.weather", value = "resource", havingValue = "all")
    static class AllWithOpenWeatherMapConditionProperty {
    }
}
