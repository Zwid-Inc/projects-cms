package com.projectscms.server.auth;

import com.projectscms.server.users.User;
import com.projectscms.server.users.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
   // @PreAuthorize("permitAll()")
    public ResponseEntity<String> register (@RequestBody User user){
        Optional<User> maybeUser = userService.getUserByEmail(user.getEmail());
        return maybeUser.isEmpty() ? ResponseEntity.ok(authService.register(user))
                : ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("{\"error\":\"Istnieje już konto powiązane z tym adresem e-mail. Rejestracja nie powiodła się.\"}");
    }
    @PostMapping("/login")
   // @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> login (@RequestBody AuthRequest request) throws Exception{
        return  ResponseEntity.ok(authService.authenticate(request));
    }
}
