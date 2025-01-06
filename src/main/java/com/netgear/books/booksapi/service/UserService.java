package com.netgear.books.booksapi.service;

import com.netgear.books.booksapi.dto.UserAuth;
import com.netgear.books.booksapi.dto.UserReg;
import com.netgear.books.booksapi.entity.User;
import com.netgear.books.booksapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.*;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtImpl jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authManager;

    public String authenticate(UserAuth authDetails) {
        User user = (User) userDetailsService.loadUserByUsername(authDetails.getEmail());
        if (user == null){
            throw new UsernameNotFoundException("User with these credentials is not found");
        }
        authManager.authenticate(new UsernamePasswordAuthenticationToken(authDetails.getEmail(), authDetails.getPassword()));
        return jwtService.generateToken(Map.of("sub",authDetails.getEmail()));
    }

    public User register(UserReg regDetails) {
            User user = User.builder()
                    .email(regDetails.getEmail())
                    .username(regDetails.getUsername())
                    .password(passwordEncoder.encode(regDetails.getPassword()))
                    .build();
                return userRepository.save(user);

    }
}
