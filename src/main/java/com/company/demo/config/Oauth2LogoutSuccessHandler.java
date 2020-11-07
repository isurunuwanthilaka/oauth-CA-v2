package com.company.demo.config;

import com.company.demo.model.User;
import com.company.demo.service.TokenService;
import com.company.demo.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("oauth2LogoutSuccessHandler")
@Slf4j
@RequiredArgsConstructor
public class Oauth2LogoutSuccessHandler implements LogoutSuccessHandler {

    private final TokenService tokenService;
    private final UserDetailService userDetailService;

    @Value("${server.servlet.session.cookie.path}")
    private String cookiePath;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        OAuth2AuthenticationToken oToken = (OAuth2AuthenticationToken) authentication;

        String username = oToken.getPrincipal().getName();

        User user = userDetailService.loadUserByUsername(username);
        tokenService.invalidateAllTokens(user);

        //expire cookies
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setPath(cookiePath);
            response.addCookie(cookie);
        }

        redirectStrategy.sendRedirect(request, response, Url.LOGOUT_SUCCESS.getUrl());
    }

}
