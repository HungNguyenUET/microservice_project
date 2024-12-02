package hungnv.account_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public ModelMapper initModelMapper() {
        return new ModelMapper();
    }
}
