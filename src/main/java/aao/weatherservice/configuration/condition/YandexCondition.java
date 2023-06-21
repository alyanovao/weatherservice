package aao.weatherservice.configuration.condition;

import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

public class YandexCondition extends AnyNestedCondition {

    public YandexCondition() {
        super(ConfigurationPhase.PARSE_CONFIGURATION);
    }

    @ConditionalOnProperty(prefix = "app.weather", value = "resource", havingValue = "yandex")
    static class YandexConditionProperty {
    }

    @ConditionalOnProperty(prefix = "app.weather", value = "resource", havingValue = "all")
    static class AllWithYandexConditionProperty {
    }
}
