package ru.akhramenko.messenger.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.akhramenko.messenger.exception.UserNotFoundException;
import ru.akhramenko.messenger.model.MessengerUser;
import ru.akhramenko.messenger.repo.MessengerUserRepo;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MessengerUserRepo messengerUserRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        MessengerUser messengerUser = messengerUserRepo.findByUserName(userName).orElseThrow(() -> new UserNotFoundException("User doesn't exist"));
        return UserPrincipal.builder()
                .messengerUser(messengerUser)
                .build();
    }
}
