package com.alurachallenge.Api_ForoHub.domain.usuario;

public record DatosActualizarUsuario(
        String nombre,
        String contrasenia,
        Perfil perfil
) {
}
