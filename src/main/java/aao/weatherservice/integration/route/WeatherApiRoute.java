package aao.weatherservice.integration.route;

import aao.weatherservice.common.Constant;
import aao.weatherservice.configuration.ServerConfiguration;
import aao.weatherservice.configuration.WeatherApiServerConfiguration;
import aao.weatherservice.dto.weatherapi.WeatherDto;
import aao.weatherservice.integration.processor.PrepareQueryProcessor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnBean(WeatherApiServerConfiguration.class)
public class WeatherApiRoute extends RouteBuilder {

    private final ServerConfiguration configuration;

    public WeatherApiRoute(CamelContext context,
                           @Qualifier(value = "weatherApiServerConfiguration") ServerConfiguration configuration) {
        super(context);
        this.configuration = configuration;
    }

    @Override
    public void configure() throws Exception {
        errorHandler(defaultErrorHandler());

        onException(Exception.class)
                .handled(true)
                .process(exchange -> {
                    val exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                    log.error(exception.getMessage());
                    exchange.getIn().setBody(exception);
                })
                .end();

        from("direct:weatherApiRoute")
            .routeId("weatherRouteId")
            .process(new PrepareQueryProcessor(configuration))
            .toD("${exchangeProperty." + Constant.WEATHER_URL + "}")
            .unmarshal().json(JsonLibrary.Jackson, WeatherDto.class)
        .end();
    }
}
