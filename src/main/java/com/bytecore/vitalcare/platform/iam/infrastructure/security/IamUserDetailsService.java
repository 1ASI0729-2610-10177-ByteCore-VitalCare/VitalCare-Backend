package com.bytecore.vitalcare.platform.iam.infrastructure.security;

import com.bytecore.vitalcare.platform.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class IamUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public IamUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: %s".formatted(username)));

        return new UserDetailsImpl(user.getId(), user.getEmail(), user.getPassword());
    }
}
