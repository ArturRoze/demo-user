package com.example.demouser.service.kafka.consumer;

import com.example.demouser.model.dto.telegram.TelegramAuthenticateUserDto;
import com.example.demouser.service.authentication.TelegramAuthenticationService;
import com.example.demouser.util.JsonParserUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class TelegramAuthenticationConsumer {

    private final Logger logger = getLogger(TelegramAuthenticationConsumer.class);

    private final TelegramAuthenticationService telegramAuthenticationService;

    @Autowired
    public TelegramAuthenticationConsumer(TelegramAuthenticationService telegramAuthenticationService) {
        this.telegramAuthenticationService = telegramAuthenticationService;
    }

    @KafkaListener(groupId = "telegram_message_group",
            topicPattern = "${spring.kafka.prefix.telegram-message-topic}"
//            errorHandler = "kafkaConsumerErrorHandler"
    )
    public void telegramAuthenticateListener(ConsumerRecord<Long, String> record) {
        TelegramAuthenticateUserDto telegramAuthenticateUserDto = JsonParserUtils.convertToObject(record.value(), TelegramAuthenticateUserDto.class);
        telegramAuthenticationService.authenticate(telegramAuthenticateUserDto);
    }

}
