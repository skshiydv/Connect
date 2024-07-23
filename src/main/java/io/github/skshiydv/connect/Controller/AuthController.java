package io.github.skshiydv.connect.Controller;


import io.github.skshiydv.connect.configuration.JwtGenerator;
import io.github.skshiydv.connect.dto.AuthResponseDto;
import io.github.skshiydv.connect.dto.RegistrationDto;
import io.github.skshiydv.connect.dto.loginDto;
import io.github.skshiydv.connect.model.Roles;
import io.github.skshiydv.connect.model.UserEntity;
import io.github.skshiydv.connect.repository.rolesRepository;
import io.github.skshiydv.connect.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth/")
public class AuthController {
    private final userRepository userRepository;
    private final rolesRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder PasswordEncoder;
    private final JwtGenerator jwtGenerator;

    @Autowired
    public AuthController(userRepository userRepository, rolesRepository roleRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.PasswordEncoder = passwordEncoder;
    }
    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody loginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token=jwtGenerator.generator(authentication);

        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegistrationDto registrationDto) {
        if (userRepository.existsByUsername(registrationDto.getUsername()))
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        UserEntity user = new UserEntity();
        user.setUsername(registrationDto.getUsername());
        user.setName(registrationDto.getName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(PasswordEncoder.encode(registrationDto.getPassword()));
        Roles role = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
