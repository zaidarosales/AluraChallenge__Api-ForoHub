package com.alurachallenge.Api_ForoHub.domain.usuario;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String nombre;
    private  String email;
    @Setter
    private  String contrasenia;
    @Enumerated(EnumType.STRING)
    private Perfil perfil;
    private Boolean state;

    public Usuario(@Valid DatosRegistroUsuario datos) {
        this.id = null;
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.contrasenia = datos.contrasenia();
        this.perfil = datos.perfil();
        this.state = true;
    }

    public void eliminar(){
        this.state = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ADMIN"));
    }

    @Override
    public String getPassword() {
        return contrasenia;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void actualizarInformaciones(DatosActualizarUsuario datos) {
        if(datos.nombre() != null){
            this.nombre=datos.nombre();
        }
        if(datos.perfil() != null){
            this.perfil=datos.perfil();
        }
    }
}
