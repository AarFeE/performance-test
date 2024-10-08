package riwi.aarfee.performance_test.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import riwi.aarfee.performance_test.dtos.requests.LoginReq;
import riwi.aarfee.performance_test.dtos.requests.RegisterReq;
import riwi.aarfee.performance_test.services.AuthService;

@Tag(name = "Authentication")
@RestController
@RequestMapping("api/auth")
@Validated
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Login",
            description = "Login with username and password",
    responses = {
            @ApiResponse(responseCode = "200", description = "Successful login, JWT returned"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Parameter(description = "Login request containing email and password")
            @Valid @RequestBody LoginReq authRequest) {

        String jwt = authService.login(authRequest.getEmail(), authRequest.getPassword());
        return ResponseEntity.ok(jwt);
    }

    @Operation(summary = "User Registration",
            description = "Register a new user and return a success message.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful registration"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "409", description = "User already exists")
            })
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Parameter(description = "Registration request containing email, name, and password")
            @Valid @RequestBody RegisterReq authRequest) {

        String result = authService.register(authRequest.getEmail(), authRequest.getName(), authRequest.getPassword(), authRequest.getRole());
        return ResponseEntity.ok(result);
    }

}
