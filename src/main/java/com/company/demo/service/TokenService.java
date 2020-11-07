package com.company.demo.service;

import com.company.demo.exception.EntitySaveException;
import com.company.demo.model.Token;
import com.company.demo.model.User;
import com.company.demo.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public void invalidateAllTokens(User user) {
        List<Token> tokens = tokenRepository.findAllByUserAndInvalidated(user,0);

        tokens.forEach(token -> {
            token.setInvalidated(1);
            token.setLogoutAt(LocalDateTime.now());
        });

        try {
            tokenRepository.saveAll(tokens);
        } catch (Exception e) {
            throw new EntitySaveException("Invalidated token save failed.");
        }
    }
}
