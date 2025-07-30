package com.alurachallenge.Api_ForoHub.domain.topico;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public record DatosListaTopico(
        Long id,
        String titulo,
        String mensaje,
        String autor_nombre,
        String autor_email,
        String nombreCurso,
        String categoriaCurso,
        String fechaCreacion, // ← nuevo campo bonito


        Boolean state
) {

    public DatosListaTopico(Topico topico){
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor().getNombre(),
                topico.getAutor().getEmail(),
                topico.getCurso().getNombreCurso(),
                topico.getCurso().getCategoriaCurso(),
                topico.getFechaCreacion().format(
                        DateTimeFormatter.ofPattern("d 'de' MMMM 'del' yyyy 'a las' h:mm a", new Locale("es", "ES"))),
                topico.getState()
        );

    }
}
