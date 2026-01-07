package ru.akhramenko.messenger.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.akhramenko.messenger.model.MessageAttachment;

import java.util.UUID;

@Repository
public interface MessageAttachmentRepo extends JpaRepository<MessageAttachment, UUID> {
}
