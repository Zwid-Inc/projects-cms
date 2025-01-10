package com.projectscms.server.auth;

import com.projectscms.server.users.User;
import com.projectscms.server.users.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


public interface AuthService {

    String register(@Valid User user);
    String authenticate(AuthRequest request) throws Exception;
    String authenticate(@NotNull String email, @NotNull String password) throws Exception;
}