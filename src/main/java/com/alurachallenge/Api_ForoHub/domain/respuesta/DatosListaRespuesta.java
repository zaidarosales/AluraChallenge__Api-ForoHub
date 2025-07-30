package com.alurachallenge.Api_ForoHub.domain.respuesta;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public record DatosListaRespuesta(
        Long id,
        String respuestaMensaje,
        String solucion,
        String topicoTitulo,
        String topicoMensaje,
        String topicoNameCurso,
        String autorNombre,
        String fechaRespuesta
) {

    public DatosListaRespuesta(Respuesta respuesta) {
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getSolucion(),
                respuesta.getTopico().getTitulo(),
                respuesta.getTopico().getMensaje(),
                respuesta.getTopico().getCurso().getNombreCurso(),
                respuesta.getAutor().getNombre(),
                respuesta.getFechaCreacion().format(
                        DateTimeFormatter.ofPattern( "d 'de' MMMM 'del' yyyy 'a las' h:mm a", new Locale("es", "ES"))
                )
        );
    }
}
