package com.alurachallenge.Api_ForoHub.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DatosAuthentication(
        @NotBlank String email,
        @NotBlank String contrasenia
) {
}
