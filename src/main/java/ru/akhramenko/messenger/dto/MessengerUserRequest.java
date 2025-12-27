package ru.akhramenko.messenger.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record MessengerUserRequest(

    @NotNull(message = "You need to have a name")
    @Size(min = 4, max = 32, message = "Username size should be between 4 and 32")
    String userName,

    @Size(min = 2, max = 32, message = "Name size should be between 2 and 32")
    String firstName,

    @Size(min = 2, max = 32, message = "Password size should be between 2 and 32")
    String password
) {}
