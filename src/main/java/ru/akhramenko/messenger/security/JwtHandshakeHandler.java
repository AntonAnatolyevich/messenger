package ru.akhramenko.messenger.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtHandshakeHandler extends DefaultHandshakeHandler {

    private final JwtDecoder jwtDecoder;
    private final JwtToPrincipalConverter converter;

    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        var token = extractToken(request);
        if (token == null) return null;

        try {
            var jwt = jwtDecoder.decode(token);
            return (UserPrincipal) converter.convert(jwt);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    private String extractToken(ServerHttpRequest request) {
        var query = request.getURI().getQuery();
        return query != null && query.contains("token=")
                ? query.split("token=")[1].split("&")[0]
                : null;
    }
}
