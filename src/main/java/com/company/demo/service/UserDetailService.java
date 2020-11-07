package com.company.demo.service;

import com.company.demo.model.User;
import com.company.demo.repository.UserRepository;
import com.company.demo.util.AuditLogger;
import com.company.demo.util.CacheWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailService {

    private final UserRepository userRepository;
    private final CacheWrapper cacheWrapper;

    public User getAuthenticatedUserFromSecurityContext() {

        DefaultOAuth2User principal = (DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal != null) {
            return loadUserByUsername(principal.getName());
        }

        return null;
    }

    public User loadUserByUsername(String name) {
        Optional<User> optionalUser = userRepository.findByUsername(name);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            AuditLogger.read(user);
            return user;
        }
        return null;
    }

}
