package com.alurachallenge.Api_ForoHub.domain.curso;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jdk.jfr.StackTrace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class Curso {

    @Column(name = "nombreCurso")
    private String nombreCurso;
    @Column(name = "categoriaCurso")
    private String categoriaCurso;

    public Curso(DatosCurso datosCurso) {
        this.nombreCurso = datosCurso.nombreCurso();
        this.categoriaCurso = datosCurso.categoriaCurso();
    }


    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public String getCategoriaCurso() {
        return categoriaCurso;
    }

    public void setCategoriaCurso(String categoriaCurso) {
        this.categoriaCurso = categoriaCurso;
    }
}
