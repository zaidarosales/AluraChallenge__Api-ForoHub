package com.alurachallenge.Api_ForoHub.domain.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmailContaining(String email);

    Page<Usuario> findAllByStateTrue(Pageable paginacion);

    Optional<Usuario> findByIdAndStateTrue(Long id);
}
