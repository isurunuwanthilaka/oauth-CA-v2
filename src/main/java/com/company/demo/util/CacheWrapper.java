package com.company.demo.util;
import com.company.demo.model.UserStatus;
import com.company.demo.model.UserStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CacheWrapper {

    private final Cache cache;

    public UserStatus getUserStatus(UserStatusEnum userStatus) {
        List<UserStatus> arr = cache.putUserStatus();
        Optional<UserStatus> optional = arr.stream().filter(x -> x.getStatusName().getName().equalsIgnoreCase(userStatus.getName())).findFirst();
        return optional.orElse(null);
    }
}
