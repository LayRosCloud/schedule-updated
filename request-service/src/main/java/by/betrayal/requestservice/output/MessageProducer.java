package by.betrayal.requestservice.output;

import by.betrayal.requestservice.entity.MessageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageProducer {

    private final KafkaTemplate<String, MessageEntity> kafkaTemplate;

    public void sendMessage(MessageEntity message) {
        kafkaTemplate.send("request", message);
    }
}
