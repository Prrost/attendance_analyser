package kz.diploma.rprettser.attendance_analyser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class LmsClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        // TODO: добавить таймауты, error handler
        return new RestTemplate();
    }
}
