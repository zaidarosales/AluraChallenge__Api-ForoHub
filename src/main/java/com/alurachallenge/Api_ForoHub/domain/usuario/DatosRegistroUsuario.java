package com.alurachallenge.Api_ForoHub.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroUsuario(
        @NotBlank String nombre,
        @NotBlank  String email,
        @NotBlank String contrasenia,
        @NotNull Perfil perfil
) {
}
