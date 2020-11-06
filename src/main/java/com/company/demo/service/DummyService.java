package com.company.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class DummyService {

    public Optional<String> ping() {
        return Optional.of("Ping");
    }

}
