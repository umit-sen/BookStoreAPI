package com.umitsen.onlinebookstore.Service;
import com.umitsen.onlinebookstore.Entity.AuthenticationRequest;
import com.umitsen.onlinebookstore.Entity.AuthenticationResponse;
import com.umitsen.onlinebookstore.Entity.RegisterRequest;
import com.umitsen.onlinebookstore.Repository.UserRepository;
import com.umitsen.onlinebookstore.Entity.Role;


import com.umitsen.onlinebookstore.Entity.User;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(@NotNull RegisterRequest request) {
        Role userRole = request.isAdmin() ? Role.ADMIN : Role.USER;
        var user = User.builder().name(request.getName())
                        .email(request.getEmail())
                                .password(encoder.encode(request.getPassword()))
                                        .role(userRole)
                                                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }



    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    }

