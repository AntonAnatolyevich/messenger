package ru.akhramenko.messenger.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthRequest {

    @NotNull(message = "Username can not be empty")
    @Size(min = 1, max = 32, message = "Username size should be between 4 and 32")
    private String userName;

    @Size(min = 6, max = 20, message = "Password size should be between 6 and 20")
    @NotNull(message = "Password can not be empty")
    private String password;
}
