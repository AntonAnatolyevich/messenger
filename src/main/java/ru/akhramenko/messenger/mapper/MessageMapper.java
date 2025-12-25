package ru.akhramenko.messenger.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.akhramenko.messenger.dto.MessageRequest;
import ru.akhramenko.messenger.dto.MessageResponse;
import ru.akhramenko.messenger.model.Message;

@Component
@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "messageSender", ignore = true)
    @Mapping(target = "messageRecipient", ignore = true)
    Message toEntity (MessageRequest messageRequest);

    MessageResponse toDto (Message message);
}
