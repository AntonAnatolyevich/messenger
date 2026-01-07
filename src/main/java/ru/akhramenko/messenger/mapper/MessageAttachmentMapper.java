package ru.akhramenko.messenger.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.akhramenko.messenger.dto.MessageAttachmentResponse;

import ru.akhramenko.messenger.model.MessageAttachment;

@Component
@Mapper(componentModel = "spring")
public interface MessageAttachmentMapper {

    MessageAttachmentResponse toDto (MessageAttachment messageAttachment);
}
