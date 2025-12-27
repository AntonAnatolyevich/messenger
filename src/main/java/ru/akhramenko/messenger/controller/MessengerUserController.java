package ru.akhramenko.messenger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.akhramenko.messenger.dto.MessageResponse;
import ru.akhramenko.messenger.dto.MessengerUserResponse;
import ru.akhramenko.messenger.model.MessengerUser;
import ru.akhramenko.messenger.security.UserPrincipal;
import ru.akhramenko.messenger.service.MessageService;
import ru.akhramenko.messenger.service.MessengerUserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class MessengerUserController {

    private final MessengerUserService messengerUserService;
    private final MessageService messageService;

    @GetMapping("")
    public List<MessengerUserResponse> getAllUsers() {
        return messengerUserService.findAll();
    }

    @GetMapping("/{id}")
    public MessengerUser getUserById(@PathVariable UUID id) {
        return messengerUserService.findEntityById(id);
    }

    // лучше сделать ручку отдельную где поиск через квери параметры осуществляется
    // сейчас не расширяемо
    // чото типо /users/search?name=Jeka&age=20&city=Kal
    @GetMapping("/search/{name}")
    public MessengerUser getUserByName(@PathVariable String  name) {
        return messengerUserService.findEntityByUserName(name);
    }

    @GetMapping("/chats")
    public List<MessengerUserResponse> getChatList(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return messengerUserService.findAllRecipientByUserId(userPrincipal.getMessengerUser().getId());
    }
    // dialog это как будто чото на русский лад
    // лучше чат просто, ну и не история я бы назвал а getUserChat
    @GetMapping("/chats/{id}")
    public List<MessageResponse> getDialogHistory(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                  @PathVariable UUID id) {
        return messageService.findDialogHistory(userPrincipal.getMessengerUser().getId(), id);
    }
}
