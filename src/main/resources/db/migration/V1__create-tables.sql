CREATE TABLE usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasenia VARCHAR(255) NOT NULL,
    perfil VARCHAR(100) NOT NULL,
    state TINYINT,
    PRIMARY KEY (id)
);

CREATE TABLE topicos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(200) NOT NULL,
    mensaje TEXT NOT NULL,
    usuario_id BIGINT NOT NULL,
    nombreCurso VARCHAR(100) NOT NULL,
    categoriaCurso VARCHAR(100) NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    state TINYINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_topicos_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE respuestas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensaje TEXT NOT NULL,
    topico_id BIGINT NOT NULL,
    fechaCreacion DATETIME NOT NULL,
    usuario_id BIGINT NOT NULL,
    solucion TEXT NOT NULL,
    state TINYINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_respuestas_topico_id FOREIGN KEY (topico_id) REFERENCES topicos(id),
    CONSTRAINT fk_respuestas_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
)