package ru.akhramenko.messenger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ru.akhramenko.messenger.dto.MessageRequest;
import ru.akhramenko.messenger.dto.MessageResponse;
import ru.akhramenko.messenger.security.UserPrincipal;
import ru.akhramenko.messenger.service.MessageService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class WebSocketChatController {

    private final MessageService messageService;

//    @MessageMapping("/chat.send.{recipientId}")
//    public MessageResponse sendMessage(
//            @DestinationVariable UUID recipientId,
//            @Payload MessageRequest messageRequest,
//            Principal principal) {
//        return messageService.create(recipientId, messageRequest, principal);
//    }

    @MessageMapping("/chat.send.{recipientId}")
    public MessageResponse sendMessage(Principal principal,
                                       @RequestPart(value = "file", required = false) List<MultipartFile> file,
                                       @RequestPart("message") MessageRequest messageRequest,
                                       @DestinationVariable UUID recipientId) {
        return messageService.createWithFile(recipientId, file, messageRequest, principal);
    }
}
