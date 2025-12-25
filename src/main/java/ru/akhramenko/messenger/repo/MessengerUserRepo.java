package ru.akhramenko.messenger.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.akhramenko.messenger.model.MessengerUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessengerUserRepo extends JpaRepository<MessengerUser, UUID> {

    Optional<MessengerUser> findByUserName(String userName);

    @Query(value = "SELECT DISTINCT u.* FROM users u " +
            "JOIN message m ON u.id = m.recipient_id " +
            "WHERE m.sender_id = :senderId " +
            "UNION " +
            "SELECT DISTINCT u.* FROM users u " +
            "JOIN message m ON u.id = m.sender_id " +
            "WHERE m.recipient_id = :senderId",
            nativeQuery = true)
    List<MessengerUser> findAllRecipientByUserId(@Param("senderId") UUID senderId);

}
