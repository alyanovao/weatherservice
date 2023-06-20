package aao.weatherservice.integration.processor;

import aao.weatherservice.common.Constant;
import aao.weatherservice.configuration.ServerConfiguration;
import lombok.val;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class PrepareQueryProcessor implements Processor {

    private final ServerConfiguration configuration;

    public PrepareQueryProcessor(ServerConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        val body = exchange.getIn().getBody(String.class);
        val url = configuration.getConfiguration()
                .replace("{api-key}", configuration.getApiKey())
                .replace("{city}", body);
        exchange.setProperty(Constant.WEATHER_URL, url);
    }
}
