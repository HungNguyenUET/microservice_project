package hungnv.account_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ComponentConfiguration {

    @Bean
    public ModelMapper initModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestClient initRestClient() {
        return RestClient.create();
    }

}
