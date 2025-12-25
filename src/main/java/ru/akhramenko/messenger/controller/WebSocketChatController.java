package ru.akhramenko.messenger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import ru.akhramenko.messenger.dto.MessageRequest;
import ru.akhramenko.messenger.dto.MessageResponse;
import ru.akhramenko.messenger.service.MessageService;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class WebSocketChatController {

    private final MessageService messageService;

    @MessageMapping("/chat.send.{recipientId}")
    public MessageResponse sendMessage(
            @DestinationVariable UUID recipientId,
            @Payload MessageRequest messageRequest,
            Principal principal) {
        return messageService.create(recipientId, messageRequest, principal);
    }
}
