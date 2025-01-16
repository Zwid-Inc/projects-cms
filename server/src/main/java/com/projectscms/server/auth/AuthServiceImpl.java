package com.projectscms.server.auth;

import com.projectscms.server.conf.JwtService;
import com.projectscms.server.users.User;
import com.projectscms.server.users.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        var token = jwtService.generateToken(user);
        return "{\"token\":"+"\""+token+"\"}";
    }
    public String authenticate(AuthRequest request) throws Exception{
        //System.out.println("Received email and password: " + request.email() + " " + request.password());
        return authenticate(request.email(), request.password());
    }
    public String authenticate(@NotNull String email, @NotNull String password) throws Exception {
        try {
            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (passwordEncoder.matches(password, user.getPassword())) {
                    var token = jwtService.generateToken(user);
                    return "{\"token\":" + "\"" + token + "\"}";
                } else {
                    return "Uwierzytelnianie uzytkownika : " + email + " nie powiodło się";
                }
            } else {
                return "Uwierzytelnianie uzytkownika : " + email + " nie powiodło się";
            }
        } catch (Exception e) {
            throw new Exception("Uwierzytelnianie uzytkownika : " + email + " nie powiodło się");
        }
    }


}