package kz.diploma.rprettser.attendance_analyser.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@Configuration
public class KafkaConfig {
    // TODO: кастомная десериализация если понадобится
}
