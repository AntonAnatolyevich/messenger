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

    public MessageResponse create(UUID recipientId, MessageRequest messageRequest, Principal principal) {
        var message = messageMapper.toEntity(messageRequest);
        var messengerUserSender = messengerUserService.findEntityById(UUID.fromString(principal.getName()));
        var messengerUserRecipient = messengerUserService.findEntityById(recipientId);
        message.setMessageSender(messengerUserSender);
        message.setMessageRecipient(messengerUserRecipient);
        messageRepo.save(message);

        // я бы раздели на два сервиса для удробства переиспользования и тестирования
        // первый сервис работает непосредсвенно с сущностью, типо create update и тд
        // второй сервис используе сервис для работы с сущностью и занимается рассылкой по сокету пользакам
        // сейчас тесты будет неудобно писать

        // вот как у тебя есть щас messengerUserService
        // messageService для сущности
        // messageSendingService для отправки
        var messageResponse = messageMapper.toDto(message);
        messagingTemplate.convertAndSendToUser(
                recipientId.toString(),
                "/queue/messages",
                messageResponse
        );
        return messageResponse;
    }

    // вот тут тоже сделал бы отдельный метод для работы непосредсвенно с сущностью
    // и потом отдельный сервис который его использует и уже там бы мапил в модельнку
    // потому что представь что модель расширится и нужно будет брать инфу и из других сервисов
    // некрасиво будет все сюда тащить
    public List<MessageResponse> findDialogHistory(UUID senderId, UUID recipientId) {
        return messageRepo.findDialogHistory(senderId, recipientId).stream().map(messageMapper::toDto).toList();
    }
}
