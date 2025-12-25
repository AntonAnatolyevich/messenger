package ru.akhramenko.messenger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.akhramenko.messenger.dto.MessageRequest;
import ru.akhramenko.messenger.dto.MessageResponse;
import ru.akhramenko.messenger.mapper.MessageMapper;
import ru.akhramenko.messenger.model.Message;
import ru.akhramenko.messenger.model.MessengerUser;
import ru.akhramenko.messenger.repo.MessageRepo;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepo messageRepo;
    private final MessageMapper messageMapper;
    private final MessengerUserService messengerUserService;
    private final SimpMessagingTemplate messagingTemplate;

    public MessageResponse create (UUID recipientId, MessageRequest messageRequest, Principal principal) {
        Message message = messageMapper.toEntity(messageRequest);
        MessengerUser messengerUserSender = messengerUserService.findEntityById(UUID.fromString(principal.getName()));
        MessengerUser messengerUserRecipient = messengerUserService.findEntityById(recipientId);
        message.setCreatedAt(LocalDateTime.now());
        message.setMessageSender(messengerUserSender);
        message.setMessageRecipient(messengerUserRecipient);
        messageRepo.save(message);

        MessageResponse messageResponse = messageMapper.toDto(message);
        messagingTemplate.convertAndSendToUser(
                recipientId.toString(),
                "/queue/messages",
                messageResponse
        );
        return messageResponse;
    }

    public List<MessageResponse> findDialogHistory (UUID senderId, UUID recipientId) {
        return messageRepo.findDialogHistory(senderId, recipientId).stream().map(messageMapper::toDto).toList();
    }
}
