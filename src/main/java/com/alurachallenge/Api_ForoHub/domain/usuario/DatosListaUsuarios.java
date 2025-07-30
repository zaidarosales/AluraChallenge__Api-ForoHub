package com.alurachallenge.Api_ForoHub.domain.usuario;

public record DatosListaUsuarios(
        Long id,
        String nombre,
        String email,
        Perfil perfil
) {
    public DatosListaUsuarios(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getPerfil()
        );
    }


}
