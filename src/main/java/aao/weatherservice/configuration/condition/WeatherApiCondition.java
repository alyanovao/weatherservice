package aao.weatherservice.configuration.condition;

import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

public class WeatherApiCondition extends AnyNestedCondition {

    public WeatherApiCondition() {
        super(ConfigurationPhase.PARSE_CONFIGURATION);
    }

    @ConditionalOnProperty(prefix = "app.weather", value = "resource", havingValue = "weatherapi")
    static class WeatherApiConditionProperty {
    }

    @ConditionalOnProperty(prefix = "app.weather", value = "resource", havingValue = "all")
    static class AllWithWeatherApiConditionProperty {
    }
}
