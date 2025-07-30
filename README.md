# 🚀 API ForoHub 🚀

¡Bienvenido al repositorio de la API REST de ForoHub! Este proyecto es la columna vertebral de una plataforma de foro, permitiendo la gestión de tópicos de discusión de manera segura y eficiente. Desarrollada con las últimas tecnologías de Java y el ecosistema Spring.

---

## ✨ Descripción del Proyecto

API ForoHub es una API RESTFULL construida con Java y Spring Boot, diseñada para gestionar tópicos en un sistema de foro. Implementa un sistema de autenticación y autorización robusto utilizando Spring Security y JSON Web Tokens (JWT), garantizando que solo los usuarios autenticados puedan interactuar con los recursos del foro.

El corazón de la API reside en sus capacidades CRUD (Crear, Leer, Actualizar, Borrar) para los tópicos, permitiendo a los usuarios registrados participar activamente en diversas discusiones.

---

## 🌟 Tecnologías Utilizadas

- **Java 17+**: El lenguaje de programación principal.
- **Spring Boot**: Framework para el desarrollo rápido y eficiente de aplicaciones Spring.
- **Spring Data JPA**: Para una interacción ORM simplificada con la base de datos.
- **Spring Security**: Proporciona autenticación, autorización y protección contra vulnerabilidades comunes.
- **JSON Web Tokens (JWT)**: Para una autenticación de usuario sin estado y segura.
- **MySQL / MariaDB**: Base de datos relacional para persistir la información (configurable).
- **Flyway**: Para la gestión de migraciones de base de datos, asegurando la consistencia del esquema.
- **Maven**: Herramienta de gestión de proyectos y dependencias.
- **Lombok**: Para reducir el código boilerplate en las entidades y DTOs.
- **Validación de Jakarta (Bean Validation)**: Para asegurar la integridad de los datos de entrada.

---

## 🚀 Funcionalidades Principales

La API ForoHub ofrece las siguientes capacidades:

### 👥 Gestión de Usuarios:

- Registro: Creación de nuevas cuentas de usuario.
- Login: Autenticación de usuarios para obtener un token JWT.
- Crear Usuario: Crear usuarios.
- Listar Usuarios: Consultar todos los usuarios existentes, con opciones de paginación y ordenamiento.
- Buscar Usuario: Encontrar usuarios específicos por ID u otros criterios.
- Actualizar Usuario: Modificar el contenido de usuarios existentes.
- Eliminar Usuario: Eliminar usuarios del foro.

### 💬 Gestión de Tópicos (CRUD Completo):

- Crear Tópico: Publicar nuevos temas de discusión en el foro.
- Listar Tópicos: Consultar todos los tópicos existentes, con opciones de paginación y ordenamiento.
- Buscar Tópico: Encontrar tópicos específicos por ID u otros criterios.
- Actualizar Tópico: Modificar el contenido de tópicos existentes.
- Eliminar Tópico: Eliminar tópicos del foro.

### 💬 Gestión de Respuestas (CRUD Completo):

- Crear Respuesta: Realizar respuestas de discusión en el foro.
- Listar Respuestas: Consultar todos las respuestas existentes, con opciones de paginación y ordenamiento.
- Buscar Respuesta: Encontrar respuestas específicas por ID u otros criterios.
- Actualizar Respuesta: Modificar el contenido de respuestas existentes.
- Eliminar Respuesta: Eliminar respuestas del foro.

---

## 🛠️ Cómo Clonar y Probar el Proyecto

Sigue estos pasos para poner en marcha la API ForoHub en tu entorno local.

### Prerequisitos

Antes de empezar, asegúrate de tener instalado:

- Java Development Kit (JDK) 17 o superior.
- Maven (generalmente incluido con tu IDE o puedes descargarlo).
- Un servidor de base de datos MySQL o MariaDB en ejecución.
- Un cliente REST como Insomnia o Postman para probar los endpoints.

### 1. Clonar el Repositorio

Abre tu terminal o Git Bash y ejecuta el siguiente comando:

```bash
git clone https://github.com/zaidarosales/api-forohub.git
cd api-forohub
```
### 2. Configura tu archivo application.properties
   Configura tu base de datos:
````properties
spring.datasource.url=jdbc:ruta_de_tu_base_de_datos
spring.datasource.username=tu_usuario_db
spring.datasource.password=tu_password_db
````

### 3. Construir el Proyecto
   Desde la raíz del proyecto en tu terminal, ejecuta el siguiente comando Maven para construir el proyecto:
```bash
mvn clean install
````

### 4. Ejecutar la Aplicación
   Después de la construcción exitosa, puedes ejecutar la aplicación Spring Boot:
```bash
mvn spring-boot:run
```

La aplicación se iniciará por defecto en el puerto 8080.

## ⚙️ Uso de la API (Flujo Básico)
Para interactuar con la API, sigue este flujo:

### Paso 1: Registrar un Nuevo Usuario
Endpoint: POST /usuarios/registrar

Body (JSON):
```json
{
"nombre": "nombre_de_usuario",
"email": "usuario@example.com",
"password": "mi_password_segura"
}
Respuesta: 201 Created
```

### Paso 2: Iniciar Sesión y Obtener el Token JWT
Endpoint: POST /usuarios/login

Body (JSON):
```Json
{
"email": "usuario@example.com",
"password": "mi_password_segura"
}
Respuesta: 200 OK con el token JWT en el cuerpo de la respuesta.

```
```json
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}
  Guarda este token. Lo necesitarás para todas las demás operaciones.
```
{


### Paso 3: Realizar Operaciones CRUD de Tópicos (Necesitas el Token)
Para cada operación, asegúrate de incluir el token JWT en el encabezado Authorization con el prefijo Bearer.

Ejemplo de encabezado:

```makefile
Authorization: Bearer TU_TOKEN_JWT_AQUI
Crear Tópico: POST /topicos

Listar Tópicos: GET /topicos (soporta paginación: ?page=0&size=10&sort=fechaCreacion,desc)

Buscar Tópico por ID: GET /topicos/{id}

Actualizar Tópico: PUT /topicos/{id}

Eliminar Tópico: DELETE /topicos/{id}

```

📄 Licencia
Este proyecto está bajo la licencia MIT. Consulta el archivo [LICENSE](./LICENSE) para más d