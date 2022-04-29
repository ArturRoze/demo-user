package com.example.demouser.service.kafka.producer;

import com.example.demouser.model.dto.telegram.TelegramAuthorizedUserDto;
import com.example.demouser.util.JsonParserUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class TelegramAuthenticationProducer {

    private final Logger logger = getLogger(TelegramAuthenticationProducer.class);

    @Value("${spring.kafka.prefix.telegram-authenticate-topic}")
    private String telegramAuthenticateTopic;

    private final KafkaTemplate<Long, String> kafkaTemplate;

    public TelegramAuthenticationProducer(KafkaTemplate<Long, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(TelegramAuthorizedUserDto telegramAuthorizedUserDto) {
        String message = JsonParserUtils.convertToJson(telegramAuthorizedUserDto);
        Long userId = telegramAuthorizedUserDto.getUserId();
        ListenableFuture<SendResult<Long, String>> futureObject = kafkaTemplate.send(telegramAuthenticateTopic, userId, message);
        futureObject.addCallback(getCallback(message, telegramAuthenticateTopic));
    }

    private ListenableFutureCallback<? super SendResult<Long, String>> getCallback(String data, String kafkaTopic) {
        return new ListenableFutureCallback<SendResult<Long, String>>() {

            @Override
            public void onSuccess(SendResult<Long, String> result) {
                logger.info("TELEGRAM MESSAGE: Data was sent to topic " + kafkaTopic + ": " + "successfully");
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("TELEGRAM MESSAGE: Error while sending data " + data + " to topic " + kafkaTopic);
                throw new RuntimeException(ex.getMessage());
            }

        };
    }

}
