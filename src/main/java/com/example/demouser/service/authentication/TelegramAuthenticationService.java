package com.example.demouser.service.authentication;

import com.example.demouser.model.dto.telegram.TelegramAuthenticateUserDto;

public interface TelegramAuthenticationService {

    void authenticate(TelegramAuthenticateUserDto telegramAuthenticateUserDto);

}
