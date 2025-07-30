package com.alurachallenge.Api_ForoHub.controller;


import com.alurachallenge.Api_ForoHub.domain.usuario.DatosAuthentication;
import com.alurachallenge.Api_ForoHub.domain.usuario.Usuario;
import com.alurachallenge.Api_ForoHub.infra.security.DatosTokenJWT;
import com.alurachallenge.Api_ForoHub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid DatosAuthentication datos ){
        // usar con ciudado autehnticacionToken ya que con ella se accede a la contrasenia dedificada
        var authenticacionToken = new UsernamePasswordAuthenticationToken(datos.email(), datos.contrasenia());
        var autenticacion = authenticationManager.authenticate(authenticacionToken);
        var tokenJWT = tokenService.generarToken((Usuario) autenticacion.getPrincipal());

        return  ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
    }
}
