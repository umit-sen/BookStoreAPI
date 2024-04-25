package com.umitsen.onlinebookstore.Controller.Auth;

import com.umitsen.onlinebookstore.Entity.AuthenticationRequest;
import com.umitsen.onlinebookstore.Entity.AuthenticationResponse;
import com.umitsen.onlinebookstore.Entity.RegisterRequest;
import com.umitsen.onlinebookstore.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
@PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
){
return ResponseEntity.ok(service.register(request));
}
@PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request

){
    return ResponseEntity.ok(service.authenticate(request));
}
}
