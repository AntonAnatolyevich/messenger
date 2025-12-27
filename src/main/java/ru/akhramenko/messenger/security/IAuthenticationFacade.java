package ru.akhramenko.messenger.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthenticationFacade {

    UserDetails getAuthenticationPrincipal();

}
