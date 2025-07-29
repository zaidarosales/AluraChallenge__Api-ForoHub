package com.alurachallenge.Api_ForoHub.domain.respuesta;

public record DatosDetalleRespuesta(
        Long id,
        String respuestaMensaje,
        String solucion,
        String topicoTitulo,
        String topicoMensaje,
        String topicoNameCurso,
        String topicoFecha,
        String autorNombre,
        String autorEmail,
        String fechaRespuesta) {
    public DatosDetalleRespuesta(Respuesta respuesta) {

    }
}


