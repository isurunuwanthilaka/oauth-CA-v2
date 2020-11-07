package com.company.demo.repository;

import com.company.demo.model.Token;
import com.company.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByToken(String jwt);

    List<Token> findAllByUserAndInvalidated(User user, int i);
}
