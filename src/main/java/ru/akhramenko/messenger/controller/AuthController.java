package ru.akhramenko.messenger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.akhramenko.messenger.dto.AuthRequest;
import ru.akhramenko.messenger.dto.AuthResponse;
import ru.akhramenko.messenger.dto.MessengerUserRequest;
import ru.akhramenko.messenger.dto.MessengerUserResponse;
import ru.akhramenko.messenger.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public MessengerUserResponse signin(@RequestBody MessengerUserRequest messengerUserRequest) {
        return authService.signInMessengerUser(messengerUserRequest);
    }

    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody AuthRequest authRequest) {
        return authService.signUpMessengerUser(authRequest);
    }
}
