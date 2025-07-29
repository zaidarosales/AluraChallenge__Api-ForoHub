package com.alurachallenge.Api_ForoHub.domain.respuesta;

import com.alurachallenge.Api_ForoHub.domain.topico.Topico;
import com.alurachallenge.Api_ForoHub.domain.usuario.Usuario;
import jakarta.persistence.*;
import jdk.jfr.Threshold;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "respuestas")
@Entity(name = "Respuesta")

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;
    @Column(name = "fechaCreacion")
    private LocalDateTime fechaCreacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private String solucion;
    private Boolean state;

    public Respuesta(Topico topico, String mensaje, Usuario usuario, String solucion) {
        this.id = null;
        this.mensaje = mensaje;
        this.topico = topico;
        this.fechaCreacion = LocalDateTime.now();
        this.usuario = usuario;
        this.solucion = solucion;
        this.state = true;
    }

    public void actuaizarInformacion(DatosActualizarRespuesta datos){
        if (datos.mensaje() != null){
            this.mensaje = datos.mensaje();
        }
        if (datos.solucion() != null){
            this.solucion = datos.solucion();
        }
    }

    public void eliminar() {
        this.state = false;
    }
}
