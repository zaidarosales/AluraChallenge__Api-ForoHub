package com.alurachallenge.Api_ForoHub.controller;

import com.alurachallenge.Api_ForoHub.domain.respuesta.*;
import com.alurachallenge.Api_ForoHub.domain.topico.Topico;
import com.alurachallenge.Api_ForoHub.domain.topico.TopicoRepository;
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
import richard.lipa.Api_ForoHub.domain.respuesta.DatosDetalleRespuesta;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name="bearer-key")
public class RespuestaController {


    @Autowired
    private RespuestaRepository respuestaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity crearRespuesta(@RequestBody @Valid DatosRegistroRespuesta datos, UriComponentsBuilder uriComponentsBuilder){
        Optional usuarioOptional = usuarioRepository.findById(datos.autorId());
        Optional topicoOptional = topicoRepository.findById(datos.topicoId());
        if(usuarioOptional.isPresent() && topicoOptional.isPresent()){
            Topico topico = (Topico) topicoOptional.get();
            Usuario usuario = (Usuario) usuarioOptional.get();
            var respuesta = new Respuesta(datos.mensaje(), datos.solucion(), usuario, topico);
            var nuevaRespuesta = respuestaRepository.save(respuesta);
            var uri = uriComponentsBuilder.path("topico/{id}").buildAndExpand(nuevaRespuesta.getId()).toUri();
            return  ResponseEntity.created(uri).body(new DatosDetalleRespuesta(nuevaRespuesta));
        }else {
            String mensaje = "El ID " + datos.topicoId() + " no corresponde a ningún tópico registrado.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
        }
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaRespuesta>> listar(@PageableDefault(size = 10, sort = {"mensaje"}, page = 0) Pageable paginacion){
        var page = respuestaRepository.findAllByStateTrue(paginacion).map(DatosListaRespuesta::new);

        return  ResponseEntity.ok(page);

    }

    @Transactional
    @PutMapping("/{id}") //  @RequestBody despues de id para evitar que no cargue los datos de la peticion
    public  ResponseEntity actualizar(@PathVariable Long id, @RequestBody DatosActualizarRespuesta datos){
        if(id==null){
            String mensaje = "NO existe id en la peticion";
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(mensaje);
        }
        Optional<Respuesta>  respuestaOptional = respuestaRepository.findByIdAndStateTrue(id);
        System.out.println("respuest Optional: "+ respuestaOptional);
        if (respuestaOptional.isPresent()) {
            Respuesta respuesta = (Respuesta) respuestaOptional.get();
            System.out.println("respuest Optional: "+ respuesta);
            respuesta.actualizarInformaciones(datos);
            var respuestaUpdate =respuestaRepository.save(respuesta);
            System.out.println("Después de saverespuesta update: " +respuestaUpdate);

            return ResponseEntity.ok(new DatosDetalleRespuesta(respuestaUpdate));
        }
        String mensaje = "El ID " + id + " no corresponde a ningún tópico registrado.";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);

    }

    @Transactional
    @GetMapping("/{id}") //  @RequestBody despues de id para evitar que no cargue los datos de la peticion
    public  ResponseEntity detalleRespuesta(@PathVariable Long id ){
        if(id==null){
            String mensaje = "NO existe id en la peticion";
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(mensaje);
        }
        //System.out.println("Datos recibidos: " + datos); // Debug: para que se llame a datos y evitar que cargue vacio
        Optional<Respuesta> respuestaOptional = respuestaRepository.findByIdAndStateTrue(id);
        if (respuestaOptional.isPresent()) {
            Respuesta respuesta = respuestaOptional.get();

            System.out.println("Después de saverespuesta update: " +respuesta);

            return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));
        }
        String mensaje = "El ID " + id + " no corresponde a ningún tópico registrado.";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public  ResponseEntity eliminar(@PathVariable Long id){
        Optional<Respuesta> respuestaOptional = respuestaRepository.findByIdAndStateTrue(id);
        if (respuestaOptional.isPresent()) {
            Respuesta respuesta = respuestaOptional.get();
            respuesta.eliminar();
            return  ResponseEntity.noContent().build();
        }
        String mensaje = "El ID " + id + " no corresponde a ningún tópico registrado.";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);

    }

}
