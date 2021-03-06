package com.company.demo.controller;

import com.company.demo.exception.ErrorMessage;
import com.company.demo.model.User;
import com.company.demo.service.JwtTokenService;
import com.company.demo.service.UserDetailService;
import com.company.demo.util.ResponseHelper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserDetailService userDetailService;
    private final JwtTokenService jwtTokenService;

    @GetMapping("/login")
    @ApiOperation(value = "Login")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public void login(HttpServletResponse response) {
        response.setHeader("Location", "http://localhost:3000/login");
        response.setStatus(302);
    }

    @GetMapping("/jwt")
    @ApiOperation(value = "JWT Endpoint")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public ResponseEntity getJwt() {
        User user = userDetailService.getAuthenticatedUserFromSecurityContext();
        if (user == null) {
            log.error("Null user object in secured endpoint.");
            return ResponseHelper.setError(HttpStatus.UNAUTHORIZED, ErrorMessage.UNAUTHENTICATED);
        }
        String jwt = jwtTokenService.generateToken(user);
        return ResponseHelper.setResponse(jwt);
    }

    @PostMapping("/jwt/validate")
    @ApiOperation(value = "JWT Validation Endpoint")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public ResponseEntity validateJwt(HttpServletRequest request) {

        String token = jwtTokenService.getJwtFromServletRequest(request);

        if (token == null) {
            log.error("No JWT token found in Authorization header.");
            return ResponseHelper.setError(HttpStatus.NOT_ACCEPTABLE, ErrorMessage.MALFORMED_REQUEST);
        }

        if (jwtTokenService.validateToken(token)) {
            return ResponseHelper.setResponse(true);
        } else {
            return ResponseHelper.setError(HttpStatus.BAD_REQUEST, ErrorMessage.INVALID_JWT);
        }
    }

    @RequestMapping("/logout-success")
    @ApiOperation(value = "Logout Success Page")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public void logoutSuccess(HttpServletResponse response) {
        response.setHeader("Location", "http://localhost:3000/login");
        response.setStatus(302);
    }
}
