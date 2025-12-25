package ru.akhramenko.messenger.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.akhramenko.messenger.dto.MessengerUserRequest;
import ru.akhramenko.messenger.dto.MessengerUserResponse;
import ru.akhramenko.messenger.model.MessengerUser;

@Component
@Mapper(componentModel = "spring")
public interface MessengerUserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "secondName", ignore = true)
    MessengerUser toEntity (MessengerUserRequest messengerUserRequest);

    MessengerUserResponse toDto (MessengerUser messengerUser);
}
