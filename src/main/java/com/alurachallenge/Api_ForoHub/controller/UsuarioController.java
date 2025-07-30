package com.alurachallenge.Api_ForoHub.controller;

import com.alurachallenge.Api_ForoHub.domain.usuario.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name="bearer-key")
public class UsuarioController {
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @PostMapping
    public ResponseEntity registar(@RequestBody @Valid DatosRegistroUsuario datos, UriComponentsBuilder uriComponentsBuilder){

        var usuario = new Usuario(datos);
        var passwordPlano = datos.contrasenia();
        var passwordHasheado = passwordEncoder.encode(passwordPlano);
        usuario.setContrasenia(passwordHasheado);
        var usuarioNuevo =   repository.save(usuario);
        var uri = uriComponentsBuilder.path("usuario/{id}").buildAndExpand( usuario.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosDetalleUsuario(usuarioNuevo));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaUsuarios>> listar(@PageableDefault(size = 10, sort = {"nombre"}, page = 0) Pageable paginacion){

        var page = repository.findAllByStateTrue(paginacion).map(DatosListaUsuarios::new);
        return  ResponseEntity.ok(page);

    }



    @Transactional
    @PutMapping("/{id}") //  @RequestBody despues de id para evitar que no cargue los datos de la peticion
    public  ResponseEntity actualizar(@PathVariable Long id, @RequestBody DatosActualizarUsuario datos){
        if(id==null){
            String mensaje = "NO existe id en la peticion";
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(mensaje);
        }
        Optional<Usuario> usuarioOptional = repository.findByIdAndStateTrue(id);
        System.out.println("respuest Optional: "+ usuarioOptional);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = (Usuario) usuarioOptional.get();
            System.out.println("respuest Optional: "+ usuario);
            if (datos.contrasenia() != null){
                System.out.println("contrasenia Plaba " + datos.contrasenia());
                var contraseniaHasheada = passwordEncoder.encode(datos.contrasenia());
                usuario.setContrasenia(contraseniaHasheada);
                System.out.println("contrasenia hasheada "+ usuario.getContrasenia());
            }
            usuario.actualizarInformaciones(datos);
            // Guardando   cambios
            var usuarioUpdate =repository.save(usuario);
            System.out.println("Después de saverespuesta update: " +usuarioUpdate);

            return ResponseEntity.ok(new DatosDetalleUsuario(usuarioUpdate));
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
        Optional<Usuario>  usuarioOptional = repository.findByIdAndStateTrue(id);
        System.out.println("respuest Optional: "+ usuarioOptional);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = (Usuario) usuarioOptional.get();
            System.out.println("respuest Optional: "+ usuario);
            return ResponseEntity.ok(new DatosDetalleUsuario(usuario));
        }
        String mensaje = "El ID " + id + " no corresponde a ningún tópico registrado.";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public  ResponseEntity eliminar(@PathVariable Long id){
        Optional<Usuario>  usuarioOptional = repository.findByIdAndStateTrue(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = (Usuario) usuarioOptional.get();
            usuario.eliminar();
            return  ResponseEntity.noContent().build();
        }
        String mensaje = "El ID " + id + " no corresponde a ningún tópico registrado.";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);

    }
}
