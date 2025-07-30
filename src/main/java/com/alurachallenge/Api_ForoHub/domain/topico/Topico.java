package com.alurachallenge.Api_ForoHub.domain.topico;


import com.alurachallenge.Api_ForoHub.domain.curso.Curso;
import com.alurachallenge.Api_ForoHub.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", exclude = {"autor"})
@ToString(exclude = {"autor"})
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private  String titulo;
    @Setter
    private  String mensaje;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private Usuario autor;
    @Setter
    @Embedded
    private Curso curso;
    private Boolean state;

    public Topico(@Valid String titulo, String mensaje, Usuario usuario, Curso curso) {
        this.id = null;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaCreacion = LocalDateTime.now();
        this.autor = usuario;
        this.curso = curso;
        this.state = true;
    }



    public void actualizarInformaciones(@Valid DatosActualizarTopico datos, Curso cursoUpdates) {
        if(datos.titulo() != null && !datos.titulo().equals(this.titulo)) {
            System.out.println("entro al modificar titulo");
            this.titulo = datos.titulo();
        }
        if(datos.mensaje() != null && !datos.mensaje().equals(this.mensaje)) {
            System.out.println("entro al modificar mensage");
            this.mensaje = datos.mensaje();
        }
        System.out.println("no entro a modificar titulo ni mensage");
        if(cursoUpdates != null) {

            if(this.curso == null) {
                this.curso = new Curso("", "");
            }

            if(cursoUpdates.getNombreCurso() != null) {
                System.out.println("entro al modificar curso");
                this.curso.setNombreCurso(cursoUpdates.getNombreCurso());
            }
            if(cursoUpdates.getCategoriaCurso() != null) {
                this.curso.setCategoriaCurso(cursoUpdates.getCategoriaCurso());
            }
        }

    }

    public void eliminar() {
        this.state=false;
    }

}
