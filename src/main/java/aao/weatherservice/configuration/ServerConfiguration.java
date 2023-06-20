package aao.weatherservice.configuration;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ServerConfiguration {
    private String schema;
    private String host;
    private String port;
    private String endpoint;
    private String apiKey;
    private String name;

    public String getConfiguration() {
        return schema + "://" + host + ":" + port + "/" + endpoint;
    }
}
