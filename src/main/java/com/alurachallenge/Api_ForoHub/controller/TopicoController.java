package com.alurachallenge.Api_ForoHub.controller;

import com.alurachallenge.Api_ForoHub.domain.curso.Curso;
import com.alurachallenge.Api_ForoHub.domain.topico.*;
import com.alurachallenge.Api_ForoHub.domain.usuario.Usuario;
import com.alurachallenge.Api_ForoHub.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name="bearer-key")
public class TopicoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity crearTopico(@RequestBody @Valid DatosRegistroTopico datos, UriComponentsBuilder uriComponentsBuilder){
        Usuario usuario = usuarioRepository.findById(datos.autorId())
                .orElseThrow(() -> new IllegalArgumentException("Autor con ID " + datos.autorId() + " no encontrado o NO existe en la BD."));
        Curso curso = new Curso(datos.nombreCurso(), datos.categoriaCurso());
        var topico = new Topico(datos.titulo(), datos.mensaje(), usuario, curso);
        var nuevoTopico = topicoRepository.save(topico);
        var uri = uriComponentsBuilder.path("topico/{id}").buildAndExpand(nuevoTopico.getId()).toUri();
        return  ResponseEntity.created(uri).body(new DatosDetalleTopico(nuevoTopico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaTopico>> listar(@PageableDefault(size = 10, sort = {"curso.nombreCurso", "fechaCreacion"}, page = 0) Pageable paginacion){
        var page = topicoRepository.findAllByStateTrue(paginacion).map(DatosListaTopico::new);
        return  ResponseEntity.ok(page);
    }

    @Transactional
    @PutMapping("/{idTopico}") //  @RequestBody despues de id para evitar que no cargue los datos de la peticion
    public  ResponseEntity actualizar(@PathVariable Long idTopico, @RequestBody  @Valid DatosActualizarTopico datos){
        if(idTopico==null){
            String mensaje = "NO existe id en la peticion";
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(mensaje);
        }
        System.out.println("Datos recibidos: " + datos);
        if(datos.titulo() == null && datos.mensaje() == null &&
                datos.nombreCurso() == null && datos.categoriaCurso() == null) {
            throw new IllegalArgumentException("Todos los campos del DTO están null");
        }
        Optional<Topico> topicoOptional = topicoRepository.findById(idTopico);
        if (topicoOptional.isPresent()) {
            Topico topico = topicoOptional.get();
            Curso cursoUpdates = new Curso(datos.nombreCurso(), datos.categoriaCurso());
            topico.actualizarInformaciones(datos, cursoUpdates);

            // Forzar detección de cambios
            var topicoUpdate = topicoRepository.save(topico);
            System.out.println("Después de save topico update: " + topicoUpdate);

            return ResponseEntity.ok(new DatosDetalleTopico(topicoUpdate));
        }
        String mensaje = "️El ID " + idTopico + " no corresponde a ningún tópico registrado.";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);

    }

    @Transactional
    @DeleteMapping("/{idTopico}")
    public ResponseEntity delete (@PathVariable Long idTopico){
        if(idTopico==null){
            String mensaje = "NO existe id en la peticion";
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(mensaje);
        }
        Optional<Topico> topicoOptional = topicoRepository.findById(idTopico);
        if (topicoOptional.isPresent()) {
            Topico topico = topicoOptional.get();
            System.out.println("eliminado topico: "+ topico);
            topico.eliminar();
            return ResponseEntity.noContent().build();
        } else {
            String mensaje = "El ID " + idTopico + " no corresponde a ningún tópico registrado.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
        }
    }


    @Transactional
    @GetMapping("/{id}")
    public  ResponseEntity detalleTopico(@PathVariable Long id){
        System.out.println("buscado topico.....");
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isEmpty()) {
            String mensaje = "El ID " + id + " no corresponde a ningún tópico registrado.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
        }
        Topico topico = topicoOptional.get();
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }
}
