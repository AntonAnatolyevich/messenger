package ru.akhramenko.messenger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.akhramenko.messenger.dto.AuthRequest;
import ru.akhramenko.messenger.dto.AuthResponse;
import ru.akhramenko.messenger.dto.MessengerUserRequest;
import ru.akhramenko.messenger.dto.MessengerUserResponse;
import ru.akhramenko.messenger.mapper.MessengerUserMapper;
import ru.akhramenko.messenger.model.MessengerUser;
import ru.akhramenko.messenger.repo.MessengerUserRepo;
import ru.akhramenko.messenger.security.JwtIssuer;
import ru.akhramenko.messenger.security.UserPrincipal;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MessengerUserMapper messengerUserMapper;
    private final MessengerUserRepo messengerUserRepo;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtIssuer jwtIssuer;

    public MessengerUserResponse signInMessengerUser(MessengerUserRequest messengerUserRequest) {
        // создание сущности доллнжо быть в messageUserService, репозиторий сюда тащить не надо
        MessengerUser messengerUser = messengerUserMapper.toEntity(messengerUserRequest);
        messengerUser.setPassword(bCryptPasswordEncoder.encode(messengerUserRequest.password()));
        // Date устаревшее апи
        // ну и это не надо ваще
//        messengerUser.setCreatedAt(new Date());
        messengerUserRepo.save(messengerUser);
        return messengerUserMapper.toDto(messengerUser);
    }

    /*Следует разделить token и user */
    public AuthResponse signUpMessengerUser(AuthRequest authRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.userName(), authRequest.password())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        String token = jwtIssuer.issue(principal);

        return AuthResponse.builder()
                .token(token)
                .user(messengerUserMapper.toDto(principal.getMessengerUser()))
                .build();
    }
}
