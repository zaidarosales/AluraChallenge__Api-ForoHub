package com.alurachallenge.Api_ForoHub.domain.respuesta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    Page<Respuesta> findAllByStateTrue(Pageable paginacion);

    Optional<Respuesta> findByIdAndStateTrue(Long id);
}
