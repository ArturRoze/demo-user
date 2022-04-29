package com.example.demouser.service.authentication.impl;

import com.example.demouser.exception.PasswordException;
import com.example.demouser.model.dto.telegram.TelegramAuthenticateUserDto;
import com.example.demouser.model.dto.telegram.TelegramAuthorizedUserDto;
import com.example.demouser.model.entity.user.User;
import com.example.demouser.service.authentication.AuthenticatedUserService;
import com.example.demouser.service.authentication.TelegramAuthenticationService;
import com.example.demouser.service.kafka.producer.TelegramAuthenticationProducer;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class TelegramAuthenticationServiceImpl implements TelegramAuthenticationService {

    private final AuthenticatedUserService authenticatedUserService;

    private final TelegramAuthenticationProducer telegramAuthenticationProducer;

    public TelegramAuthenticationServiceImpl(AuthenticatedUserService authenticatedUserService,
                                             TelegramAuthenticationProducer telegramAuthenticationProducer) {
        this.authenticatedUserService = authenticatedUserService;
        this.telegramAuthenticationProducer = telegramAuthenticationProducer;
    }

    @Override
    public void authenticate(TelegramAuthenticateUserDto telegramAuthenticateUserDto) {

        User authenticatedUser = findAuthenticatedUser(telegramAuthenticateUserDto);

        telegramAuthenticationProducer.sendMessage(buildTelegramAuthorizedUserDto(authenticatedUser, telegramAuthenticateUserDto.getChatId()));
    }

    private User findAuthenticatedUser(TelegramAuthenticateUserDto telegramAuthenticateUserDto) {
        try {
            return authenticatedUserService.authenticateEntity(telegramAuthenticateUserDto.getLogin(), telegramAuthenticateUserDto.getPassword());
        } catch (EntityNotFoundException | PasswordException ex) {
            return null;
        }
    }

    private TelegramAuthorizedUserDto buildTelegramAuthorizedUserDto(User user, Long chatId) {
        if (user == null) {
            return new TelegramAuthorizedUserDto(null, chatId,  null, false);
        }

        return new TelegramAuthorizedUserDto(user.getId(), chatId, user.getLogin(), true);
    }

}
