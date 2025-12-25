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
    public List<MessengerUserResponse> getUserById() {
        return messengerUserService.findAll();
    }

    @GetMapping("/{id}")
    public MessengerUser getUserById(@PathVariable UUID id) {
        return messengerUserService.findEntityById(id);
    }

    @GetMapping("/search/{name}")
    public MessengerUser getUserByName(@PathVariable String  name) {
        return messengerUserService.findEntityByUserName(name);
    }

    @GetMapping("/chatlist")
    public List<MessengerUserResponse> getChatList(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return messengerUserService.findAllRecipientByUserId(userPrincipal.getMessengerUser().getId());
    }

    @GetMapping("/chatlist/{id}")
    public List<MessageResponse> getDialogHistory(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                  @PathVariable UUID id) {
        return messageService.findDialogHistory(userPrincipal.getMessengerUser().getId(), id);
    }
}
