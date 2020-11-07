package com.company.demo.util;

import com.company.demo.model.UserStatus;
import com.company.demo.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Cache {

    private final UserStatusRepository userStatusRepository;

    @Cacheable("user_status")
    public List<UserStatus> putUserStatus() {
        return userStatusRepository.findAll();
    }
}
