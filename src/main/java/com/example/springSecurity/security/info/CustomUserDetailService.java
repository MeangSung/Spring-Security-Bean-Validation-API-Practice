package com.example.springSecurity.security.info;

import com.example.springSecurity.user.repo.UserRepository;
import com.example.springSecurity.user.repo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User entity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return CustomUserDetail.create(entity);
    }
}
