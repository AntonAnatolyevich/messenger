package ru.akhramenko.messenger.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.akhramenko.messenger.model.Message;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepo extends JpaRepository<Message, UUID> {

    @Query(value = "SELECT * FROM message WHERE sender_id = :senderId", nativeQuery = true)
    List<Message> findMessagesByUserId(@Param("senderId") UUID senderId);

    @Query(value = "SELECT m.* FROM Message m WHERE (m.sender_id = :user1Id AND m.recipient_id = :user2Id) OR (m.sender_id = :user2Id AND m.recipient_id = :user1Id) ORDER BY m.created_at ASC", nativeQuery = true)
    List<Message> findDialogHistory(@Param("user1Id") UUID user1Id, @Param("user2Id") UUID user2Id);
}
