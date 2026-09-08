package com.abcbank.visitor.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abcbank.visitor.domain.entities.User;
import com.abcbank.visitor.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override 
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                    .orElseThrow(() ->
                    new UsernameNotFoundException(
                        "User not found: " + username
                    ));
        String [] roles = user.getRoles()
                        .stream().map(r -> "ROLE_" + r.getRoleName().name())
                        .toArray(String[]::new);
        
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .disabled(!user.isActive())
                .authorities(roles)
                .build();
    }
}
