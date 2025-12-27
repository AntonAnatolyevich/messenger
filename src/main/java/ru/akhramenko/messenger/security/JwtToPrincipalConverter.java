package ru.akhramenko.messenger.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtToPrincipalConverter {

    private final CustomUserDetailService userDetailService;

    public UserDetails convert(DecodedJWT jwt) {
        return userDetailService.loadUserByUsername(jwt.getClaim("userName").asString());
    }
}
