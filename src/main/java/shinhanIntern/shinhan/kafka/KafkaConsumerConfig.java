package shinhanIntern.shinhan.kafka;

import jdk.jfr.Enabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Enabled
@Configuration
public class KafkaConsumerConfig {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    public KafkaConsumerConfig(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
