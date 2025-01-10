package com.projectscms.server.auth;

import com.projectscms.server.conf.JwtService;
import com.projectscms.server.users.User;
import com.projectscms.server.users.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{


    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    public String register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        var token = jwtService.generateToken(user);
        return "{\"token\":"+"\""+token+"\"}";
    }
    public String authenticate(AuthRequest request) throws Exception{
        return authenticate(request.email(), request.password());
    }
    public String authenticate(@NotNull String email, @NotNull String password) throws Exception {
        try {
            var user = userRepository.findByEmail(email).orElseThrow();
            if (passwordEncoder.matches(password, user.getPassword())) {
                var token = jwtService.generateToken(user);
                return "{\"token\":" + "\"" + token + "\"}";
            }else{
                return "Uwierzytelnianie uzytkownika : " + email + " nie powiodło się";
            }
        } catch (Exception e) {
            throw new Exception("Uwierzytelnianie uzytkownika : " + email + " nie powiodło się");
        }
    }


}