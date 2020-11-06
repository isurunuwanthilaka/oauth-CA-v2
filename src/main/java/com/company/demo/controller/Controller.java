package com.company.demo.controller;

import com.company.demo.exception.ErrorMessage;
import com.company.demo.service.DummyService;
import com.company.demo.util.ResponseHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Validated
@RestController
@RequestMapping("v1")
@Api(value = "ping")
@RequiredArgsConstructor
public class Controller {

    private final DummyService service;

    @GetMapping("/ping")
    @ApiOperation(value = "ping")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success", response = String.class)})
    public ResponseEntity ping() {
        Optional<String> ping = service.ping();

        if (ping.isPresent()) {
            return ResponseHelper.setResponse(ping);
        }

        return ResponseHelper.setError(ErrorMessage.PING_FAILED, "Ping failed because Pong was not there!");
    }

}
