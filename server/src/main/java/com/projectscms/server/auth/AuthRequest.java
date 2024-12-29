package com.projectscms.server.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record AuthRequest(
        @Email(message = "Niepoprawny format adresu email")
        String email,

        @NotNull(message = "Nie podano hasła")
        @Size(min=8, max=32, message ="Hasło musi składać się z przynajmniej 8 znaków")
        String password
) {}