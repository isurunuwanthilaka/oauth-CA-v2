package com.company.demo.config;

import com.company.demo.model.User;
import com.company.demo.model.UserStatus;
import com.company.demo.model.UserStatusEnum;
import com.company.demo.repository.UserRepository;
import com.company.demo.util.CacheWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component("oauth2authSuccessHandler")
@Slf4j
@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final CacheWrapper cacheWrapper;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        OAuth2AuthenticationToken oToken = (OAuth2AuthenticationToken) authentication;

        String username = oToken.getPrincipal().getAttributes().get("username").toString();
        String email = oToken.getPrincipal().getAttributes().get("email").toString();
        Long sampathAdId = Long.valueOf(oToken.getPrincipal().getAttributes().get("sampath_ad_id").toString());
        String name = oToken.getPrincipal().getAttributes().get("name").toString();
        UserStatus activeUserStatus = cacheWrapper.getUserStatus(UserStatusEnum.ACTIVE);

        User user = User.builder().username(username).email(email).externalId(sampathAdId)
                .name(name).userStatus(activeUserStatus).build();

        String redirectUri = updateUserIfExists(user);

        redirectStrategy.sendRedirect(request, response, redirectUri);

    }

    private String updateUserIfExists(User user) {
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            user.setId(existingUser.getId());
            user.setUserStatus(existingUser.getUserStatus());
            user.setRoles(existingUser.getRoles());
            try {
                userRepository.save(user);
            } catch (Exception e) {
                log.error("Error Syncing Authenticated user to database : {}", e.getLocalizedMessage());
            }
            return Url.LOGIN.getUrl();
        } else {
            return Url.LOGOUT.getUrl();
        }
    }
}
