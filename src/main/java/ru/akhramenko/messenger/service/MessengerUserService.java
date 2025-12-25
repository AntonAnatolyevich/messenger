package ru.akhramenko.messenger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akhramenko.messenger.dto.MessengerUserResponse;
import ru.akhramenko.messenger.exception.UserNotFoundException;
import ru.akhramenko.messenger.mapper.MessengerUserMapper;
import ru.akhramenko.messenger.model.MessengerUser;
import ru.akhramenko.messenger.repo.MessengerUserRepo;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessengerUserService {

    private final MessengerUserMapper messengerUserMapper;
    private final MessengerUserRepo messengerUserRepo;

    public List<MessengerUserResponse> findAll() {
        return messengerUserRepo.findAll().stream().map(messengerUserMapper::toDto).toList();
    }

    public List<MessengerUserResponse> findAllRecipientByUserId(UUID id) {
        return messengerUserRepo.findAllRecipientByUserId(id).stream().map(messengerUserMapper::toDto).toList();
    }

    public MessengerUser findEntityById(UUID id) {
        return messengerUserRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User with id: " + id + " doesn't exist"));
    }

    public MessengerUser findEntityByUserName(String name) {
        return messengerUserRepo.findByUserName(name).orElseThrow(() -> new UserNotFoundException("User with id: " + name + " doesn't exist"));
    }
}
