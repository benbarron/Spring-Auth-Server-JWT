package io.github.benbarron.springauthorizationserver.controllers;

import io.github.benbarron.springauthorizationserver.models.*;
import io.github.benbarron.springauthorizationserver.services.JsonWebTokenService;
import io.github.benbarron.springauthorizationserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JsonWebTokenService jsonWebTokenService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(UserService userService, PasswordEncoder passwordEncoder, JsonWebTokenService jsonWebTokenService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jsonWebTokenService = jsonWebTokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<JsonResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        String hashedPassword = this.passwordEncoder.encode(registrationRequest.getPassword());
        String username = registrationRequest.getUsername();
        User user = this.userService.save(new User(username, hashedPassword));
        JsonResponse response = new JsonResponse(HttpStatus.CREATED, "User created.");
        response.addDataField("user", user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JsonResponse> authenticate(@RequestBody AuthenticateRequest authenticateRequest) {
        String username = authenticateRequest.getUsername();
        String password = authenticateRequest.getPassword();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            this.authenticationManager.authenticate(authenticationToken);
            User user = this.userService.loadUserByUsername(username);
            String jwt = this.jsonWebTokenService.generateToken(user);
            JsonResponse jsonResponse = new JsonResponse(HttpStatus.ACCEPTED,"Log in success");
            jsonResponse.setUser(user);
            jsonResponse.setToken(jwt);
            return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
        } catch (BadCredentialsException e) {
            JsonResponse jsonResponse = new JsonResponse( HttpStatus.BAD_REQUEST, "Bad credentials.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }
    }
}
