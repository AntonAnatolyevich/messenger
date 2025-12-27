package ru.akhramenko.messenger.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtToPrincipalConverter jwtToPrincipalConverter;
    private final JwtDecoder jwtDecoder;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) {
        try {
            extractTokenFromRequest(request)
                    .map(jwtDecoder::decode)
                    .map(jwtToPrincipalConverter::convert)
                    .ifPresent(userDetails -> attributes.put("userDetails", userDetails));
            var query = request.getURI().getQuery();
            if (query != null && query.contains("channelId=")) {
                var channelId = query.split("channelId=")[1]; // Парсим из URL
                attributes.put("channelId", UUID.fromString(channelId));
            }
            return true;
            // плохо ловить так, либо конкретные Exception лови либо переписать логику стоит
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // Можно добавить логику после установки соединения
    }

    private Optional<String> extractTokenFromRequest(ServerHttpRequest request) {
        var token = request.getHeaders().getFirst("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return Optional.of(token.substring(7));
        }
        return Optional.empty();
    }
}
